package com.yyd.lr1.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yyd.lr1.enity.GsObj;
import com.yyd.lr1.mapper.GsObjMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GsObjService {
    @Autowired
    private GsObjMapper gsObjMapper;

    /**
     * 查找文法
     * @param gsContent
     * @return
     */
    public GsObj getGsObj(String gsContent){
        QueryWrapper<GsObj> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("content", gsContent);
        return gsObjMapper.selectOne(queryWrapper);
    }

    /**
     * 插入文法
     * @param gsObj
     * @return
     */
    public int insertGsObj(GsObj gsObj){
        return gsObjMapper.insert(gsObj);
    }
}
