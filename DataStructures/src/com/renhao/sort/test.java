package com.renhao.sort;

import java.util.*;

/**
 * @author RenHao
 * @create 2022-09-15 19:06
 */
public class test {

    public static void main(String[] args) {
//        System.out.println(fion(9));
//        ArrayList<Integer> integers = new ArrayList<>();


//        int[][] arr = new int[][]{{1,2},{3,1},{2,4},{2,3},{4,4}};
//        int[][] arr = new int[][]{{3,4}};
//        int length = arr.length;

        //[4,1,2]
        //[1,3,4,2]
//        int[] arr1 = new int[]{4,1,2};
//        int[] arr2 = new int[]{1,3,4,2};
//        int[] nextGreaterElement = nextGreaterElement(arr1, arr2);
//        System.out.println(Arrays.toString(nextGreaterElement));

//        int[] arr1 = new int[]{1,4,2,5,3};
//        int i = sumOddLengthSubarrays(arr1);
//        System.out.println(i);

//        int[] arr1 = new int[]{0,1,0,3,12};
//        moveZeroes(arr1);
//        StringBuilder builder = new StringBuilder();
//        String str = "abcde";
//        System.out.println(str.substring(1, 3));

//        String s = "abcd";
//        String t = "abcde";
////        interpret(str);
//        findTheDifference(s, t);

//        String[] words = new String[]{"word","world","row"};
//        String order = "worldabcefghijkmnpqstuvxyz";
//        isAlienSorted(words, order);
//
//        ArrayList<Integer> list = new ArrayList<>();
//
//        Collections.sort(list, new Comparator<Integer>() {
//
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                return Integer.compare(o1, o2);
//            }
//        });

        System.out.println(climbStair(10));

    }

    public static boolean isAlienSorted(String[] words, String order) {
        int[] index = new int[26];
        for (int i = 0; i < order.length(); ++i) {
            index[order.charAt(i) - 'a'] = i;
        }
        for (int i = 1; i < words.length; i++) {
            boolean valid = false;
            for (int j = 0; j < words[i - 1].length() && j < words[i].length(); j++) {
                int prev = index[words[i - 1].charAt(j) - 'a'];
                int curr = index[words[i].charAt(j) - 'a'];
                if (prev < curr) {
                    valid = true;
                    break;
                } else if (prev > curr) {
                    return false;
                }
            }
            if (!valid) {
                /* 比较两个字符串的长度 */
                if (words[i - 1].length() > words[i].length()) {
                    return false;
                }
            }
        }
        return true;
    }


    public static char findTheDifference(String s, String t) {
        int ret = 0;
        for (int i = 0; i < s.length(); ++i) {
            ret ^= s.charAt(i);
        }
        for (int i = 0; i < t.length(); ++i) {
            ret ^= t.charAt(i);
        }
        return (char) ret;
    }

    public static int fion(int n){
        if(n == 1 || n == 2){
            return n;
        }else{
            return fion(n - 1) + fion(n - 2);
        }
    }

    public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        Deque<Integer> stack = new ArrayDeque<Integer>();
        for (int i = nums2.length - 1; i >= 0; --i) {
            int num = nums2[i];
            while (!stack.isEmpty() && num >= stack.peek()) {
                stack.pop();
            }
            map.put(num, stack.isEmpty() ? -1 : stack.peek());
            stack.push(num);
        }
        int[] res = new int[nums1.length];
        for (int i = 0; i < nums1.length; ++i) {
            res[i] = map.get(nums1[i]);
        }
        return res;
    }

    public static int sumOddLengthSubarrays(int[] arr) {
        //求前缀和
        int arrLength = arr.length;//存储数组长度
        int sum = 0;//存储总和
        int[] prefixSum = new int[arrLength + 1];
        for(int i = 0; i < arrLength; i++){
            prefixSum[i + 1] = prefixSum[i] + arr[i];
        }
        //根据前缀和，求所有奇数长度子数组的和
        for(int start = 0; start < arrLength; start++){
            for(int length = 1; (start + length) <= prefixSum.length; length += 2){
                sum += prefixSum[start + length] - prefixSum[start];
            }
        }
        return sum;
    }

    public static void moveZeroes(int[] nums) {
        int n = nums.length, left = 0, right = 0;
        while (right < n) {
            if (nums[right] != 0) {
                swap(nums, left, right);
                left++;
            }
            right++;
        }
    }

    public static void swap(int[] nums, int left, int right) {
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
        Arrays.stream(new int[]{1,2,3}).sum();

    }

    public static String interpret(String command) {
        StringBuilder builder = new StringBuilder();
        int index = 0;//辅助遍历字符串
        while(index < command.length()){
            if(command.charAt(index) == 'G'){
                builder.append("G");
                index++;
            }
            if(command.charAt(index) == '(' && command.charAt(index+1) == ')'){
                builder.append("o");
                index += 2;
            }
            if(command.charAt(index) == '(' && command.charAt(index+1) != ')'){
                builder.append("al");
                index += 4;
            }
        }
        return builder.toString();//builder转String
    }

    public static int climbStair(int n){
        if(n == 1){
            return 1;
        } else if (n == 2) {
            return 2;
        }
        return climbStair(n-1) + climbStair(n-2);
    }

}
