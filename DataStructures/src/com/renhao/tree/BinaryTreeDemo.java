package com.renhao.tree;

/**
 * @author RenHao
 * @create 2022-10-14 17:40
 */

//测试二叉树
public class BinaryTreeDemo {
    public static void main(String[] args) {

        //创建一个二叉树
        BinaryTree binaryTree = new BinaryTree();
        //创建节点
        HeroNode root = new HeroNode(1, "宋江");
        HeroNode node2 = new HeroNode(2, "吴用");
        HeroNode node3 = new HeroNode(3, "卢俊义");
        HeroNode node4 = new HeroNode(4, "林冲");
        HeroNode node5 = new HeroNode(5, "关胜");
        //对二叉树进行初始化
        binaryTree.setRoot(root);
        root.setLeft(node2);
        root.setRight(node3);
        node3.setLeft(node5);
        node3.setRight(node4);
        //测试前序遍历
//        binaryTree.preOrder();//1,2,3,5,4
        //测试中序遍历
//        binaryTree.infixOrder();//2,1,5,3,4
        //测试后序遍历
//        binaryTree.postOrder();//2,5,4,3,1
        //测试用前序遍历的方式对指定no的节点进行查找
//        binaryTree.preOrderSearch(5);//比较4次
        // 测试用中序遍历的方式对指定no的节点进行查找
//        binaryTree.infixOrderSearch(5);//比较3次
        // 测试用后序遍历的方式对指定no的节点进行查找
//        binaryTree.postOrderSearch(5);//比较2次
        //测试删除节点的方法
        System.out.println("删除前进行前序遍历:");
        binaryTree.preOrder();
        binaryTree.delNode(5);
        System.out.println("删除后进行前序遍历:");
        binaryTree.preOrder();

    }
}

//定义BinaryTree 二叉树
class BinaryTree{
    private HeroNode root;//声明二叉树的根节点

    public HeroNode getRoot() {
        return root;
    }

    public void setRoot(HeroNode root) {
        this.root = root;
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