package com.yyd.lr1.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yyd.lr1.enity.AnalyzeData;
import com.yyd.lr1.enity.GsObj;
import com.yyd.lr1.logic.LR1;
import com.yyd.lr1.logic.Result;
import com.yyd.lr1.logic.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.xml.ws.soap.Addressing;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class LR1Service {
    @Autowired
    GsObjService gsObjService;
    @Autowired
    RedisTemplate redisTemplate;
    /**
     * 对于文法以及句子的处理过程
     * @param GS 文法字符串
     * @param s 待分析句子
     * @return
     */
    public Object analyzeGS(String GS, String s){
        // 1. 查询数据库，看该文法是否已存在
        GsObj gsObj = gsObjService.getGsObj(GS);
        Result result = new Result();
        LR1 lr1 = new LR1();
        if(gsObj != null){
            // 2.如果存在，从数据库中获取该文法主键、文法内容、以及分析表
            lr1.init(gsObj.getContent(), s, gsObj.getNumOfStatus(), gsObj.getC());
            lr1.setAnalyzeTable(Util.analyzeTable(gsObj.getAnalyzeTable()));
            result.analyzeTable = lr1.packageResultData().analyzeTable;
            result.CData = lr1.getStringOfC();
            // 3.以 "待分析句子:文法主键" 形式的字符串为键，在Redis数据库中查询是否存在该句子的分析过程表
            List<AnalyzeData> list = (List<AnalyzeData>)redisTemplate.opsForValue().get(s+":" + gsObj.getId());
            if(list != null){
                // 4.如果存在
                result.analyzeData = list;
            }else{
                // 5.如果不存在
                lr1.run();
                redisTemplate.opsForValue().set(s+":" +gsObj.getId(), lr1.getAnalyzeDataList());
                result.analyzeData = lr1.getAnalyzeDataList();
            }
        }
        else{
            // 6. 如果不存在
            lr1.init(GS, s, 0, null);
            lr1.createAnalyzeTable();
            result = (Result) lr1.run();
            GsObj tempGsObj = new GsObj();
            tempGsObj.setContent(GS);
            tempGsObj.setAnalyzeTable(lr1.getAnalyzeTable());
            tempGsObj.setNumOfStatus(lr1.getCLen());
            tempGsObj.setC(JSONArray.toJSONString(lr1.getStringOfC()));
            gsObjService.insertGsObj(tempGsObj);
            redisTemplate.opsForValue().set(s+":" + gsObjService.getGsObj(GS).getId(), lr1.getAnalyzeDataList());
        }
        return result;
    }
}
