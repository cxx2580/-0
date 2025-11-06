package com.card;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.card.mapper")  // 扫描MyBatis接口
public class CardManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(CardManagementApplication.class, args);
    }
}