package com.postdesign.dectersystem;

import com.github.jeffreyning.mybatisplus.conf.EnableMPP;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan(value = "com.postdesign.dectersystem.mapper")
@EnableMPP
public class DetectSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(DetectSystemApplication.class, args);
    }

}
