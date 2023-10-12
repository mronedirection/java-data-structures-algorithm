package com.renhao.recursion;

/**
 * @author RenHao
 * @create 2022-09-22 21:47
 */
public class RecursionTest {
    public static void main(String[] args) {

        //通过打印问题，回顾递归调用机制
//        test(4);

        System.out.println(factorial(3));

    }

    //打印问题
    //n=2
    //n=3
    //n=4
    private static void test(int n) {
        if(n > 2){
            test(n - 1);
        }//else{
            System.out.println("n=" + n);
        //}
    }

    //阶乘问题
    public static int factorial(int n){
        if(n == 1){
            return 1;
        }else{
            return factorial(n - 1) * n;
        }
    }
}
