package com.yyd.ll1.controller;

import com.yyd.ll1.LL1.LL1;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class LLController {
    @RequestMapping("/analyze")
    public Object analyze() throws IOException {
        LL1 ll1 = new LL1();
        return ll1.run("i+i*i#");
    }
}
