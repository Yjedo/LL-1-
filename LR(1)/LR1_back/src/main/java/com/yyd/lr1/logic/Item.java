package com.yyd.lr1.logic;

import java.util.regex.Pattern;

/**
 * 项目类
 */
public class Item {
    //项目内容(形如A->a.b,x), 其中x为展望符
    private String content;

    //非终结符
    private String vnChar;

    //.的位置
    private int dotIndex;

    //产生式
    private String createStr;

    //展望符
    private String outlookChar;

    public Item(String s){
        this.content = s;
        if(isItem()){
            String[] strings1 = s.split("->");
            String[] strings2 = strings1[1].split(",");
            vnChar = strings1[0];
            createStr = strings2[0];
            outlookChar = strings2[1];
            dotIndex = createStr.indexOf(".");
        }else {
            System.out.print(s + "  ");
            System.out.println("输入字符串不满足项目的基本格式!");
        }
    }

    //判断字符串是否符合项目格式
    public boolean isItem(){
        //利用正则表达式匹配判断输入字符串是否满足一个项目的基本格式
        return Pattern.matches("[A-Z]->.*\\..*,.", this.content);
    }

    /**
     * 获取特定位置的字符
     * @return
     */
    public String getIndexChar(int pos){
        if(pos > createStr.length() -1) return "";
        return createStr.charAt(pos) + "";
    }

    public int getDotIndex(){
        return this.dotIndex;
    }

    public int getLengthOfCreateStr(){
        return this.createStr.length();
    }

    public String getCreateStr(){
        return createStr;
    }

    public String getContent(){
        return  this.content;
    }

    public String getOutlookChar(){
        return outlookChar;
    }
}
