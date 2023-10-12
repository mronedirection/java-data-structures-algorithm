package com.renhao.binarysearchnorecursion;

/**
 * 使用非递归的方式实现二分查找
 * @author RenHao
 * @create 2022-10-29 15:28
 */
public class BinarySearchNoRecur {
    public static void main(String[] args) {
        //测试
        int[] arr = {1,3, 8, 10, 11, 67, 100};
        int index = binarySearch(arr, 100);
        System.out.println("index=" + index);//6
    }

    /**
     * 使用非递归的方式实现二分查找
     * @param arr 待查找数组
     * @param target 待查找元素
     * @return 如果找到，返回索引；否则，返回-1
     */
    public static int binarySearch(int[] arr, int target) {

        int left = 0;//左指针
        int right = arr.length - 1;//右指针
        int mid;//中间指针
        while (left <= right) {
            mid = (left + right) / 2;//中间指针
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {//向右查找
                left = mid + 1;
            } else {//向左查找
                right = mid - 1;
            }
        }
        return -1;
    }
}
