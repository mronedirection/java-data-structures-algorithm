package com.renhao.greedy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 使用贪心算法处理-集合覆盖问题
 * 选择最少的广播台，让所有的地区都可以接收到信号
 * @author RenHao
 * @create 2022-11-08 14:18
 */
public class GreedyAlgorithm {
    public static void main(String[] args) {
        //创建HashMap，初始化广播电台
        HashMap<String, HashSet<String>> broadCasts = new HashMap<>();

        HashSet<String> set1 = new HashSet<>();
        set1.add("北京");
        set1.add("上海");
        set1.add("天津");

        HashSet<String> set2 = new HashSet<>();
        set2.add("广州");
        set2.add("北京");
        set2.add("深圳");

        HashSet<String> set3 = new HashSet<>();
        set3.add("成都");
        set3.add("上海");
        set3.add("杭州");

        HashSet<String> set4 = new HashSet<>();
        set4.add("上海");
        set4.add("天津");

        HashSet<String> set5 = new HashSet<>();
        set5.add("杭州");
        set5.add("大连");

        broadCasts.put("k1", set1);
        broadCasts.put("k2", set2);
        broadCasts.put("k3", set3);
        broadCasts.put("k4", set4);
        broadCasts.put("k5", set5);

        //创建HashSet存储所有地区
        HashSet<String> allAreas = new HashSet<>();
        allAreas.addAll(set1);
        allAreas.addAll(set2);
        allAreas.addAll(set3);
        allAreas.addAll(set4);
        allAreas.addAll(set5);

        //创建ArrayList,存储最终选择的电台
        ArrayList<String> selects = new ArrayList<>();
        //存储for循环过程中的最大set对应的key
        String maxKey = null;
        //临时变量
        HashSet<String> tempSet = null;
        HashSet<String> preMaxSet = null;
        while(allAreas.size() != 0){
            //置空maxKey
            maxKey = null;
            //遍历broadCasts的keySet
            for(String key : broadCasts.keySet()){
                //取出当前key对应的tempSet
                tempSet = broadCasts.get(key);
                //求出当前tempSet与allAreas的交集
                tempSet.retainAll(allAreas);
                //求出上一个maxKey对应的set与allAreas的交集
                preMaxSet = null;
                if(maxKey != null){
                    preMaxSet = broadCasts.get(maxKey);
                    preMaxSet.retainAll(allAreas);
                }
                //如果当前tempSet的size大于上一个maxSet的size，则替换maxKey
                if(tempSet.size() > 0 && (maxKey == null || tempSet.size() > preMaxSet.size())){
                    maxKey = key;
                }
            }
            //如果找到了maxKey
            if(maxKey != null){
                //将当前maxKey放入ArrayList
                selects.add(maxKey);
                //从allAreas中移除maxKey对应的地区
                allAreas.removeAll(broadCasts.get(maxKey));
            }
        }
        System.out.println("要选择的电台为：" + selects);//[k1, k2, k3, k5]
    }


}
