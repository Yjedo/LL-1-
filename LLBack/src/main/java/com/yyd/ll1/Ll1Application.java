package com.yyd.ll1;

import com.yyd.ll1.LL1.LL1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Ll1Application {

    public static void main(String[] args) throws IOException {

        SpringApplication.run(Ll1Application.class, args);
        LL1 ll1 = new LL1();
        ll1.run("i+i*i#");
    }

}
