package com.renhao.sort;

/**
 * @author RenHao
 * @create 2022-09-27 17:18
 */
public class InsertSort {
    public static void main(String[] args) {
        //初始化一个待排序的数组
//        int[] arr = new int[]{3, 9, -1, 10, 20};//太短了，花费的时间太少
        //创建长度为80000的待排序数组
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int)(Math.random() * 80000);//[0.0, 1.0)
        }
        //记录排序前时间
        long start = System.currentTimeMillis();
        //选择排序
        insertSort(arr);
        //记录排序后时间
        long end = System.currentTimeMillis();
        //输出排序后数组
        System.out.println("选择排序花费时间：" + (end - start) + "ms");//316ms
//        System.out.println(Arrays.toString(arr));

    }

    /*
    将原数组看作一个由有序数组，和无序数组组成的数组；
    依次取无序数组中的元素，与有序数组中的元素比较，查找待插入位置；
    查找方法：遍历有序数组，遇到比待插入元素大的元素，则将其在有序数组中后移一位，直到遍历到最后一位或者遇到比待插入元素小的元素，则将待插入元素插入到该元素后面；
     */
    private static void insertSort(int[] arr) {
        int insertValue = 0;
        int insertIndex = 0;
        for (int i = 1; i < arr.length; i++) {//依次取无序数组中的元素[1, n-1]
            insertValue = arr[i];//待插入数据（从第一个开始找起）
            insertIndex = i - 1;//辅助查找待插入位置
            while(insertIndex >= 0 && insertValue < arr[insertIndex]){//升序排序
                //如果不满足条件
                arr[insertIndex + 1] = arr[insertIndex];//将待插入数据的左边元素后移一位
                insertIndex--;//将待插入数据与左边下个元素进行比较，直到找到第一个元素或者找到第一个不大于insertValue的元素，则将其插在该元素后面
            }
            //循环执行完毕后，代表已经找到要插入的位置
            if((insertIndex + 1) != i){//如果相等，则不需要移动待插入元素的位置
                arr[insertIndex + 1] = insertValue;//将insertValue插入其后，开始查找下一个待插入元素的位置
            }
//            System.out.println("第" + (i) + "次排序后的数组为：" + Arrays.toString(arr));
        }
    }


}
