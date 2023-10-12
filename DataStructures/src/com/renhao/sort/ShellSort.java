package com.renhao.sort;

/**
 * @author RenHao
 * @create 2022-10-09 10:17
 */
public class ShellSort {
    public static void main(String[] args) {
//        int[] arr = new int[]{8, 9, 1, 7, 2, 3, 5, 4, 6, 0};

        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int)(Math.random() * 80000);
        }

        long start = System.currentTimeMillis();
        shellSort2(arr);
        long end = System.currentTimeMillis();
        System.out.println("ShellSort排序花费时间：" + (end - start) + "ms");//交换式4307ms --> 移位式12ms
//        System.out.println(Arrays.toString(arr));

    }

    //希尔排序（交换）
    //希尔排序时，对有序序列在插入时采用交换法
    private static void shellSort(int[] arr) {
        int length = arr.length;//保存数组长度
        int temp = 0;//辅助交换
        for(int gap = length / 2; gap > 0; gap /= 2){//控制轮数(gap:length / 2 --> 1)
            for (int i = gap; i < length; i++) {//i:gap --> length-1
                for (int j = i - gap; j >= 0; j -= gap) {//倒序比较，每比较一轮就排好一次序
                    if(arr[j] > arr[j + gap]){
                        temp = arr[j];
                        arr[j] = arr[j + gap];
                        arr[j + gap] = temp;
                    }
                }
            }
        }
    }

    //希尔排序（移位）
    //希尔排序时，对有序序列在插入时采用移位法
    private static void shellSort2(int[] arr){
        int length = arr.length;//保存数组长度
        int insertIndex = 0;
        int insertValue = 0;
        for(int gap = length / 2; gap > 0; gap /= 2){
            for (int i = gap; i < length; i++) {
                insertValue = arr[i];//插入值
                insertIndex = i - gap;//插入位置
                if(insertValue < arr[insertIndex]){
                    while(insertIndex >= 0 && insertValue < arr[insertIndex]){
                        arr[insertIndex + gap] = arr[insertIndex];
                        insertIndex -= gap;//递减
                    }
                    //看不懂看OneNote
                    arr[insertIndex + gap] = insertValue;//将insertValue插入到有序数组中
                }
            }
        }
    }


//    public static void shellSort1(int[] arr) {
//
//        int temp = 0;
//        int count = 0;
//        // 根据前面的逐步分析，使用循环处理
//        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
//            for (int i = gap; i < arr.length; i++) {
//                // 遍历各组中所有的元素(共gap组，每组有个元素), 步长gap
//                /*
//                下面步骤的原理：倒着比较排序，与冒泡的思路并不一致
//                冒泡思路：每一轮找出最大的元素放置在数组的最后位置
//                下面的思路：最后一个元素可以看作是待排序的元素，需要将待排序的元素插入进已经排好序的数组中，可以采用倒着比较的方法
//                 */
//                for (int j = i - gap; j >= 0; j -= gap) {
//                    // 如果当前元素大于加上步长后的那个元素，说明交换
//                    if (arr[j] > arr[j + gap]) {
//                        temp = arr[j];
//                        arr[j] = arr[j + gap];
//                        arr[j + gap] = temp;
//                    }
//                }
//            }
//            System.out.println("希尔排序第" + (++count) + "轮 =" + Arrays.toString(arr));
//        }
//    }


}
