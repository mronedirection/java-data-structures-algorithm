package com.renhao.search;

/**
 * @author RenHao
 * @create 2022-10-11 15:23
 */
public class SeqSearch {

    public static void main(String[] args) {
        int[] arr = {1, 9, 11, -1, 34, 89};
        int index = seqSearch(arr, 11);
        if(index != -1){
            System.out.println("找到该元素，索引为：" + index);
        }else{
            System.out.println("未找到该元素");
        }

    }

    //线性查找是逐一比对，发现有相同值，就返回下标
    private static int seqSearch(int[] arr, int value){
        for (int i = 0; i < arr.length; i++) {
            if(value == arr[i]){
                return i;
            }
        }
        return -1;
    }



}
