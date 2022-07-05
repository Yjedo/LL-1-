package com.yyd.lr1.enity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("gs")
public class GsObj {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("content")
    private String content;
    @TableField("analyzeTable")
    private String analyzeTable;
    @TableField("c")
    private String C;
    @TableField("numOfStatus")
    private Integer numOfStatus;
}
