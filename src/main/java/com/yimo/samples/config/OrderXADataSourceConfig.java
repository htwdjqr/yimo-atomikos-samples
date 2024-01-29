package com.yimo.samples.config;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * 订单库数据源配置
 *
 * @author 会跳舞的机器人
 * @date 2024/1/25
 */
@Configuration
@MapperScan(basePackages = {"com.yimo.samples.dao.order"}, sqlSessionTemplateRef = "orderSqlSessionTemplate")
public class OrderXADataSourceConfig {

    @Primary
    @Bean(name = "orderDruidXADataSource")
    @ConfigurationProperties(prefix = "spring.datasource.order")
    public DruidXADataSource orderDruidXADataSource() {
        return new DruidXADataSource();
    }

    @Bean(name = "orderDataSource")
    public DataSource orderDataSource(@Qualifier("orderDruidXADataSource") DruidXADataSource druidXADataSource) {
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(druidXADataSource);
        xaDataSource.setUniqueResourceName("orderDataSource");
        return xaDataSource;
    }

    @Bean(name = "orderSqlSessionFactory")
    public SqlSessionFactory orderSqlSessionFactory(@Qualifier("orderDataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/order/*.xml"));//扫描指定目录的xml
        return bean.getObject();
    }

    @Bean(name = "orderSqlSessionTemplate")
    public SqlSessionTemplate orderSqlSessionTemplate(@Qualifier("orderSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
