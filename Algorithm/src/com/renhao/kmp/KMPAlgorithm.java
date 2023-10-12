package com.renhao.kmp;

import java.util.Arrays;

/**
 * @author RenHao
 * @create 2022-11-07 10:23
 */
public class KMPAlgorithm {
    public static void main(String[] args) {
        String str1 = "BBC ABCDAB ABCDABCDABDE";
        String str2 = "ABCDABD";
        int[] next = getNext(str2);
        System.out.println("next = " + Arrays.toString(next));
        int index = KMPSearch(str1, str2, next);
        System.out.println("index = " + index);
    }

    //KMP算法
    /**
     * 判断str1中是否包含str2
     * @param str1 主串
     * @param str2 模式串
     * @param next 部分匹配值表
     * @return 第一次匹配到的开始索引；如果未找到，则返回-1
     */
    public static int KMPSearch(String str1, String str2, int[] next){
        for (int i = 0, j = 0; i < str1.length(); i++) {
            //KMP的核心
            //如果未匹配到，则需要利用部分匹配值表来重置j
            while(j > 0 && str1.charAt(i) != str2.charAt(j)){
                j = next[j-1];
            }
            //如果匹配到，则继续向后匹配
            if(str1.charAt(i) == str2.charAt(j)){
                j++;
            }
            //每次匹配完，判断是否匹配到模式串的串尾
            if(j == str2.length()){
                return i - j + 1;
            }
        }
        return -1;
    }

    //获取模式串的部分匹配值表 ABCDABD
    public static int[] getNext(String dest){
        int[] next = new int[dest.length()];//存储部分匹配值
        next[0] = 0;//当模式串长度为1时，部分匹配值为0
        for (int i = 1, j = 0; i < dest.length(); i++) {
            //KMP的核心
            //如果未匹配到，需要重置j
            while(j > 0 && dest.charAt(i) != dest.charAt(j)){
                j = next[j-1];
            }

            //当匹配到时，为next数组赋值
            if(dest.charAt(i) == dest.charAt(j)){
                next[i] = ++j;
            }
        }
        return next;
    }

}
