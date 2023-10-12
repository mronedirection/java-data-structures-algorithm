package com.renhao.dynamic;

import java.util.Arrays;

/**
 * 背包问题（0/1）
 * @author RenHao
 * @create 2022-10-31 11:25
 */
public class KnapsackProblem {

    public static void main(String[] args) {

        int[] weights = {1, 4, 3};//物品的重量
        int[] values = {1500, 3000, 2000};//不同重量物品对应的价值
        int m = 4;//背包的容量
        int n = values.length;//物品的个数

        int[][] v = new int[n + 1][m + 1];//前i个物品，将其最佳组合放入容量为j的背包，背包能存储的最大价值
//        int[][] path = new int[n+1][m+1];//记录背包存放物品情况


        //初始化第一行和第一列
        for (int i = 0; i < v[0].length; i++) {
            v[0][i] = 0;//将第一行设置为0
        }
        for (int i = 0; i < v.length; i++) {
            v[i][0] = 0;//将第一列设置为0
        }

        //填表：动态规划
        for (int i = 1; i < v.length; i++) {
            for (int j = 1; j < v[0].length; j++) {
                if(weights[i -1] > j){//如果第i-1个物品的重量大于背包的容量
                    v[i][j] = v[i - 1][j];//则此时背包存储最大价值的最优解等于上一行的最优解
                }else{//如果第i-1个物品的重量不大于背包的容量
                    v[i][j] = Math.max(v[i - 1][j], values[i - 1] + v[i - 1][j - weights[i - 1]]);
                    //为了记录背包存放物品的情况，将上一行公式改为if-else
//                    if(v[i - 1][j] < values[i - 1] + v[i - 1][j - weights[i - 1]]){
//                        v[i][j] = values[i - 1] + v[i - 1][j - weights[i - 1]];
//                        path[i][j] = 1;//记录背包存放物品情况
//                    }else{
//                        v[i][j] = v[i - 1][j];
//                    }
                }
            }
        }

        //显示不同容量背包的最佳存储方案
        //显示表格：
        for(int[] item : v){
            System.out.println(Arrays.toString(item));
        }

        //确定哪些物品放入了背包
        boolean[] isAdd = new boolean[n+1];

        for (int i = n; i > 0; i--) {
            if(v[i][m] == v[i-1][m]){
                isAdd[i] = false;//第i个物品没有放入背包
            }else {
                isAdd[i] = true;
                m -= weights[i-1];//第i个物品放入了背包
            }
        }

        //输出背包最佳存储方案
        for (int i = 1; i < isAdd.length; i++) {
            if(isAdd[i]){
                System.out.printf("将第%d个商品放入背包\n", i);
            }
        }

//        //输出容量为4的背包的最终存储方案，逆序查找
//        int i = path.length - 1;
//        int j = path[0].length - 1;
//        while(i > 0 && j > 0){
//            if(path[i][j] == 1){
//                System.out.printf("将第%d个商品放入背包\n", i);
//                j -= weights[i-1];
//            }
//            i--;
//        }

    }
}
