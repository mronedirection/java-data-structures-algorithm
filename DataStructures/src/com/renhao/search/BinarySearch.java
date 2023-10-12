package com.renhao.search;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RenHao
 * @create 2022-10-11 16:55
 */
public class BinarySearch {
    public static void main(String[] args) {
//        int arr[] = { 1, 8, 10, 89,1000,1000, 1234 };
        int arr[] = { 1, 1, 3, 4, 5, 6, 7, 8, 9, 10 , 11, 12, 13,14,15,16,17,18,20,20 };

//        int resIndex = binarySearch(arr, 0, arr.length - 1, 1000);
        List resIndex = binarySearch2(arr, 0, arr.length - 1, 20);

        System.out.println("resIndex = " + resIndex);
    }

    //二分查找
    //使用二分查找的前提是 该数组是有序的
    /**
     *
     * @param arr 待查找数组
     * @param left 开始索引
     * @param right 结束索引
     * @param findValue 待查找值
     * @return 如找到，返回索引，否则，返回-1
     */
    private static int binarySearch(int[] arr, int left, int right, int findValue){
        if(left > right || findValue < arr[0] || findValue > arr[arr.length - 1]){
            return -1;//未找到
        }
        int mid = (left + right) / 2;
        int midValue = arr[mid];
        //假设原数组按从小到大排序
        if(findValue > midValue){//向右查找
            return binarySearch(arr, mid + 1, right, findValue);
        } else if (findValue < midValue) {//向左查找
            return binarySearch(arr, left, mid - 1, findValue);
        }else{
            return mid;//每次都比较数组的中间值，如果找到返回索引
        }
    }

    /*
    课后思考题：
     * 课后思考题： {1,8, 10, 89, 1000, 1000，1234} 当一个有序数组中，
	 * 有多个相同的数值时，如何将所有的数值都查找到，比如这里的 1000
	 *
	 * 思路分析
	 * 1. 在找到mid 索引值，不要马上返回
	 * 2. 向mid 索引值的左边扫描，将所有满足 1000， 的元素的下标，加入到集合ArrayList
	 * 3. 向mid 索引值的右边扫描，将所有满足 1000， 的元素的下标，加入到集合ArrayList
	 * 4. 将Arraylist返回
     */
    private static ArrayList<Integer> binarySearch2(int[] arr, int left, int right, int findValue){
        if(left > right || findValue < arr[0] || findValue > arr[arr.length - 1]){
            return new ArrayList<Integer>();//未查找到，返回空List
        }

        int mid = (left + right) / 2;//中间索引
        int midValue = arr[mid];//中间值

        //假设待查找数组按升序排列
        if(findValue > midValue){//向右查找
            return binarySearch2(arr, mid + 1, right, findValue);
        } else if (findValue < midValue) {//向左查找
            return binarySearch2(arr, left, mid - 1, findValue);
        }else{
            ArrayList<Integer> list = new ArrayList<>();//存放相同元素的索引
            list.add(mid);//先把mid放进去
            //判断mid左边是否与带查找数据相同
            int temp = mid - 1;
            while(temp >= 0 && arr[temp] == findValue){
                list.add(temp--);
            }
            //判断mid右边是否与带查找数据相同
            temp = mid + 1;
            while(temp <= arr.length - 1 && arr[temp] == findValue){
                list.add(temp++);
            }
            return list;//查找完毕，返回存放索引的list
        }
    }
}
