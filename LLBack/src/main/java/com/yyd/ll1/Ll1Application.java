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
        ll1.readGS("src/main/resources/static/GSdata2.txt");
        ll1.init("i*i+i#");
        ll1.run();
//        ll1.getFirst("E");
//        ll1.getFirst("M");
//
//        ll1.getFirst("T");
//
//        ll1.getFirst("X");
//        ll1.getFirst("F");

//        ll1.init("i+i*i#");
//        ll1.run();
    }

}
