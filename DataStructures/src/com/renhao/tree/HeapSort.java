package com.renhao.tree;

/**
 * @author RenHao
 * @create 2022-10-20 10:21
 */
public class HeapSort {
    public static void main(String[] args) {
        //测试
//        int[] arr = {4, 6, 8, 5, 9, -1, 90, 89, 56, -999};
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int)(Math.random() * 80000);
        }

        long start = System.currentTimeMillis();
        heapSort(arr);
        long end = System.currentTimeMillis();

        System.out.println("堆排序时间：" + (end - start) + "ms");//6ms
//        System.out.println(Arrays.toString(arr));

    }

    //推排序方法：时间复杂度O(nlogn)
    /*
    1)构造初始堆，将给定无序序列构造成一个大顶堆（一般升序采用大顶堆，降序采用小顶堆）
    2)将堆顶元素与末尾元素交换，使末尾元素最大
    3）继续调整数组中剩余元素为大顶堆
    重复步骤2)-3),直至交换完成
     */
    public static void heapSort(int[] arr){
        //先将无序数组构成大顶堆
        //从最后一个非叶子节点调整至第一个非叶子节点
        for(int i = arr.length / 2 - 1; i >= 0; i--){
            adjustHeap(arr, i, arr.length);
        }
        //调整完成后：{4, 6, 8, 5, 9, -1, 90, 89, 56, -999} -> {90, 89, 8, 56, 9, -1, 4, 5, 6, -999}
        //交换堆顶元素和末尾元素，然后继续调整数组中剩余元素为大顶堆
        //n个元素，共需比较n-1轮
        int temp = 0;//辅助交换
        for (int i = arr.length - 1; i > 0; i--) {
            //交换堆顶元素和末尾元素
            temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;
            //交换完成后，堆顶最大的90已成功归位，{-999, 89, 8, 56, 9, -1, 4, 5, 6, 90}，继续调整前面9个元素依次归位即可
            //由于第一轮调整过，根节点以下都为大顶堆，因此不用再写循环多次调整，只用调整一次即可
            adjustHeap(arr, 0, i);//length逐渐递减 10 -> 9 -> 8 ...
        }

    }

    //将一个数组，调整成一个大顶堆
    //大顶堆：每个节点的值都大于等于左右孩子节点的值；
    //小顶堆：每个节点的值都小于等于左右孩子节点的值；
    /*
    1)首先找到数组中的最后一个非叶子节点，逆序调整至数组中第一个非叶子节点为止
    //叶子节点：没有孩子节点的子节点；非叶子节点：有孩子节点的子节点；
    2)调整思路：保证父节点的值大于或等于左右子节点的值
     */
    /**
     *
     * @param arr 待调整数组
     * @param i 非叶子节点在数组中的索引
     * @param length 待调整元素的个数
     */
    public static void adjustHeap(int[] arr, int i, int length){
        int temp = arr[i];//先取出当前元素的值，保存在临时变量，辅助交换
        //arr[i]的左子节点为arr[2 * i + 1]
        for(int k = 2 * i + 1; k < length; k = 2 * k + 1){
            //arr[i]的右子节点为arr[2 * i + 2]
            if(k + 1 < length && arr[k] < arr[k + 1]){//如果左子节点的值 < 右子节点的值
                k++;//让k指向右子节点
            }
            if(arr[k] > temp){//交换，注意此处每一轮都是和temp比，而不是arr[i]比，如果和arr[i]比会导致下面某一个子树可能不是大顶堆
                arr[i] = arr[k];//将较大元素提上去
                i = k;//让i后移，等待下一个节点与其比较
            }else{
                break;//如果arr[k] <= arr[i],则arr[2 * k + 1] <= arr[k] <= arr[i]
            }
        }
        //比较完成后，完成父子节点的交换
        arr[i] = temp;//如果循环中发生交换，此时i指向k,相当于将arr[k] = temp
    }

}
