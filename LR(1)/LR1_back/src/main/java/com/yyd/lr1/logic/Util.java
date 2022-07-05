package com.yyd.lr1.logic;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 工具类，封装常用的一些功能
 */
public class Util {

    /**
     * 设置分析表(字符串形式)
     */
    public static Map<String, String> analyzeTable(String analyzeTableStr){
        String[] list = analyzeTableStr.split("&&");
        Map<String, String> map = new HashMap<>();
        for(String s : list){
            String[] array = s.split("->");
            map.put(array[0], array[1]);
        }
       return map;
    }

    /**
     * 读取文件内容
     * @param filename
     * @return
     */
    public static String readFile(String filename){
        StringBuilder inString = new StringBuilder();
        try {
            InputStreamReader reader =
                    new InputStreamReader(new FileInputStream(filename));
            for (int ch; (ch = reader.read()) != -1; ) {
                inString.append((char) ch);
            }
        }catch (Exception e){
            System.out.println("读取文件内容出错!!!");
        }
        return inString.toString();
    }

    /**
     * 判断元素是否在数组内
     * @param strings
     * @param s
     * @return
     */
    public static boolean isInArray(String[] strings, String s){
        for(String item : strings){
            if (s.equals(item)){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断项目集列表是否包含某项目集
     * @param itemSets
     * @param itemSet
     * @return
     */
    public static ItemSet isConatins(ArrayList<ItemSet> itemSets, ItemSet itemSet) {
        for(ItemSet i:itemSets){
           if(isEqual(i, itemSet)) return i;
        }
        return null;
    }

    /**
     * 判断项目集是否包含某项目
     * @param itemSet
     * @param item
     * @return
     */
    public static boolean isConatins(ItemSet itemSet, Item item) {
        for(Item i:itemSet.getItemList()){
            if(i.getContent().equals(item.getContent())){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两项目集是否相等
     * @param itemSet1
     * @param itemSet2
     * @return
     */
    public static boolean isEqual(ItemSet itemSet1, ItemSet itemSet2){
        if(itemSet1 == null || itemSet2 == null){
            return false;
        }
        List<String> strings1 = itemSet1.getItemList().stream().map(Item::getContent).collect(Collectors.toList());
        List<String> strings2 = itemSet2.getItemList().stream().map(Item::getContent).collect(Collectors.toList());
        for(String s : strings1){
            if(!strings2.contains(s)){
                return false;
            }
        }
        return true;
    }


    /**
     * 测试函数用法等
     * @param args
     */
    public static void main(String[] args) {
        String s = "123&&456&&";
        String[] array = s.split("&&");
        Arrays.stream(array).forEach(item -> System.out.println(item));
    }
}