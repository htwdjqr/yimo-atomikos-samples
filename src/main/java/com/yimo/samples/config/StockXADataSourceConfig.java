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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * 库存库数据源配置
 *
 * @author 会跳舞的机器人
 * @date 2024/1/25
 */
@Configuration
@MapperScan(basePackages = {"com.yimo.samples.dao.stock"}, sqlSessionTemplateRef = "stockSqlSessionTemplate")
public class StockXADataSourceConfig {

    @Bean(name = "stockDruidXADataSource")
    @ConfigurationProperties(prefix = "spring.datasource.stock")
    public DruidXADataSource stockDruidXADataSource() {
        return new DruidXADataSource();
    }

    @Bean(name = "stockDataSource")
    public DataSource stockDataSource(@Qualifier("stockDruidXADataSource") DruidXADataSource druidXADataSource) {
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(druidXADataSource);
        xaDataSource.setUniqueResourceName("stockDataSource");
        return xaDataSource;
    }

    @Bean(name = "stockSqlSessionFactory")
    public SqlSessionFactory stockSqlSessionFactory(@Qualifier("stockDataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/stock/*.xml"));//扫描指定目录的xml
        return bean.getObject();
    }

    @Bean(name = "stockSqlSessionTemplate")
    public SqlSessionTemplate stockSqlSessionTemplate(
            @Qualifier("stockSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
