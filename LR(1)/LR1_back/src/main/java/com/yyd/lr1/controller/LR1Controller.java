package com.yyd.lr1.controller;

import com.yyd.lr1.logic.LR1;
import com.yyd.lr1.service.LR1Service;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LR1Controller {

    @Autowired
    LR1Service lr1Service;

    @RequestMapping("lr1")
    public Object getLr1Data(@Param("str") String str, @Param("GS")String GS){
        return lr1Service.analyzeGS(GS, str);
    }
}
