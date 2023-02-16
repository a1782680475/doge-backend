package com.doge;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动类
 *
 * @author shixinyu
 * @date 2021-06-09 10:58
 */
@EnableAsync
@MapperScan("com/doge/dao/mapper")
@SpringBootApplication
public class DogeApplication {
    public static void main(String[] args) {
        SpringApplication.run(DogeApplication.class, args);
    }
}
