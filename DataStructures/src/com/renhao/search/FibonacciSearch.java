package com.renhao.search;

import java.util.Arrays;

/**
 * @author RenHao
 * @create 2022-10-13 10:30
 */
public class FibonacciSearch {

    private static int maxSize = 20;//Fibonacci数列的长度

    public static void main(String[] args) {

        int[] arr = {1,8, 10, 89, 1000, 1234};

        int resIndex = fibSearch(arr, 0, arr.length - 1, 1);

        System.out.println("resIndex = " + resIndex);
    }

    //使用非递归的方法产生一个Fibonacci数列
    private static int[] fib(){//长度为maxSize
        int[] fib = new int[maxSize];
        fib[0] = fib[1] = 1;
        for (int i = 2; i < maxSize; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }
        return fib;
    }

    /*
    Fibonacci查找（黄金分割查找）算法：
    利用Fibonacci数列确定数组中位于黄金分割点位置的元素，将其与findValue比较；
    在使用黄金分割查找算法之前，需要先对待查找数组进行预处理：
        将待查找数组扩充为长度为f[maxSize]的数组，不足的部分，用末尾元素补齐
    mid = left + f[maxSize - 1] -1
     */

    /**
     *
     * @param arr 待查找数组
     * @param left 开始索引
     * @param right 结束索引
     * @param findValue 待查找值
     * @return 找到返回索引，未找到返回-1
     */
    private static int fibSearch(int[] arr, int left, int right, int findValue){
        int[] fib = fib();//获取长度为maxSize的Fibonacci数列
        int mid;//查找findValue时位于数组黄金分割点位置的数

        //预处理待查找数组
        //扩充
        int k = 0;//Fibonacci数列中第k个值
        while(right > fib[k] - 1){
            k++;
        }
        //复制（因为fib[k] - 1可能大于right）
        int[] temp = Arrays.copyOf(arr, fib[k]);//不足位用0补充
        //更改末尾添加的0，用arr[right]代替
        if(right < fib[k] - 1){//如果相等，则代表末尾没有不足位，没有补0
            for (int i = right + 1; i < temp.length; i++) {
                temp[i] = temp[right];
            }
        }

        //查找
        while(left <= right){
            mid = left + fib[k - 1] - 1;
            //假设待查找数组按升序排列
            if(findValue < temp[mid]){//向左查找
                right = mid - 1;
                k--;//mid左边有f[k - 1]个数，f[k - 1] - 1 = (f[k - 2] - 1) + (f[k - 3] - 1) + 1 = (f[k - 1 - 1] - 1) + (f[k - 1 - 2] - 1) + 1
            } else if (findValue > temp[mid]) {//向右查找
                left = mid + 1;
                k -= 2;//mid右边有f[k - 2]个数，f[k - 2] - 1 = (f[k - 3] - 1) + (f[k - 4] - 1) + 1 = (f[k - 2 - 1] - 1) + (f[k - 2 - 2] - 1) + 1
            }else{//找到，返回索引
                if(mid <= right){
                    return mid;
                }else{
                    return right;//原数组中最大索引为right
                }
            }
        }
        return -1;//遍历完未找到
    }
}
