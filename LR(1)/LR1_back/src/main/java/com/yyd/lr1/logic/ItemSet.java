package com.yyd.lr1.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 项目集类(状态)
 */
public class ItemSet {
    //状态编号
    private int number;
    //项目列表
    private ArrayList<Item> itemList;
    //可转换状态
    private Map<String, Integer> statusTurnMap =  new HashMap<>();

    public ItemSet(ArrayList<Item> items){
        this.itemList = items;
    }

    public void setNumber(int number){
        this.number = number;
    }

    public void addItem(Item item){
        this.itemList.add(item);
    }

    public ArrayList<Item> getItemList(){
        return itemList;
    }

    public int getNumber() {
        return number;
    }

    public void setStatus(String source, int target){
        statusTurnMap.put(source, target);
    }

    public int getStatus(String key){
        return statusTurnMap.get(key);
    }

    public Set<String> getKeySet(){
        return statusTurnMap.keySet();
    }
}
