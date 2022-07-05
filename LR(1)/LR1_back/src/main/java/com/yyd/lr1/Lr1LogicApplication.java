package com.yyd.lr1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yyd
 */

@SpringBootApplication
@MapperScan("com.yyd.lr1.mapper")
public class Lr1LogicApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lr1LogicApplication.class, args);
    }

}
