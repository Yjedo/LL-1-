package com.yyd.lr1.logic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yyd.lr1.enity.AnalyzeData;
import java.util.*;
import java.util.stream.Collectors;

/**
 * LR(1)类
 */
public class LR1 {

    //终结符
    private String[] VT;
    //非终结符
    private String[] VN;

    //待分析句子
    private String inputString;

    //文法开始符
    private String startChar;

    //项目集族C
    private ArrayList<ItemSet> C;

    //项目集族C序列化
    private Object stringOfC;

    //存储文法
    private Map<String, List<String>> GS;

    //按顺序存储产生式
    private List<String> gsList;

    //存储非空文法符号集
    private ArrayList<String> gsChars;

    //分析表
    private Map<String, String> analyzeTable;

    //句子分析过程数据
    private List<AnalyzeData> analyzeDataList;

    //状态个数
    private int numOfStatus;

    //求解FIRST集
    /**
     * 求解first集合
     * @param gsChar 文法符号
     */
    public List<String> getFirst(String gsChar){
        List<String> firstOfGsChar = new ArrayList<>();
        boolean flag = true;
        int i;
        for(i = 0; i < gsChar.length(); i++){
            if (Util.isInArray(VT, String.valueOf(gsChar.charAt(i))) ||String.valueOf(gsChar.charAt(i)).equals("#")){
                //首字符即为终结符
                firstOfGsChar.add(String.valueOf(gsChar.charAt(i)));
                flag = false;
                break;
            }else {
                List<String> strList = GS.get(String.valueOf(gsChar.charAt(i)));
                List<String> tempFirst = new ArrayList<>();
                for(String str : strList){
                    List<String> tempList = getFirst(str);
                    try {
                        tempFirst = sumList(tempFirst, tempList);
                    }catch (Exception e){
                        System.out.println(e);
                        System.out.println(gsChar + tempFirst + tempList);
                        return null;
                    }
                }
                if(tempFirst.contains("$")){
                    //如果包含空字符
                    firstOfGsChar = addAllNotNullItem(firstOfGsChar, tempFirst);

                }else{
                    firstOfGsChar = sumList(firstOfGsChar, tempFirst);
                    flag = false;
                    break;
                }
            }
        }
        if (flag && i==gsChar.length()){
            //如果gsChar中全为非终结符，且全部可推出$, 则将$加入firstOfGsChar
            firstOfGsChar.add("$");
        }
        return firstOfGsChar;
    }

    /**
     * 将First(A)中所有非空元素加入到First(B)中
     * @param A
     * @param B
     */
    private List<String> addAllNotNullItem(List<String> A, List<String> B){
        A.stream().forEach((String s)->B.add(s));
        //去重与空字符$
        return B.stream().distinct().filter((String s)->!s.equals("$")).collect(Collectors.toList());
    }


    /**
     * 合并两集合元素并去重
     * @param A
     * @param B
     * @return
     */
    private List<String> sumList(List<String> A, List<String> B){
        A.forEach((String s)-> System.out.print(s));
        System.out.println(" ");
        B.forEach((String s)-> System.out.print(s));
        System.out.println(" ");
        try {
            A.stream().forEach((String s)->B.add(s));
        }catch (StackOverflowError e){
            System.out.println(e);
            A.forEach((String s)-> System.out.println(s));
            B.forEach((String s)-> System.out.println(s));
            return null;
        }
        return B.stream().distinct().collect(Collectors.toList());
    }


    /**
     * 读取文法内容并封装相关数据
     */
    public void readGS(String inString){
        gsList = new ArrayList<>();
        String[] strings = inString.split("\n");
        startChar = strings[0].charAt(0) + "";
        gsList.add("Z->" +startChar);
//        Arrays.stream(strings).forEach((String s)-> System.out.println(s));
        GS = new HashMap<>();

        List<String> vtList = new ArrayList<>();

        Arrays.stream(strings).forEach((String s)->{
            String leftStr = s.substring(0, 1);
            String rightStr = s.substring(3);
            gsList.add(s);
            for (int i = 0; i < rightStr.length(); i++){
                //提取终结符
                if(!(rightStr.charAt(i) >= 'A' && rightStr.charAt(i) <= 'Z')){
                    vtList.add(String.valueOf(rightStr.charAt(i)));
                }
            }
//            System.out.println(rightStr+" "+leftStr);
            if(GS.containsKey(leftStr)){
                //如果该非终结符已经存在产生式, 则将后续产生式加入到其产生式表中
                GS.get(leftStr).add(rightStr);
            }else {
                ArrayList<String> strList = new ArrayList<>();
                strList.add(rightStr);
                GS.put(leftStr, strList);
            }
        });

        VN = GS.keySet().toArray(new String[0]);
        VT = vtList.stream().distinct().collect(Collectors.toList()).toArray(new String[0]);

        //构建文法符号集
        gsChars = new ArrayList<>();
        Arrays.stream(VN).forEach(s->gsChars.add(s));
        Arrays.stream(VT).forEach(s->gsChars.add(s));


    }

    /**
     * 通过文件初始化（测试用）
     * @param s
     */
    public void init(String s){
        this.inputString = s;
        System.out.println("输入句子为: "+s);
        try {
            //从文件读取文法
            readGS(Util.readFile("src/main/resources/static/test1.txt"));
        }catch (Exception e){
            System.out.println("读取文件出现问题");
        }
    }

    /**
     * 通过参数初始化
     * @param gs
     * @param s
     */
    public void init(String gs, String s, int numOfStatus, String C){
        this.inputString = s;
        this.numOfStatus = numOfStatus;
        this.stringOfC = JSONArray.parse(C);
        System.out.println("输入句子为: "+s);
        readGS(gs);
    }



    //TODO 求解项目集闭包CLOSURE(I)
    private ItemSet closure(ItemSet I){
        ItemSet itemSet = new ItemSet(new ArrayList<>());

        //将I中的项目全部加入
        I.getItemList().stream().forEach(item -> itemSet.addItem(item));

        ArrayList<Item> tempSet = itemSet.getItemList();

        //遍历当前闭包的项目,对于形如 A->a.BS,x的项目进行处理
        for(int i = 0; i < tempSet.size(); i++){
            Item item = tempSet.get(i);
            int dotPos = item.getDotIndex();
            String charAfterDot = item.getIndexChar(dotPos+1);
            //如果.后面存在文法字符，且其是非终结符
            if(dotPos < item.getLengthOfCreateStr()-1 && Util.isInArray(VN, charAfterDot)){

                String S = item.getCreateStr().substring(dotPos+2);
                List<String> tempFrist = getFirst(S + item.getOutlookChar());

                //添加符合要求项目进入闭包
                for (String str:GS.get(charAfterDot)){
                    for (String firstItem : tempFrist){
                        Item tempItem = new Item(charAfterDot + "->" + "." + str + "," + firstItem);
                        if (!Util.isConatins(itemSet, tempItem)){
                            itemSet.addItem(tempItem);
                        }
                    }
                }
                tempSet = itemSet.getItemList();
            }
        }

        if(itemSet.getItemList().size() == 0){
            return null;
        }

        return itemSet;
    }

    //TODO 求解状态转换函数GO(I, X)
    private ItemSet GO(ItemSet I, String X){
        ItemSet itemSet = new ItemSet(new ArrayList<>());

        for(Item item : I.getItemList()){
            //此处dotPos是点在项目产生式中的位置，而非在整个项目中所处的位置
            int dotPos = item.getDotIndex();
            String charAfterDot = item.getIndexChar(dotPos+1);
            if(dotPos < item.getLengthOfCreateStr()-1 && charAfterDot.equals(X)){
                //交换.与其后文法符号的位置
                String s1 = item.getContent().substring(0, dotPos+3);
                String s2 = item.getContent().substring(dotPos+2+3);
                String newStr = s1 + charAfterDot + "." + s2;
                itemSet.addItem(new Item(newStr));
            }
        }

        return closure(itemSet);
    }

    //TODO 求解项目集族C
    private void createC(){
        //初始化
        this.C = new ArrayList<>();
        ItemSet itemSet = new ItemSet(new ArrayList<>());
        itemSet.addItem(new Item("Z->." + startChar + ",#"));
        itemSet = closure(itemSet);
        itemSet.setNumber(0);
        C.add(itemSet);

        //遍历C中每个项目集
        for(int i = 0; i < C.size(); i++){
            ItemSet tempItemSet = C.get(i);
            //遍历文法符号集
            for (String gsChar:gsChars){
                ItemSet items = GO(tempItemSet, gsChar);
                ItemSet itemSet1 = Util.isConatins(C, items);
                if (items != null && itemSet1 == null){
                    //若GO(I, X)不为空，且在C中不存在
                    items.setNumber(C.size());
                    tempItemSet.setStatus(gsChar, C.size());
                    C.set(i, tempItemSet);
                    C.add(items);
                }else if(itemSet1 != null) {
                    //若GO(I, X)不为空，且在C中存在
                    tempItemSet.setStatus(gsChar, itemSet1.getNumber());
                    C.set(i, tempItemSet);
                }
            }
        }
    }

    //TODO 构建分析表
    public void createAnalyzeTable(){
        createC();
        this.numOfStatus = this.C.size();
        analyzeTable = new HashMap<>();
        for(ItemSet itemSet : C){
            for(String s : VN){
                //遍历非终结符集,填写GOTO表
                if(itemSet.getKeySet().contains(s)){
                    analyzeTable.put(itemSet.getNumber() + ":" + s, itemSet.getStatus(s) + "");
                }
            }
            for(Item item : itemSet.getItemList()){
                if(item.getDotIndex() < item.getLengthOfCreateStr()-1){
                    int dotPos = item.getDotIndex();
                    String charAfterDot = item.getIndexChar(dotPos+ 1);
                    //.后面存在终结符，移进
                    if(Util.isInArray(VT, charAfterDot) && itemSet.getKeySet().contains(charAfterDot)){
                        analyzeTable.put(itemSet.getNumber() + ":" + charAfterDot, "s" + itemSet.getStatus(charAfterDot));
                    }
                } else if(item.getDotIndex() == item.getLengthOfCreateStr()-1){
                    if(item.getContent().equals("Z->" + startChar + ".,#")){
                        //接受标志, acc
                        analyzeTable.put(itemSet.getNumber() + ":#", "acc");
                        continue;
                    }
                    String str = item.getContent().substring(0, item.getDotIndex()+3);

                    int numOfStr = gsList.indexOf(str);

                    //归约
                    if(numOfStr != -1){
                        analyzeTable.put(itemSet.getNumber() + ":" + item.getOutlookChar(), "r" + numOfStr);
                    }
                }
            }
        }
    }

    //TODO 总控程序
    public Object run(){
        //初始化
        analyzeDataList = new ArrayList<>();
        Stack<StackObj> stack = new Stack<>();
        StackObj stackObj = new StackObj();
        stackObj.status = "0";
        stackObj.X = "#";
        stack.push(stackObj);

        //记录当前读取字符的位置
        int index = 0;
        int step = 1;
        while(true){
            AnalyzeData analyzeData1 = new AnalyzeData();
            StackObj tempObj = stack.peek();
            analyzeData1.step = step+ "";
            Stack<StackObj> tempStack = stack;
            tempStack.stream().forEach(item->{
                System.out.println(item.status);
                if(item.status != null && item.X != null){
                    analyzeData1.stackData += item.status + " ";
                    analyzeData1.string += item.X + " ";
                }
            });
            analyzeData1.stackData =  analyzeData1.stackData.substring(4);
            analyzeData1.string =  analyzeData1.string.substring(4);

            analyzeData1.s = inputString.substring(index);

            String result = analyzeTable.get(tempObj.status + ":" + inputString.charAt(index));
            if(result == null){
                System.out.println(step);
                System.out.println(tempObj.status + " : " + inputString.charAt(index) + " -> " + result);
                return  null;
            }
            if(result.charAt(0) == 's'){
                //移进判断
                analyzeData1.action = "Action[" + tempObj.status + inputString.charAt(index) + "]" + "=" +
                        result + ", 状态" + result.charAt(1) + "入栈";
                StackObj stackObj1 = new StackObj();
                stackObj1.status = result.charAt(1) + "";
                stackObj1.X = inputString.charAt(index) + "";
                index++;
                stack.push(stackObj1);
            } else if(result.charAt(0) == 'r'){
                //规约判断
                String str = gsList.get(result.charAt(1)-'0');
                String[] strings = str.split("->");
                for(int i = 0; i < strings[1].length(); i++){
                    stack.pop();
                }
                StackObj stackObj1 = stack.peek();
                String result1 =  analyzeTable.get(stackObj1.status + ":" + strings[0]);
                analyzeData1.action = result + ":" +  str + "归约," + "GOTO(" + stackObj1.status + "," + strings[0] + ")=" + result1 + " 入栈";
                StackObj newStackObj = new StackObj();
                newStackObj.status = result1;
                newStackObj.X = strings[0];
                stack.push(newStackObj);
            } else if(result.equals("acc")){
                //结束判断
                analyzeData1.action = "Acc: 分析成功";
                analyzeDataList.add(analyzeData1);
                break;
            } else {
                System.out.println(tempObj.status + " : " + inputString.charAt(index) + " -> " + result);
                System.out.println("分析出现错误了");
                return null;
            }
            analyzeDataList.add(analyzeData1);
            step++;
        }

        return packageResultData();
    }

    /**
     * 打包返回数据
     */
    public Result packageResultData(){
        Result result = new Result();
        result.analyzeData = this.analyzeDataList;

        List<List<String>> returnAnalyzeTable = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        List<String> tempGsChars = gsChars;
        tempGsChars.add("#");
        list1.add("状态");
        tempGsChars.stream().forEach(item -> list1.add(item));
        returnAnalyzeTable.add(list1);

        for(int i  = 0; i<numOfStatus; i++){
            List<String> list = new ArrayList<>();
            list.add(i+"");
            for(String s : tempGsChars){
                if(this.analyzeTable.get(i+":" +s) != null){
                    list.add(this.analyzeTable.get(i+":" +s));
                }else {
                    list.add(" ");
                }
            }
            returnAnalyzeTable.add(list);
        }

        result.analyzeTable = returnAnalyzeTable;

        ArrayList<C> clist = new ArrayList<>();

       if(this.stringOfC != null)
           result.CData = this.stringOfC;
       else {
           for(ItemSet itemSet : this.C){
               C c = new C();
               c.num = itemSet.getNumber();
               c.items = (ArrayList<String>) itemSet.getItemList().stream().map(item ->  item.getContent()).collect(Collectors.toList());
               c.turnStatus = (ArrayList<String>) itemSet.getKeySet().stream().map(item->item+"==>"+itemSet.getStatus(item)).collect(Collectors.toList());
               clist.add(c);
           }
           result.CData = clist;
           this.stringOfC = clist;
       }

        return result;
    }


    /**
     * 序列化分析表
     */
    public String getAnalyzeTable(){
        String analyzeTableStr = "";
        for(String key : analyzeTable.keySet()){
            analyzeTableStr += (key + "->" + analyzeTable.get(key)) +  "&&";
        }
        return analyzeTableStr;
    }

    /**
     * 手动设置分析表(从数据库获取)
     * @param map
     */
    public void setAnalyzeTable(Map<String, String> map){
        this.analyzeTable = map;
    }

    /**
     * 获取分析过程数据
     * @return
     */
    public List<AnalyzeData> getAnalyzeDataList(){
        if(this.analyzeDataList != null){
            return this.analyzeDataList;
        }
        System.out.println("分析过程数据表未初始化！！");
        return null;
    }

    /**
     * 获取状态个数
     * @return
     */
    public int getCLen(){
        return this.C.size();
    }

    public Object getStringOfC(){
        return this.stringOfC;
    }

}
