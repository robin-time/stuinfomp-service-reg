package com.lxy.stuinfomp.service.reg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = "com.lxy.stuinfomp" )
@EnableDiscoveryClient
@MapperScan(basePackages = "com.lxy.stuinfomp.commons.mapper")
@EnableAsync
@EnableSwagger2
public class StuinfompServiceRegApplication {
    public static void main(String[] args) {
        SpringApplication.run(StuinfompServiceRegApplication.class,args);
    }
}
