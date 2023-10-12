package com.renhao.dac;

/**
 * @author RenHao
 * @create 2022-10-29 17:12
 */
public class Hanoitower {
    public static void main(String[] args) {
        hanoiTower(3, 'A', 'B', 'C');
    }

    public static void hanoiTower(int num, char a, char b, char c){
        if(num == 1){
            //如果n=1，直接将A -> C
            System.out.println("第1个盘：" + a + " -> " + c);
        }else{//如果n > 1
            //1.先将n-1个圆盘从A -> B, 期间要借助于C
            hanoiTower(num - 1, a, c, b);
            //2.将第n个（最大的）圆盘从A -> C
            System.out.println("第" + num + "个盘：" + a + " -> " + c);
            //3.最后将n-1个圆盘从从B -> C, 期间要借助于A
            hanoiTower(num - 1, b, a, c);
        }
    }
}
