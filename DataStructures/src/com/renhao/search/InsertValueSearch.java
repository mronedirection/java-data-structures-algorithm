package com.renhao.search;


/**
 * @author RenHao
 * @create 2022-10-12 12:22
 */
public class InsertValueSearch {
    public static void main(String[] args) {
//        int arr[] = { 1, 8, 10, 89,1000,1000, 1234 };
        int arr[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 , 11, 12, 13,14,15,16,17,18,19,20 };

        int resIndex = insertValueSearch(arr, 0, arr.length - 1, 1);


        System.out.println("resIndex = " + resIndex);
    }

    /*
    插值排序：对二分查找的优化
    int mid = left + (findValue - arr[left]) / (arr[right] - arr[left]) * (right - left)
     */

    /**
     *
     * @param arr 待查找数组
     * @param left 开始索引
     * @param right 结束索引
     * @param findValue 待查找数
     * @return 待查找数在数组中的索引
     */
    private static int insertValueSearch(int[] arr, int left, int right, int findValue){
        //待查找数组按升序排列
        //注意：findValue < arr[0] || findValue > arr[arr.length - 1]必须需要
        //否则我们得到的mid可能越界
        if(left > right || findValue < arr[0] || findValue > arr[arr.length - 1]){
            return -1;//未找到，返回-1
        }

        int mid = left + (findValue - arr[left]) / (arr[right] - arr[left]) * (right - left);//与二分查找的不同点
        int midValue = arr[mid];

        if(findValue > midValue){//向右递归
            return insertValueSearch(arr, mid + 1, right, findValue);
        } else if (findValue < midValue) {//向左递归
            return insertValueSearch(arr, left, mid - 1, findValue);
        }else{
            return mid;
        }
    }
}
