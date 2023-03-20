package com.postdesign.detectsystem;

import com.github.jeffreyning.mybatisplus.conf.EnableMPP;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@MapperScan(value = "com.postdesign.detectsystem.mapper")
@EnableMPP
public class DetectSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(DetectSystemApplication.class, args);
    }

}
