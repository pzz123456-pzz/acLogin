package com.ac.aclogin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class AcloginApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcloginApplication.class, args);
    }

}
