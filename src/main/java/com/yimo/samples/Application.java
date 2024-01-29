package com.yimo.samples;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 这是描述
 *
 * @author 会跳舞的机器人
 * @date 2024/1/12
 */
@SpringBootApplication(scanBasePackages = "com.yimo.samples")
@EnableTransactionManagement
@MapperScan({"com.yimo.samples.dao"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
