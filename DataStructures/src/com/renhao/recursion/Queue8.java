package com.renhao.recursion;

import java.util.Arrays;

/**
 * @author RenHao
 * @create 2022-09-26 15:01
 */
public class Queue8 {

    //定义max表示共有多少个皇后
    int max = 8;
    //定义数组array，保存皇后位置存放情况，比如 arr = {0, 4, 7, 5, 2, 6, 1, 3}
    int[] array = new int[max];
    static int count;//统计共有多少种摆法
    static int countJudge;//统计共判断了多少次


    public static void main(String[] args) {
        Queue8 queue8 = new Queue8();
        queue8.check(0);
        System.out.printf("一共有%d种方法", count);
        System.out.println();
        System.out.printf("一共判断了%d次", countJudge);


    }

    //编写一个方法，放置第n个皇后
    private void check(int n){
        //判断是否已经放完8个皇后，如果放置完毕，则调用print()方法，输出8个皇后的位置
        if(n == max){
            count++;
            System.out.println(Arrays.toString(array));//8个皇后放置完毕，打印array数组
            return;
        }
        //未放置完8个皇后，开始放置皇后
        for (int i = 0; i < max; i++) {
            array[n] = i;//从第一列开始判断，将第n行的皇后放置在哪一列无冲突
            if(judge(n)){//如果可以放置，则判断下一行的皇后可以放置在哪一列
                check(n + 1);
            }
            //如果不能放置，则将该行的皇后移动到下一列
        }

    }

    //当我们放置第n个皇后时，去判断该皇后是否和前面已经摆放的皇后冲突
    private boolean judge(int n){
        countJudge++;
        for (int i = 0; i < n; i++) {//判断当前放置的皇后与之前放置的皇后是否有冲突（同行或同列或同斜线）
            if(array[i] == array[n] || Math.abs(n - i) == Math.abs(array[i] - array[n])){
                return false;
            }
        }
        return true;//判断完无冲突，可以放置
    }

//    //调用此方法，将皇后摆放的位置输出
//    private void print(){
//        for (int i = 0; i < max; i++) {
//            System.out.println(array);
//        }
//    }

}
