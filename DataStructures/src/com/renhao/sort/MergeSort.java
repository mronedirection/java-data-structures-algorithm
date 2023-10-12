package com.renhao.sort;

/**
 * @author RenHao
 * @create 2022-10-10 15:07
 */
public class MergeSort {
    //使用递归将无序数组先拆分，再对无序数组按照顺序进行合并
    public static void main(String[] args) {
        int arr[] = { 8, 4, 5, 7, 1, 3, 6, 2};

//        int[] arr = new int[80000];
//        for (int i = 0; i < 80000; i++) {
//            arr[i] = (int) (Math.random() * 80000);
//        }

        long start = System.currentTimeMillis();
        int[] temp = new int[arr.length];//辅助存储有序数据的数组
        mergeSort(arr, 0, arr.length - 1, temp);
        long end = System.currentTimeMillis();

        System.out.println("归并排序所花费的时间为：" + (end - start) + "ms");//8ms
//        System.out.println(Arrays.toString(arr));

    }

    /*
    归并排序核心思想：分治，先拆分再合并
    时间复杂度:O(nlogn)
     */

    /**
     * 使用递归进行分治
     * @param arr 待排序数组
     * @param left 开始索引，数组开头
     * @param right 结束索引，数组末尾
     * @param temp 辅助存储有序数据的数组，与待排序数组长度相等的数组
     */
    public static void mergeSort(int[] arr, int left, int right, int[] temp){
        if(left >= right){
            return;
        }
        //使用递归将数组拆分成单个元素，再调用合并算法，进行合并
        int middle = (left + right) / 2;
        //递归middle左边的数组
        mergeSort(arr, left, middle, temp);
        //递归middle右边的数组
        mergeSort(arr, middle + 1, right, temp);
        //拆分完成，调用合并算法，对middle左右两侧的数组有序合并，八个数据共合并七轮
        //(0, 0, 1) --> (2, 2, 3) --> (0, 1, 3) --> (4, 4, 5) --> (6, 6, 7) --> (4, 5, 7) --> (0, 3, 7)
        merge(arr, left, middle, right, temp);
    }

    /**
     * 合并
     * 使用双指针遍历中间索引两侧的无序数组，并将其填在辅助数组temp中
     * @param arr 每一轮拆分后的待合并数组
     * @param left 待合并数组的的初始索引
     * @param mid 待合并数组的中间索引
     * @param right 待合并数组的结束索引
     * @param temp 辅助存储有序数据的数组，与待排序数组长度相等的数组
     */
    public static void merge(int[] arr, int left, int mid, int right, int[] temp){

        int i = left;//mid左侧数组的开始索引
        int j = mid + 1;//mid右侧数组的开始索引
        int t = 0;//temp数组的当前索引

        //将mid左右两侧的数据按照顺序填充到temp数组，直到左右两边的数组，有一边处理完毕为止
        while(i <= mid && j <= right){
            if(arr[i] <= arr[j]){
                temp[t++] = arr[i++];
            }else{
                temp[t++] = arr[j++];
            }
        }

        //把有剩余数据的一侧数据依次填充到temp中
        while(i <= mid){
            temp[t++] = arr[i++];
        }
        while(j <= right){
            temp[t++] = arr[j++];
        }

        //将temp中的元素按照left --> right的角标顺序拷贝到arr中
        t = 0;//temp数组的当前索引
        int tempLeft = left;//arr数组中的开始索引
        while(tempLeft <= right){//right:arr数组中的结束索引
            arr[tempLeft++] = temp[t++];
        }

    }


}
