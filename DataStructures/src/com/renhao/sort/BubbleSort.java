package com.renhao.sort;

/**
 * @author RenHao
 * @create 2022-09-27 11:29
 */
public class BubbleSort {
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
//        Date date1 = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String date1Str = sdf.format(date1);
//        System.out.println("排序前时间：" + date1Str);
        //冒泡排序
        bubbleSort(arr);
        //记录排序后时间
        long end = System.currentTimeMillis();
        //输出排序后数组
        System.out.println("冒泡排序花费时间：" + (end - start) + "ms");//5609ms
//        System.out.println(Arrays.toString(arr));


    }

    //冒泡排序的时间复杂度O(n^2)
    //冒泡排序思路：
    /*
    对于一个长度为n的数组，如果按照从小到大的顺序排列；
    第一轮：可以依次比较相邻两个元素的大小，将大的元素放在后面，这样就将整个数组中最大的元素放在了最后的位置；
    第二轮：确定第二大的元素；
    ...
    第n-1轮：确定倒数第二大的元素位置；
    外层循环n-1轮；内层循环n-1-i轮（i为外层循环的索引位置）
     */
    public static void bubbleSort(int[] arr){
        int temp = 0;//临时变量，辅助交换
        //优化冒泡排序，增加标志位，减少循环次数
        boolean flag = true;//退出外层循环标志位
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if(arr[j] > arr[j+1]){//升序排列
                    flag = false;//有交换，则继续进行循环
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
//            System.out.println("第" + (i+1) + "次排序后的数组为：" + Arrays.toString(arr));
            if(flag){//上一轮没有发生交换，则退出外层循环
                break;
            }else{
                flag = true;//如果有交换，则重置flag，进行下次判断
            }
        }
    }


}
