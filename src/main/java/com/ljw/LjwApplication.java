package com.ljw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.ljw.dao")
public class LjwApplication {
    public static void main(String[] args) {
        SpringApplication.run(LjwApplication.class, args);
    }
}