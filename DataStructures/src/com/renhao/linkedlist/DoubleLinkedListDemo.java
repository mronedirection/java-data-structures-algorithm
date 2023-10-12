package com.renhao.linkedlist;

/**
 * @author RenHao
 * @create 2022-09-14 10:36
 */
public class DoubleLinkedListDemo {

    public static void main(String[] args) {
        //测试
        //创建节点
        HeroNode2 hero1 = new HeroNode2(1, "宋江", "及时雨");
        HeroNode2 hero2 = new HeroNode2(2, "卢俊义", "玉麒麟");
        HeroNode2 hero3 = new HeroNode2(3, "吴用", "智多星");
        HeroNode2 hero4 = new HeroNode2(4, "林冲", "豹子头");
        //创建一个双向链表，并添加元素
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
//        doubleLinkedList.add(hero1);
//        doubleLinkedList.add(hero2);
//        doubleLinkedList.add(hero3);
//        doubleLinkedList.add(hero4);

        //按编号顺序插入
        doubleLinkedList.addByOrder(hero1);
        doubleLinkedList.addByOrder(hero3);
        doubleLinkedList.addByOrder(hero4);
        doubleLinkedList.addByOrder(hero2);
        //显示链表
        System.out.println("原链表：");
        doubleLinkedList.list();

        //修改
        doubleLinkedList.update(new HeroNode2(4, "小林", "小豹"));
        System.out.println("修改后的链表：");
        doubleLinkedList.list();

        //删除
        doubleLinkedList.del(4);
        System.out.println("删除后的链表：");
        doubleLinkedList.list();

    }

}

//创建一个双向链表的类
class DoubleLinkedList{

    //先初始化一个头节点，头节点不要动，不存放具体数据
    private HeroNode2 head = new HeroNode2(0, " ", " ");

    //返回头节点
    public HeroNode2 getHead(){
        return head;
    }

    //遍历双向链表的方法
    //显示链表[遍历]
    public void list(){
        //判断链表是否为空
        if(head.next == null){
            System.out.println("链表为空");
            return;
        }
        //如果链表不为空，遍历链表并显示
        HeroNode2 temp = head.next;//定义一个临时节点变量用于辅助遍历
        while(temp != null){
            System.out.println(temp);
            temp = temp.next;//temp后移
        }
    }

    //添加一个节点到双向链表的最后
    public void add(HeroNode2 heroNode){
        //遍历链表，找到链表的最后位置
        HeroNode2 temp = head;
        while(temp.next != null){
            temp = temp.next;
        }
        //将temp节点指向新节点，新节点的pre指向temp节点
        temp.next = heroNode;
        heroNode.pre = temp;
    }

    //第二种方式：在添加英雄时，根据排名将英雄插入指定的位置
    //如果有这个排名，则添加失败，并给出提示
    public void addByOrder(HeroNode2 heroNode){
        //遍历链表，找到要插入位置的前一个节点
        HeroNode2 temp = head;//辅助遍历的临时节点
        HeroNode2 lastTemp = null;//保存temp节点的下一个节点
        boolean flag = true;//判断是否能插入,true:表示能插入
        while(temp.next != null){
            if(temp.next.no == heroNode.no){
                flag = false;//该元素已存在，不能插入
                break;
            } else if (temp.next.no > heroNode.no) {
                break;//找到正确插入位置
            }
            temp = temp.next;//temp后移，遍历链表
        }
        if(flag){
            lastTemp = temp.next;//保存原来temp节点指向的后一个节点
            temp.next = heroNode;//将temp指向新的节点
            heroNode.pre = temp;//将新节点的pre指向temp节点
            if(lastTemp != null){//如果temp节点不是最后一个节点
                heroNode.next = lastTemp;//那么将新节点的next指向原来temp的后一个节点
                lastTemp.pre = heroNode;//将原来temp节点的后一个节点的pre指向新节点
            }
        }else {
            System.out.println("该元素已存在，不能插入");
        }
    }

    //根据提供的节点的no值，修改链表中相同no值节点的内容，可以看到双向链表的节点内容修改和单向链表一样
    //只是节点类型改成 HeroNode2
    public void update(HeroNode2 newHeroNode2){
        //判断链表是否为空，为空输出提示信息，并返回
        if(head.next == null){
            System.out.println("链表为空，无法修改");
            return;
        }
        //链表不为空，遍历链表，找到要修改的节点
        //1.遍历完链表没找到
        //2.找到了
        boolean flag = false;//判断是否找到要修改的节点，false:没找到
        HeroNode2 temp = head;//辅助遍历的节点
        while(head.next != null){
            if(temp.next.no == newHeroNode2.no){
                flag = true;//代表找到要修改的节点
                break;
            }
            temp = temp.next;//temp后移，遍历
        }
        //遍历完成，判断是否找到待修改节点
        if(flag){//找到了，修改该节点属性
            temp.next.name = newHeroNode2.name;
            temp.next.nickName = newHeroNode2.nickName;
        }else {//没有找到
            System.out.printf("没有找到编号%d的节点，无法修改\n", newHeroNode2.no);
        }
    }

    //从双向链表中删除一个节点
    //说明
    //1 对于双向链表，我们可以直接找到要删除的这个节点
    //2 找到后，自我删除即可
    public void del(int no){
        //1.判断链表是否为空，为空则输出提示信息，并返回
        if(head.next == null){
            System.out.println("链表为空，无法删除");
            return;
        }
        //2.如不为空，则遍历链表找到该节点
        HeroNode2 temp = head.next;//辅助遍历
        boolean flag = false;//判断是否找到待删除节点，true：表示找到
        while(temp != null){//已经到链表的最后
            if(temp.no == no){
                flag = true;//找到待删除节点，将flag置为true
                break;
            }
            temp = temp.next;//temp后移，遍历原链表
        }
        // 判断是否找到
        if(flag){//找到了待删除的节点
            //3.删除该节点
            temp.pre.next = temp.next;//将temp节点的前一个节点的next指向temp节点的后一个节点
            if(temp.next != null){
                //如果是最后一个节点，则temp.next = null，就不需要执行下面这句话，否则出现空指针
                temp.next.pre = temp.pre;//将temp节点的后一个节点的pre指向temp节点的前一个节点
            }
        }else{
            System.out.printf("未找到编号%d的节点，无法删除", no);
        }
    }


}
//定义HeroNode2，每个HeroNode对象就是一个节点
class HeroNode2{

    //属性
    public int no;
    public String name;
    public String nickName;
    public HeroNode2 next;//指向下一个节点，默认为null
    public HeroNode2 pre;//指向前一个节点，默认为null

    //构造器
    public HeroNode2(int no, String name, String nickName){
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    //重写toString()


    @Override
    public String toString() {
        return "HeroNode2{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }

}