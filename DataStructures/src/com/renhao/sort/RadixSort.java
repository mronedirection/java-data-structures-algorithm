package com.renhao.sort;

/**
 * @author RenHao
 * @create 2022-10-10 22:18
 */
public class RadixSort {
    public static void main(String[] args) {
//        int arr[] = { 53, 3, 542, 748, 14, 214};

        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int)(Math.random() * 80000);
        }

        long start = System.currentTimeMillis();
        radixSort(arr);
        long end = System.currentTimeMillis();

        System.out.println("基数排序花费的时间为：" + (end - start) + "ms");//20ms

//        System.out.println(Arrays.toString(arr));
    }

    /*
    基数排序：只能对正数进行排序
    根据数组中每个元素各个数位上的值进行排序，从个位开始一直到最高位；
    时间复杂度为O(n*k), k是最大元素的位数；
     */
    private static void radixSort(int[] arr){
        /*
        先造10个桶
        根据每个元素的各个数位，向每个桶中放数据，同时记录每个桶中每一轮存放的数据个数，待会取数据时要用
        取完数据后，记录每个桶中数据个数的数组要置0
        放数据 --> 取数据 --> 放数据
         */
        //求出待排序数组中最大元素的位数，用于确定需要取几轮
        int max = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] > max){
                max = arr[i];
            }
        }
        int maxLength = (max + "").length();//最大元素的位数
        int[][] bucket = new int[10][arr.length];//建10个桶，每个桶的初始长度为待排序数组的长度，以免溢出
        int[] bucketElementCounts = new int[10];//记录每个桶中存放的数据个数，桶中取完数据后，要置零
        int index;//向数组中存数据时需要用到的索引
        int digitOfElement;//取数位
        //向桶中放数据 --> 放完后取数据
        for (int i = 0, n = 1; i < maxLength; i++, n *= 10) {//共存取maxLength轮
            //入桶
            for (int j = 0; j < arr.length; j++) {//遍历待排序数组
                digitOfElement = arr[j] / n % 10;//取数位，第一次取个位，第二次取十位，第三次取百位...
                bucket[digitOfElement][bucketElementCounts[digitOfElement]++] = arr[j];//存入桶
            }
            //出桶
            index = 0;//向数组中存数据时需要用到的索引

            for (int j = 0; j < bucket.length; j++) {//从第一个桶开始遍历桶
                for (int k = 0; k < bucketElementCounts[j]; k++) {//遍历每个桶中的数据
                    arr[index++] = bucket[j][k];//从桶中取出数据存入待排序数组中
                }
                //取完数据后将bucketElementCounts置零
                bucketElementCounts[j] = 0;
            }
        }
    }
}
