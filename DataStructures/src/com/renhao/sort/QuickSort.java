package com.renhao.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * @author RenHao
 * @create 2022-10-09 19:39
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] nums = new int[]{3,2,1,5,6,4};
//        int[] nums = new int[]{2,1};
//        quickSort(nums, 0, nums.length - 1);
        quickSortOptimize(nums, 0, nums.length - 1);
        System.out.println(Arrays.toString(nums));

////        int[] arr = {-9, 78, 0, 23, -567, 70, -1, 900, 4561};
//        int[] arr = new int[80000];
//        for (int i = 0; i < 80000; i++) {
//            arr[i] = (int)(Math.random() * 80000);
//        }
//        long start = System.currentTimeMillis();
//        quickSort(arr, 0, arr.length - 1);
//        long end = System.currentTimeMillis();
//        System.out.println("快速排序所耗费时间为：" + (end - start) + "ms");//20ms
////        System.out.println(Arrays.toString(arr));
    }

    /*
    以最左边的数为基准数：
    通过两个索引：一个从左至右遍历，查找大于基准数的元素，一个从右至左遍历，查找小于基准数的元素；
    将大于基准数的元素放在基准数的右边，将小于基准数的元素放在基准数的左边；
    当两个索引的位置重复时，将基准数交换至此位置；
    最后分别递归基准数左右两边的数组；
     */

    //对quickSort的优化，随机选取开始索引
    private static void quickSortOptimize(int[] arr, int begin, int end){
        if(begin > end){
            return;
        }
        int i = begin, j = end;//左右指针
        Random random = new Random();
        int targetIndex = random.nextInt(end - begin + 1) + begin;//随机选取起始索引
        int target = arr[targetIndex];//起始值
        //重点1
        arr[targetIndex] = arr[begin];//交换
        while(i < j){
            while(i < j && arr[j] >= target){j--;}
            //重点2
            arr[i] = arr[j];
            while(i < j && arr[i] <= target){i++;}
            //重点3
            arr[j] = arr[i];
        }
        //重点4
        arr[i] = target;//交换
        quickSortOptimize(arr, begin, i - 1);//向左递归
        quickSortOptimize(arr, i + 1, end);//向右递归
    }

    private static void quickSort(int[] arr, int begin, int end){
        if(begin > end){//不能忘记加此行
            return;
        }
        int i = begin;//左索引
        int j = end;//右索引
        int temp = arr[begin];//选取最左边的数作为基准数，此时必须先从j开始查找
        while(i < j){//可改为i != j，i < j最好
            while(arr[j] >= temp && i < j){
                j--;
            }
            while(arr[i] <= temp && i < j){
                i++;
            }
            if(i < j){//如果i和j还没有相遇，则交换i和j指向的元素的位置
                int t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
            }
        }
        //当i == j时，将基准数交换至此位置
        arr[begin] = arr[i];
        arr[i] = temp;
        //递归
        quickSort(arr, begin, i - 1);//对基准数左侧数组使用快速排序
        quickSort(arr, i + 1, end);//对基准数右侧数组使用快速排序
    }

}
