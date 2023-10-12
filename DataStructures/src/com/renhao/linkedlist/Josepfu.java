package com.renhao.linkedlist;

/**
 * @author RenHao
 * @create 2022-09-14 17:29
 */
public class Josepfu {
    public static void main(String[] args) {
        //测试单向环形链表
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        circleSingleLinkedList.addBoy(5);//加入5个节点

        //遍历
        System.out.println("小孩初始编号：");
        circleSingleLinkedList.showBoy();

        //出圈
        System.out.println("出圈顺序：");
        //从第一个小孩开始，每次数两个数，小孩总数为5
        circleSingleLinkedList.countBoy(1, 2, 5);
    }

}

//创建一个环形的单向链表
class CircleSingleLinkedList{
    //创建一个first节点，当前没有编号
    private Boy first = null;

    //添加小孩节点，构建成一个环形的链表
    public void addBoy(int nums){
        if(nums < 1){//对输入的nums进行校验
            System.out.println("输入人数太少");
            return;
        }
        Boy curBoy = null;//辅助节点变量，用于构建环形链表
        for (int i = 1; i <= nums; i++) {
            Boy boy = new Boy(i);
            if(i == 1){
                first = boy;//将first指向boy,并保持不动
                first.setNext(first);//构成环
                curBoy = first;//让curBoy指向第一个小孩
            }else{
                curBoy.setNext(boy);//将curBoy指向新加入的boy
                boy.setNext(first);//将新加入的boy指向first，形成环形链表
                curBoy = boy;//curBoy后移
            }
        }
    }

    //遍历当前的环形链表
    public void showBoy(){
        //判断链表是否为空
        if(first == null){
            System.out.println("链表为空");
            return;
        }
        //如果链表不为空
        //遍历链表
        Boy curBoy = first;//first指向不能动，因此创建节点变量，辅助遍历
        do{
            System.out.printf("小孩的编号 %d \n", curBoy.getNo());//输出当前curBoy节点
            curBoy = curBoy.getNext();//curBoy后移
        }while(curBoy != first);
        //方法二：
//        while(true){
//            System.out.printf("小孩的编号 %d \n", curBoy.getNo());
//            if(curBoy.getNext() == first){//curBoy 已经输出，且curBoy的下一个是first，所以结束循环
//                break;
//            }
//            curBoy = curBoy.getNext();//否则，curBoy后移，实现遍历
//        }
    }

    /**
     *
     * @param startNo 表示从第几个小孩开始
     * @param countNum 表示数几下
     * @param nums 表示最初有多小小孩在圈中
     */
    //根据用户的输入，输出小孩出圈编号
    public void countBoy(int startNo, int countNum, int nums){
        //对输入的数据进行校验
        if(startNo < 1 || countNum < 1 || nums < 1){
            System.out.println("输入数据不正确");
            return;
        }
        //定义两个指针，分别指向startNo, 和startNo - 1, 辅助出圈和遍历
        //先定义一个helper指针，让其指向最后一个节点
        Boy helper = first;
        while(true){
            if(helper.getNext() == first){
                break;//如果遍历到最后一个节点，出循环
            }
            helper = helper.getNext();//helper指针后移
        }
        //让first和helper指针移动startNo - 1次，分别指向startNo, 和startNo - 1
        for (int i = 0; i < startNo - 1; i++) {
            first = first.getNext();
            helper = helper.getNext();
        }
        //出圈
        while(true){
            if(helper == first){
                break;//此时圈中只剩下一个节点
            }
            //开始数数，出圈，让first和helper指针移动countNum - 1次，分别指向待出圈的编号和后一个编号
            for (int i = 0; i < countNum - 1; i++) {
                first = first.getNext();
                helper = helper.getNext();
            }
            System.out.printf("编号为 %d 的小孩出圈\n", first.getNo());
            first = first.getNext();//first后移
            helper.setNext(first);//让helper指向first，此时没有指针指向待出圈编号，将会被回收，实现出圈
        }
        System.out.printf("编号为 %d 的小孩出圈\n", first.getNo());
        System.out.println("GameOver");
    }
}

//创建一个Boy类，表示一个节点
class Boy{
    private int no;// 编号
    private Boy next;//指向下一个节点，默认为null

    public Boy(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }

}


