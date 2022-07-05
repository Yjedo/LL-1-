package com.yyd.ll1.controller;

import com.yyd.ll1.LR1.LR1_Synax;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class LRController {
    @RequestMapping("/lranalyze")
    public Object analyze(String GS, String str) throws IOException {
        LR1_Synax lr1_synax = new LR1_Synax();
       return lr1_synax.run(GS, str);
    }
}