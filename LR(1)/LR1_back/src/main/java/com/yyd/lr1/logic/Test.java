package com.yyd.lr1.logic;

import java.util.List;


/**
 * 临时测试
 */
public class Test {
    public static void main(String[] args) {
        LR1 lr1 = new LR1();
        lr1.init("i+i*i#");
        lr1.createAnalyzeTable();
        lr1.run();
//        List<String> list = lr1.getFirst("E");
        System.out.println("da");
    }
}
