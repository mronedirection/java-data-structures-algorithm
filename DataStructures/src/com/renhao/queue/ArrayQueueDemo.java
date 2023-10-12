package com.renhao.queue;

import java.util.Scanner;

/**
 * 使用数组模拟队列
 *
 * @author RenHao
 * @create 2022-09-11 11:05
 */
public class ArrayQueueDemo {

    public static void main(String[] args) {
        //测试
        //创建一个队列
        ArrayQueue queue = new ArrayQueue(3);
        int data = 0;//加入队列的数据
        char key = ' ';//接收用户输入
        Scanner scan = new Scanner(System.in);
        boolean loopFlag = true;
        while(loopFlag){
            System.out.println("s(show):显示队列");
            System.out.println("e(exit):退出程序");
            System.out.println("a(add):添加数据到队列");
            System.out.println("g(get):从队列取出数据");
            System.out.println("h(head):查看队列头的数据");
            System.out.print("请选择：");
            key = scan.next().charAt(0);
            switch(key){
                case 's':
                    queue.showQueue();
                    break;
                case 'e':
                    scan.close();
                    loopFlag = false;
                    System.out.println("退出程序");
                    break;
                case 'a':
                    System.out.print("请输入数据：");
                    data = scan.nextInt();
                    queue.addQueue(data);
                    break;
                case 'g':
                    try {
                        int res = queue.getQueue();
                        System.out.printf("取出的数据是%d \n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try {
                        System.out.println("队列头的数据是：" + queue.showQueueHead());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());;
                    }
                    break;
            }
        }

    }

}

//使用数组模拟队列-编写一个ArrayQueue类
class ArrayQueue {
    //成员变量（数组的最大容量，队列头，队列尾，模拟队列的数组）
    private int maxSize;//表示数组的最大容量
    private int front;//队列头
    private int rear;//队列尾
    private int[] arr;//该数组用于存放数据，模拟队列

    //创建队列的构造器
    public ArrayQueue(int maxSize){
        this.maxSize = maxSize;
        front = -1;//指向队列头部，分析出front是指向队列头的前一个位置
        rear = -1;//指向队列尾，指向队列尾的数据（即就是队列最后一个数据）
        arr = new int[maxSize];
    }

    //判断队列是否为满
    public boolean isFull(){
        return rear == maxSize - 1;
    }

    //判断队列是否为空
    public boolean isEmpty(){
        return front == rear;
    }

    //添加数据到队列
    public void addQueue(int n){
        if(isFull()){
            System.out.println("队列已满，无法添加");
            return;
        }
        arr[++rear] = n;//rear后移，指向当前元素的索引位置
        System.out.println("添加成功");
    }

    //获取队列的数据，出队列
    public int getQueue(){
        if(isEmpty()){
            throw new RuntimeException("队列为空，不能取数据");
        }
        return arr[++front];//front后移，重新指向数组中下一个元素的上一个索引位置
    }

    //显示队列的所有数据
    public void showQueue(){
        if(isEmpty()){
            System.out.println("队列为空，没有数据");
            return;
        }
        for (int i = front + 1; i < rear + 1; i++) {
            System.out.printf("arr[%d] = %d\n" , i, arr[i]);
        }
    }

    //显示队列的头数据，注意不是取出数据
    public int showQueueHead(){
        if(isEmpty()){
            throw new RuntimeException("队列为空");
        }
        return arr[front + 1];
    }

}
