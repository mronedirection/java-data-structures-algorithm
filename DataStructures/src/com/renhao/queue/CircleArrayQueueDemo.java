package com.renhao.queue;

import java.util.Scanner;

/**
 * 使用数组模拟环形队列
 *
 * @author RenHao
 * @create 2022-09-11 19:10
 */
public class CircleArrayQueueDemo {

    public static void main(String[] args) {
        //测试
        CircleArrayQueue queue = new CircleArrayQueue(3);//设置3，其队列的有效数据最大是2
        //从键盘接收字符选择菜单
        Scanner scan = new Scanner(System.in);
        char key = ' ';//接收用户输入
        int data = 0;
        boolean loopFlag = true;
        //输出菜单
        while(loopFlag){
            System.out.println("s(show):显示队列");
            System.out.println("e(exit):退出程序");
            System.out.println("a(add):添加数据到队列");
            System.out.println("g(get):从队列取出数据");
            System.out.println("h(head):查看队列头的数据");
            System.out.print("请输入：");
            key = scan.next().charAt(0);//接收一个字符
            switch (key){
                case 's':
                    queue.showQueue();
                    System.out.println();
                    break;
                case 'e':
                    if(scan != null){
                        scan.close();
                    }
                    loopFlag = false;
                    System.out.println("退出成功");
                    break;
                case 'a':
                    System.out.print("请输入一个整数：");
                    data = scan.nextInt();
                    queue.addQueue(data);
                    break;
                case 'g'://取出数据
                    try {
                        System.out.println("取出数据：" + queue.getQueue());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h'://查看队列头的数据
                    try {
                        System.out.println("队列头的数据为：" + queue.showHeadQueue());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
            }
        }
    }
}

/*
思路调整：
1.front:指向队列的第一个元素，初始值为0
2.rear:指向队列的最后一个元素的后一个位置，初始值为0，因此队列中存放的最大元素个数为maxSize - 1;
3.队列满条件：(rear+1) % maxSize == front
4.队列空条件：rear == front
5.队列中有效数据个数：(rear + maxSize - front) % maxSize
 */
class CircleArrayQueue {
    //成员变量：数组最大容量，队列首和队列尾指针，用于模拟队列的数组
    private int maxSize;//数组最大容量
    //front 变量的含义做一个调整： front 就指向队列的第一个元素, 也就是说 arr[front] 就是队列的第一个元素
    //front 的初始值 = 0
    private int front;//队列首指针
    //rear 变量的含义做一个调整：rear 指向队列的最后一个元素的后一个位置. 因为希望空出一个空间做为约定.
    //rear 的初始值 = 0
    private int rear;//队列尾指针
    private int[] arr;//该数据用于存放数据, 模拟队列

    //构造器
    public CircleArrayQueue(){

    }
    public CircleArrayQueue(int maxSize){
        this.maxSize = maxSize;
        arr = new int[maxSize];
    }

    //方法
    //判断队列是否满
    public boolean isFull(){
        //rear:[0, maxSize - 1],rear指向队列最后一个元素的下一个位置
        return ((rear + 1) % maxSize) == front;
    }
    //判断队列是否空
    public boolean isEmpty(){
        return front == rear;
    }
    //添加数据到队列
    public void addQueue(int n){
        // 判断队列是否满
        if(isFull()){
            System.out.println("队列已满，无法添加");
        }else {
            //直接将数据加入
            arr[rear] = n;
            //将 rear 后移, 这里必须考虑取模，避免数组越界
            rear = (rear + 1) % maxSize;
            System.out.println("添加成功");
        }
    }
    //获取队列的数据，出队列
    public int getQueue(){
        // 判断队列是否空
        if(isEmpty()){
            throw new RuntimeException("队列为空");
        }
        // 这里需要分析出 front是指向队列的第一个元素
        // 1. 先把 front 对应的值保留到一个临时变量
        // 2. 将 front 后移, 考虑取模
        // 3. 将临时保存的变量返回
        int value = arr[front];
        front = (front + 1) % maxSize;
        return value;
    }
    //显示队列的所有数据
    public void showQueue(){
        if(isEmpty()){
            System.out.println("队列为空");
        }
        // 思路：从front开始遍历，遍历多少个元素
        for (int i = front; i < front + size(); i++) {
            //front:[0, maxSize - 1]
            System.out.printf("arr[%d] = %d", i % maxSize, arr[i % maxSize]);
        }
    }
    //获取当前队列的有效数据个数
    public int size(){
        return (rear - front + maxSize) % maxSize;
    }
    //显示队列的头数据，注意不是取数据
    public int showHeadQueue(){
        if(isEmpty()){
            throw new RuntimeException("队列为空");
        }
        return arr[front];
    }

}
