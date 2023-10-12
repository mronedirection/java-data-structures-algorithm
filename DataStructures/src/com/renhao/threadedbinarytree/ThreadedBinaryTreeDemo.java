package com.renhao.threadedbinarytree;

/**
 * @author RenHao
 * @create 2022-10-19 9:55
 */
public class ThreadedBinaryTreeDemo {
    public static void main(String[] args) {

        //测试中序线索二叉树threadedNodes()
        HeroNode root = new HeroNode(1, "tom");
        HeroNode node2 = new HeroNode(3, "jack");
        HeroNode node3 = new HeroNode(6, "smith");
        HeroNode node4 = new HeroNode(8, "mary");
        HeroNode node5 = new HeroNode(10, "king");
        HeroNode node6 = new HeroNode(14, "dim");

        //二叉树，后面我们要递归创建, 现在简单处理使用手动创建
        root.setLeft(node2);
        root.setRight(node3);
        node2.setLeft(node4);
        node2.setRight(node5);
        node3.setLeft(node6);

        //测试线索化二叉树（中序线索化）
        ThreadedBinaryTree threadedBinaryTree = new ThreadedBinaryTree();
        threadedBinaryTree.setRoot(root);
        threadedBinaryTree.threadedNodes();
        System.out.println("10号节点的前驱节点为：" + node5.getLeft());//3节点
        System.out.println("10号节点的后继节点为：" + node5.getRight());//1节点

        //当二叉树被线索化后，不能再使用原来的方法遍历
        //测试线索化二叉树的遍历
        System.out.println("使用线索化的方式遍历 线索化二叉树");
        threadedBinaryTree.threadedBinaryList();//8, 3, 10, 1, 14, 6

    }
}

class ThreadedBinaryTree{
    private HeroNode root;//声明二叉树的根节点
    //线索化时需要
    private HeroNode pre;//线索化时，指向当前节点的前一个节点

    public HeroNode getRoot() {
        return root;
    }

    public void setRoot(HeroNode root) {
        this.root = root;
    }

    //重载threadedNodes
    public void threadedNodes(){
        this.threadedNodes(root);
    }

    //对二叉树进行线索化（采用中序遍历的方式）
    /**
     *
     * @param node 开始线索化的节点，一般从根节点开始
     */
    public void threadedNodes(HeroNode node){
        //判断当前节点是否为空，为空则返回
        if(node == null){
            return;
        }

        //以中序遍历的方式对二叉树进行线索化，左-根-右
        //线索化左子树
        threadedNodes(node.getLeft());//向左递归，寻找第一个具有空指针的节点
        //线索化当前节点
        //先处理前驱节点
        //以8节点为例
        //如果8节点的左字节点为空，则让8节点的左子节点指向pre;同时，将8节点的leftType置为1
        if(node.getLeft() == null){
            node.setLeft(pre);//让8节点的左子节点指向空节点
            node.setLeftType(1);//代表8节点具有前驱节点
        }

        //再处理后继节点
        //以8节点为例
        //此时pre指向8节点，node即当前节点为3节点，所以需要让pre节点的右子节点指向3节点，将pre节点的rightType置为1
        if(pre != null && pre.getRight() == null){
            pre.setRight(node);//让8节点的右子节点指向下一个节点
            pre.setRightType(1);//代表8节点具有后继节点
        }

        //每处理完一个节点，将pre后移，即指向当前节点node
        pre = node;

        //线索化右子树
        threadedNodes(node.getRight());
    }

    //二叉树被线索化后，不可再用原来的前中后序遍历方法进行遍历，否则会出现死循环
    //线索化二叉树的遍历方法(中序遍历：需要与线索化时的顺序保持一致)
    public void threadedBinaryList(){
        HeroNode tempNode = root;//指向遍历的当前节点，从根节点开始
        while(tempNode != null){//tempNode不为空，一直遍历
            //先向左子树查找，找到第一个被线索化的节点
            while(tempNode.getLeftType() == 0){
                tempNode = tempNode.getLeft();
            }
            //找到第一个被线索化的节点，直接输出
            System.out.println(tempNode);
            //再向右子树查找，只要下一个节点被线索化过，就一直输出
            while(tempNode.getRightType() == 1){
                tempNode = tempNode.getRight();
                System.out.println(tempNode);
            }
            tempNode = tempNode.getRight();//将tempNode后移
        }
    }

    //调用节点类中的前序遍历方法
    public void preOrder(){
        if(this.root != null){
            this.root.preOrder();
        }else{
            System.out.println("二叉树为空");
        }
    }
    //调用节点类中的中序遍历方法
    public void infixOrder(){
        if(this.root != null){
            this.root.infixOrder();
        }else{
            System.out.println("二叉树为空");
        }
    }
    //调用节点类中的后序遍历方法
    public void postOrder(){
        if(this.root != null){
            this.root.postOrder();
        }else{
            System.out.println("二叉树为空");
        }
    }

    //调用节点类中前序遍历的方法进行查找
    /**
     *
     * @param no 指定待查找编号
     */
    public void preOrderSearch(int no){
        if(this.root != null){
            HeroNode resNode = this.root.preOrderSearch(no);
            if(resNode != null){
                System.out.println(resNode);
            }else{
                System.out.println("未找到no为" + no + "的节点");
            }
        }else{
            System.out.println("二叉树为空");
        }
    }

    //调用节点类中中序遍历的方法进行查找
    /**
     *
     * @param no 指定待查找编号
     */
    public void infixOrderSearch(int no){
        if(this.root != null){
            HeroNode resNode = this.root.infixOrderSearch(no);
            if(resNode != null){
                System.out.println(resNode);
            }else{
                System.out.println("未找到no为" + no + "的节点");
            }
        }else{
            System.out.println("二叉树为空");
        }
    }

    //调用节点类中中序遍历的方法进行查找
    /**
     *
     * @param no 指定待查找编号
     */
    public void postOrderSearch(int no){
        if(this.root != null){
            HeroNode resNode = this.root.postOrderSearch(no);
            if(resNode != null){
                System.out.println(resNode);
            }else{
                System.out.println("未找到no为" + no + "的节点");
            }
        }else{
            System.out.println("二叉树为空");
        }
    }

    //调用节点类中的删除节点的方法对指定no的节点进行删除
    public void delNode(int no){
        if(this.root != null){
            if(this.root.getNo() == no){
                this.root = null;
            }else{
                this.root.delNode(no);
            }
        }else{
            System.out.println("二叉树为空，无法删除");
        }
    }
    
}

//定义HeroNode 节点
class HeroNode{

    //属性
    private int no;
    private String name;

    private HeroNode left;//左子节点
    private HeroNode right;//右子节点

    //说明
    //1. 如果leftType == 0 表示指向的是左子树, 如果 1 则表示指向前驱结点
    //2. 如果rightType == 0 表示指向是右子树, 如果 1表示指向后继结点
    private int leftType;//节点左指针类型
    private int rightType;//节点右指针类型

    public HeroNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeroNode getLeft() {
        return left;
    }

    public void setLeft(HeroNode left) {
        this.left = left;
    }

    public HeroNode getRight() {
        return right;
    }

    public void setRight(HeroNode right) {
        this.right = right;
    }

    public int getLeftType() {
        return leftType;
    }

    public void setLeftType(int leftType) {
        this.leftType = leftType;
    }

    public int getRightType() {
        return rightType;
    }

    public void setRightType(int rightType) {
        this.rightType = rightType;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }

    //前序遍历
    public void preOrder(){
        //输出根节点
        System.out.println(this);
        //递归左子树
        if(this.left != null){
            this.left.preOrder();
        }
        //递归右子树
        if(this.right != null){
            this.right.preOrder();
        }
    }

    //中序遍历
    public void infixOrder(){
        //递归左子树
        if(this.left != null){
            this.left.infixOrder();
        }
        //输出根节点
        System.out.println(this);
        //递归右子树
        if(this.right != null){
            this.right.infixOrder();
        }
    }

    //后序遍历
    public void postOrder(){
        //递归左子树
        if(this.left != null){
            this.left.postOrder();
        }
        //递归右子树
        if(this.right != null){
            this.right.postOrder();
        }
        //输出根节点
        System.out.println(this);
    }

    //使用前序遍历的顺序查找指定no的节点
    /**
     *
     * @param no 待查找节点的no
     * @return 如果找到，则返回该节点；否则，返回null；
     */
    public HeroNode preOrderSearch(int no){
        //先比较当前节点no是否为待查找no
        System.out.println("前序遍历");//记录前序查找比较次数
        if(this.no == no){
            return this;
        }
        HeroNode tempNode = null;
        //递归左子树
        if(this.left != null){
            tempNode = this.left.preOrderSearch(no);
        }
        //左子树中有返回值，或者this.left == null，执行下面语句
        if(tempNode != null){
            return tempNode;
        }
        //递归右子树
        if(this.right != null){
            tempNode = this.right.preOrderSearch(no);
        }
        return tempNode;
    }

    //使用中序遍历的顺序查找指定no的节点
    public HeroNode infixOrderSearch(int no){
        HeroNode tempNode = null;
        //递归左子树
        if(this.left != null){
            tempNode = this.left.infixOrderSearch(no);
        }
        if(tempNode != null){
            return tempNode;
        }
        //比较根节点
        System.out.println("中序遍历");//记录中序查找比较次数
        if(this.no == no){
            return this;
        }
        //递归右子树
        if(this.right != null){
            tempNode = this.right.infixOrderSearch(no);
        }
        return tempNode;
    }

    //使用后序遍历的顺序查找指定no的节点
    public HeroNode postOrderSearch(int no){
        HeroNode tempNode = null;
        //递归左子树
        if(this.left != null){
            tempNode = this.left.postOrderSearch(no);
        }
        if(tempNode != null){
            return tempNode;
        }
        //递归右子树
        if(this.right != null){
            tempNode = this.right.postOrderSearch(no);
        }
        if(tempNode != null){
            return tempNode;
        }
        //比较根节点
        System.out.println("进入后序查找");//记录后序查找比较次数
        if(this.no == no){
            return this;
        }
        return tempNode;
    }

    //删除指定no的节点

    /**
     * 1) 如果删除的节点是叶子节点，则删除该节点
     * 2) 如果删除的节点是非叶子节点，则删除该子树
     * @param no 删除编号为no的节点
     */
    public void delNode(int no) {
        //先判断当前节点的左子节点是否为待删除节点
        if(this.left != null && this.left.getNo() == no){
            this.left = null;
            return;
        }
        //再判断当前节点的右子节点是否为待删除节点
        if(this.right != null && this.right.getNo() == no){
            this.right = null;
            return;
        }
        //如果都不是，判断当前节点的左子节点是否为空，不为空则向左递归
        if(this.left != null){
            this.left.delNode(no);
        }
        //如果左子节点为空，判断当前节点的右子节点是否为空，不为空则向右递归
        if(this.right != null){
            this.right.delNode(no);
        }
    }

}