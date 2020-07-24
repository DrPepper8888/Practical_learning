package com.steam.datahanding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Serviceapplication {
    public static void main(String[] args) {
        // 启动 Springboot 的应用，参数是当前类的 class
        SpringApplication.run(Serviceapplication.class, args);
    }
}
