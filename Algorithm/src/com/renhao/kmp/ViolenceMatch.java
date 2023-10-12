package com.renhao.kmp;

/**
 * @author RenHao
 * @create 2022-10-31 16:42
 */
public class ViolenceMatch {
    public static void main(String[] args) {
        //测试暴力匹配算法
        String str1 = "硅硅谷 尚硅谷你尚硅 尚硅谷你尚硅谷你尚硅你好";
        String str2 = "尚硅谷你尚硅你";
        int index = violenceMatch(str1, str2);
        System.out.println("index=" + index);
    }

    //暴力匹配算法
    //判断str1中是否包含str2
    public static int violenceMatch(String str1, String str2){
        //将两个字符串拆分为数组
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        //暴力匹配
        int i, j;//i指向s1中当前索引，j指向s2中当前索引
        i = j = 0;
        while(i < s1.length && j < s2.length){//保证匹配时不越界
            if(s1[i] == s2[j]){//如果匹配到，则i和j同时向后移一位，继续匹配
                i++;
                j++;
            }else{//如果没有匹配到，则i向后移一位，j回到索引为0的位置，重新匹配
                i = i - (j - 1);
                j = 0;
            }
        }
        //匹配完成后，判断s1中是否包含s2
        if(j == s2.length){//包含
            return i - j;//返回首次出现时的索引
        }else{
            return -1;//不包含，返回-1
        }
    }

}
