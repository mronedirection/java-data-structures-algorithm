package com.renhao.stack;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 使用数组模拟栈
 * 1.定义一个top表示栈顶，初始化为-1
 * 2.入栈的操作，当有数据加入到栈时，top++;stack[top]=data;
 * 3.出栈的操作，int value = stack[top];top--;return value;
 * @author RenHao
 * @create 2022-09-15 10:31
 */
public class ArrayStackDemo {

    public static void main(String[] args) {

        //测试
        //先创建一个ArrayStack对象 -> 表示栈
        ArrayStack stack = new ArrayStack(4);//栈的容量为4
        boolean loop = true;//控制是否退出菜单
        String key = null;//控制菜单选择
        Scanner scanner = new Scanner(System.in);

        while(loop){
            System.out.println("show: 表示显示栈");
            System.out.println("exit: 退出程序");
            System.out.println("push: 表示添加数据到栈(入栈)");
            System.out.println("pop: 表示从栈取出数据(出栈)");
            System.out.print("请输入你的选择：");
            key = scanner.next();
            switch (key){
                case "show":
                    stack.list();
                    break;
                case "exit":
                    loop = false;//控制循环标志位置为false, 结束循环
                    System.out.println("退出成功");
                    break;
                case "push":
                    System.out.print("请输入一个整数：");
                    int data = scanner.nextInt();
                    stack.push(data);
                    break;
                case "pop":
                    try {
                        int popData = stack.pop();
                        System.out.println("出栈的数据是：" + popData);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
            }
        }
    }
}

//定义一个ArrayStack表示栈
class ArrayStack{

    //属性
    private int maxSize;//栈的大小
    private int[] stack;//数组，模拟栈，数据就存放在数组中
    private int top = -1;//top表示栈顶，初始化为-1

    //构造器
    public ArrayStack(int maxSize){
        this.maxSize = maxSize;//指定栈的最大容量
        stack = new int[this.maxSize];//初始化数组
    }

    //栈满
    public boolean isFull(){
        return top == maxSize - 1;//栈顶的指针到达数组最顶部
    }

    //栈空
    public boolean isEmpty(){
        return top == -1;//栈顶指针位于数组最底部的下一个
    }

    //入栈-push
    public void push(int value){
        if(isFull()){
            System.out.println("栈已满，无法添加");
            return;
        }
        stack[++top] = value;//将value添加入数组，同时将指针向上移动
        System.out.println("入栈成功");
    }

    //出栈-pop, 将栈顶的数据返回
    public  int pop(){
        if(isEmpty()){
            throw new RuntimeException("栈已空，无法出栈");
        }
        return stack[top--];//返回栈顶的数据，同时将指针下移
    }

    //显示栈的情况[遍历栈]，遍历时，需要从栈顶开始显示数据
    public void list(){
        if(isEmpty()){
            System.out.println("栈为空");
            return;
        }
        for (int i = top; i >= 0; i--) {//从栈顶遍历到栈底
            System.out.printf("stack[%d] = %d\n", i, stack[i]);
        }
    }
    public boolean areAlmostEqual(String s1, String s2) {
        //如果s1和s2字符不相等的索引数目不等于0或2则返回false
        int num = 0;//保存字符不相等的索引数目
        for(int i = 0; i < s1.length(); i++){
            if(s1.charAt(i) != s2.charAt(i)) num++;
        }
        //2.判断s1和s2是否相同，不考虑先后顺序
        s1.toCharArray();
        Arrays.sort(s1.toCharArray());
        Arrays.sort(s2.toCharArray());
        if((num == 0 || num == 2) && s1.equals(s2)) return true;
        return false;
    }
}
