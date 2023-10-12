package com.renhao.linkedlist;

import java.util.Stack;

/**
 * @author RenHao
 * @create 2022-09-13 11:13
 */
public class SingleLinkedListDemo {

    public static void main(String[] args) {

        //测试
        //先创建节点
        HeroNode heroNode1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode heroNode2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode heroNode3 = new HeroNode(3, "吴用", "智多星");
        HeroNode heroNode4 = new HeroNode(4, "林冲", "豹子头");

        //创建链表
        SingleLinkedList singleLinkedList = new SingleLinkedList();

        //将英雄节点加入链表
//        singleLinkedList.add(heroNode1);
//        singleLinkedList.add(heroNode4);
//        singleLinkedList.add(heroNode3);
//        singleLinkedList.add(heroNode2);

        //将英雄节点按照编号的顺序加入链表
        singleLinkedList.addByOrder(heroNode1);
        singleLinkedList.addByOrder(heroNode4);
        singleLinkedList.addByOrder(heroNode3);
        singleLinkedList.addByOrder(heroNode2);
        //显示链表
        singleLinkedList.list();

//        //按照提供的heroNode的no，修改链表中对应no的heroNode的name和nickName
//        singleLinkedList.update(new HeroNode(4, "周杰伦", "时代的狂"));
//        //显示修改数据后的链表
//        System.out.println("修改后的链表情况：");
//        singleLinkedList.list();

//        //删除一个节点
//        singleLinkedList.del(1);
//        //删除4号节点后的链表
//        System.out.println("删除4号节点后的链表:");
//        singleLinkedList.list();

//        //获取单链表的有效节点个数
//        System.out.println();
//        System.out.println("有效的节点个数=" + getLength(singleLinkedList.getHead()));//4

//        //查找单链表中的倒数第k个节点【新浪面试题】
//        System.out.println();
//        System.out.println("倒数第4个节点：" + findLastIndexNode(singleLinkedList.getHead(), 4));

//        //单链表的反转【腾讯面试题，有点难度】
//        System.out.println();
//        reverseSingleLinkedList(singleLinkedList.getHead());
//        System.out.println("反转后的链表：");
//        singleLinkedList.list();

        //从尾到头打印单链表 【百度，要求方式1：反向遍历。 方式2：Stack栈】
        System.out.println("从尾到头打印单链表，不改变链表的结构：");
        reversePrint(singleLinkedList.getHead());

    }

    //从尾到头打印单链表 【百度，要求方式1：反向遍历。 方式2：Stack栈】
    //方式2：可以利用栈这个数据结构，将各个节点压入到栈中，然后利用栈的先进后出
    //的特点，就实现了逆序打印的效果
    public static void reversePrint(HeroNode head){
        //先判断链表是否为空
        if(head.next == null){
            System.out.println("链表为空");
        } else if (head.next.next == null) {
            System.out.println(head.next);//如果只有一个节点，则打印这个节点
        }
        //链表有效节点个数大于1
        //遍历链表，将链表压入栈
        HeroNode temp = head.next;
        Stack<HeroNode> stack = new Stack<>();//新建一个栈，存放HeroNode
        //入栈：将链表的所有节点压入栈
        while(temp != null){
            stack.push(temp);
            temp = temp.next;//temp后移，实现遍历链表
        }
        //出栈：将栈中的节点进行打印，pop出栈
        while(stack.size() > 0){
            System.out.println(stack.pop());//stack的特点是先进后出；实现逆序打印
        }

    }


    //单链表的反转【腾讯面试题，有点难度】
    public static void reverseSingleLinkedList(HeroNode head){

        //判断单链表是否为空
        if(head.next == null || head.next.next == null){
            return;//如果链表为空或者链表只含有一个有效节点，则返回原链表
        }

        HeroNode newHead = new HeroNode(0, "", "");//创建一个新的头节点，用于暂时存储反转后的链表
        HeroNode temp = head.next;//定义临时节点变量，辅助遍历
        HeroNode next = null;//定义空节点，存储temp节点的下一个节点，辅助遍历

        while(temp != null){
            //存储temp节点的下一个节点，否则无法完成对旧链表的遍历，因为后面对temp.next有更新
            next = temp.next;
            //将temp节点指向新链表newHead的第一个有效节点
            temp.next = newHead.next;
            //将新链表newHead的头节点指向temp节点
            newHead.next = temp;
            //temp后移，遍历旧链表
            temp = next;
        }

        //遍历完成后，将旧链表的头节点指向反转后新链表的第一个有效节点
        head.next = newHead.next;

    }
    //方法：查找单链表中的倒数第k个节点【新浪面试题】
    //思路：
    //1.编写一个方法，接收head节点，同时接收一个index
    //2.index 表示是倒数第index个节点
    //3.先把链表从头到尾遍历，得到链表的总长度getLength
    //4.得到size后，我们从链表的第一个开始遍历(size - index)个，就可以得到
    //5.如果找到了，则返回该节点，否则返回null
    public static HeroNode findLastIndexNode(HeroNode head, int index){
        //判断链表是否为空
        if(head.next == null){
            return null;//没有找到
        }
        //获取链表长度(有效节点个数)
        int length = getLength(head);
        //先对index进行校验
        if(index <= 0 || index > length){
            return null;//输入的index不合理
        }
        //定义临时的辅助节点变量temp,用于遍历
        HeroNode temp = head.next;//length = 3, index = 1 //3 - 1 = 2, temp移动2次，从head.next移动到倒数第一个元素
        for (int i = 0; i < length - index; i++) {
            temp = temp.next;//temp从head.next后移(length-index)次
        }
        return temp;

//        int i = 0;
//        boolean flag = false;//判断是否正常终止，正常终止，则找到该元素
//        while(temp != null){
//            i++;
//            if(i < (length - index)){//temp从head.next后移(length-index)次
//                temp = temp.next;
//            }else{
//                flag = true;//代表正常终止，即找到该索引位置
//                break;
//            }
//        }
//        if(flag){
//            return temp;//返回倒数第index个元素
//        }else{
//            return null;//代表index越界
//        }

    }

    //方法：获取到单链表的节点的个数（如果是带头结点的链表，需求不统计头节点）

    /**
     *
     * @param head 链表的头节点
     * @return 返回有效的节点个数（存有数据的节点的个数）
     */
    public static int getLength(HeroNode head){
        if(head.next == null){//链表为空
            return 0;
        }

        int length = 0;//记录有效节点的个数
        HeroNode temp = head.next;//创建临时节点变量，用于遍历链表，没有统计头节点
        while(temp != null){
            length++;
            temp = temp.next;//temp后移，遍历链表
        }
        return length;
    }

}

//定义SingleLinkedList 管理英雄
class SingleLinkedList{
    //先初始化一个头节点，头节点不能做任何修改，并且不存放具体的数据
    private HeroNode head = new HeroNode(0, "", "");
    //返回头节点
    public HeroNode getHead(){
        return head;
    }
    //添加节点到单向链表
    //思路，当不考虑编号顺序时
    //1.找到当前链表的最后节点
    //2.将最后这个节点的next 指向新的节点
    public void add(HeroNode heroNode){

        //因为head节点不能动，因此我们需要一个辅助遍历的节点 temp
        HeroNode temp = head;
        //遍历链表，找到最后一个元素
        while(temp.next != null){
            //找到链表的最后一个节点，如果没有找到，就将temp后移
            temp = temp.next;
        }
        //当退出while循环时，temp就指向了链表的最后
        //将最后这个节点的next指向新的节点
        //head -> HeroNode1 -> HeroNode2 -> HeroNode3...
        temp.next = heroNode;
    }

    //按照编号的顺序将数据插入链表，在内存中就将数据的顺序排好，提高程序运行速度
    //第二种方式：在添加英雄时，根据英雄no将英雄插入到指定位置
    //（如果有这个排名，则添加失败，并给出提示）
    public void addByOrder(HeroNode heroNode){
        //因为头节点不能动，因此我们仍然通过一个辅助变量来帮助找到添加的位置
        //因为单链表，所以需要保证我们找的temp位于添加位置的前一个节点，即(temp.next.no > heroNode.no),将需要添加的元素插在temp后面
        boolean flag = true;//flag标志表明添加的编号是否存在，判断是否能插入
        HeroNode temp = head;
        while(true){
            //说明temp已经在链表的最后，因此可以在temp后面插入
            if(temp.next == null){
                break;
            }
            //从前向后找，第一个temp.next.no值大于heroNode.no的位置，就表明应该将元素插入到temp后面，temp.next前面
            if(temp.next.no > heroNode.no){
                break;
            } else if (temp.next.no == heroNode.no) {
                flag = false;//欲插入的元素已存在
                break;
            }
            //表明没找到，因此将temp后移，继续找
            temp = temp.next;
        }
        if(flag){
            //将heroNode指向temp的下一个元素
            heroNode.next = temp.next;
            //将temp代表的heroNode指向新插入的heroNode
            temp.next = heroNode;
        }else{
            System.out.printf("准备插入的编号为%d的英雄已经存在了，不能加入\n", heroNode.no);
        }
    }

    //修改节点的信息，根据no编号修改对应heroNode的信息，no编号不能被修改
    //说明
    //1.根据newHeroNode的no来修改即可
    public void update(HeroNode newHeroNode){
        //判断是否为空
        if(head.next == null){
            System.out.println("链表为空");
        }
        //根据no编号，找到需要修改的节点
        boolean flag = false;//判断遍历完链表后，是否找到no编号的heroNode
        HeroNode temp = head.next;
        while(temp != null){
            //找到要修改的heroNode
            if(temp.no == newHeroNode.no){
                flag = true;//找到no对应的heroNode，修改标志位
                break;
            }
//            //遍历至链表末尾
//            if(temp == null){
//                break;
//            }
            temp = temp.next;
        }

        //根据flag判断是否找到要修改的节点
        if(flag){//找到了
            temp.name = newHeroNode.name;
            temp.nickName = newHeroNode.nickName;
        }else{//未找到
            System.out.printf("没有找到编号%d的节点，不能修改\n", newHeroNode.no);
        }
    }

    //删除节点
    //思路
    //1.head 不能动，因此我们需要一个temp辅助节点找到待删除节点的前一个节点
    //2.说明我们在比较时，是temp.next.no和需要删除的节点的no比较
    public void del(int no){
        //判断链表是否为空
        if(head.next == null){
            System.out.println("链表为空");
            return;
        }
        //如不为空，遍历链表
        HeroNode temp = head;
        boolean flag = false;//判断遍历完链表后，是否找到待删除的节点
        while(temp.next != null){
            //找到待删除的节点
            if(temp.next.no == no){
                flag = true;
                break;
            }
            //temp后移：将temp指向下一个节点
            temp = temp.next;
        }
        if(flag){
            //如果找到，将待删除节点的前一个节点，指向待删除节点的后一个节点
            //jvm中，如果该对象未被引用地址指向，则将会被GC机制自动回收
            temp.next = temp.next.next;
        }else{
            System.out.printf("要删除的%d号节点不存在\n", no);
        }

    }
    //显示链表[遍历]
    public void list(){
        //判断链表是否为空
        if(head.next == null){
            System.out.println("链表为空");
            return;
        }
        //遍历链表
        //定义一个辅助节点变量来遍历
        HeroNode temp = head.next;
        while(temp != null){
            //输出节点信息
            //head1(head.next) -> head2 -> head3...
            System.out.println(temp);
            //将temp后移
            temp = temp.next;
        }
    }

}

//定义HeroNode，每个HeroNode对象就是一个节点
class HeroNode {
    //成员变量
    public int no;
    public String name;
    public String nickName;

    public HeroNode next;//指向下一个节点

    //构造器
    public HeroNode(int no, String name, String nickName){
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    //为了显示方法，我们重写toString()

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }

}