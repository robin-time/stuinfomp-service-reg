package com.lxy.stuinfomp.service.reg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.lxy.stuinfomp.commons.mapper")
public class StuinfompServiceRegApplication {
    public static void main(String[] args) {
        SpringApplication.run(StuinfompServiceRegApplication.class,args);
    }
}
