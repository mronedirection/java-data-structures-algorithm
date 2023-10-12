package com.renhao.sort;

/**
 * @author RenHao
 * @create 2022-09-27 14:58
 */
public class SelectSort {
    public static void main(String[] args) {

        //初始化一个待排序的数组
//        int[] arr = new int[]{3, 9, -1, 10, 20};//太短了，花费的时间太少
        //创建长度为80000的待排序数组
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int)(Math.random() * 80000);
        }
        //记录排序前时间
        long start = System.currentTimeMillis();
        //选择排序
        selectSort(arr);
        //记录排序后时间
        long end = System.currentTimeMillis();
        //输出排序后数组
        System.out.println("选择排序花费时间：" + (end - start) + "ms");//1470ms
//        System.out.println(Arrays.toString(arr));

    }

    //选择排序的时间复杂度为O(n^2)
    /*
    选择排序思想：
    对于一个长度为n的数组，如果按照从小到大的顺序排列；
    将数组看作一个有序数组和无序数组，每一轮选取无序数组的第一个数作为初始最小值，遍历无序数组，查找无序数组中的最小值，放至有序数组末尾；
    共需遍历n-1轮
     */
    private static void selectSort(int[] arr) {
        int min = 0;//存放最小值
        int minIndex = 0;//存放最小值索引
        for (int i = 0; i < arr.length - 1; i++) {//共需查找n-1轮
            min = arr[i];
            minIndex = i;
            for (int j = i+1; j < arr.length - 1; j++) {//每次需要遍历
                if(min > arr[j]){
                    min = arr[j];
                    minIndex = j;
                }
            }
            //将找到的最小值与第i个元素交换
            if(minIndex != i){
                arr[minIndex] = arr[i];
                arr[i] = min;
            }
//            System.out.println("第" + (i+1) + "次排序后的数组为：" + Arrays.toString(arr));
        }
    }

}
