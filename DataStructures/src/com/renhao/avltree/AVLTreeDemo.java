package com.renhao.avltree;

/**
 * @author RenHao
 * @create 2022-10-27 12:29
 */
public class AVLTreeDemo {
    public static void main(String[] args) {

//        int[] arr = {4,3,6,5,7,8};
        int[] arr = { 10, 12, 8, 9, 7, 6 };
//        int[] arr = { 10, 11, 7, 6, 8, 9 };
        AVLTree avlTree = new AVLTree();
        for (int i = 0; i < arr.length; i++) {
            avlTree.add(new Node(arr[i]));
        }
        System.out.println("整棵树的高度：" + avlTree.getRoot().getHeight());//3
        System.out.println("左子树的高度：" + avlTree.getRoot().getLeftTreeHeight());//2
        System.out.println("右子树的高度：" + avlTree.getRoot().getRightTreeHeight());//2
        System.out.println("根节点：" + avlTree.getRoot());

    }
}

//创建一个平衡二叉排序树
//对于任何一个节点，其左右两颗子树的高度差不大于1
class AVLTree{
    private Node root;//根节点

    public Node getRoot() {
        return root;
    }

    //添加节点
    public void add(Node node) {
        if (root == null) {
            root = node;
        } else {
            root.addNode(node);
        }
    }

    //根据指定value值查找相应的节点
    public Node searchNode(int value) {
        if (root != null) {
            return root.searchNode(value);
        } else {
            return null;
        }
    }

    //根据指定value值查找相应节点的父节点
    public Node searchParentNode(int value){
        if (root != null) {
            return root.searchParentNode(value);
        } else {
            return null;
        }
    }

    //根据指定value值删除节点
    /*
    二叉排序树的删除节点包括三种情况：
    根据value值找到待删除节点 targetNode
    根据value值找到待删除节点的父节点 parentNode
    1)叶子节点：
    1.3判断该叶子节点是父节点的左子节点还是右子节点
        如果是左子节点：parentNode.leftNode = null
        如果是右子节点：parentNode.rightNode = null
    2)包含一颗子树的节点：
    2.3首先判断targetNode是parentNode的左子节点还是右子节点
        如果是左子节点，判断targetNode是具有左子树还是右子树
            如果是左子树：parentNode.leftNode = targetNode.leftNode
            如果是右子树：parentNode.leftNode = targetNode.rightNode
        如果是右子节点，判断targetNode是具有左子树还是右子树
            如果是左子树：parentNode.rightNode = targetNode.leftNode
            如果是右子树：parentNode.rightNode = targetNode.rightNode
    3)包含两颗子树的节点：
    3.3找出targetNode右子树中value最小的minNode,并返回minNode.value
    3.4令targetNode.value = minNode.value，删除minNode
     */
    public void delNode(int value){
        if(root == null){
            System.out.println("二叉树为空，无法删除");
            return;
        }
        //先找到待删除节点
        Node targetNode = searchNode(value);
        //目标节点为空
        if(targetNode == null){
            System.out.println("未找到待删除节点");
            return;
        }
        //目标节点不为空，但二叉树只有一个节点，则目标节点为根节点
        if(root.leftNode == null && root.rightNode == null){
            root = null;
            return;
        }
        //找到待删除节点的父节点
        Node parentNode = searchParentNode(value);
        //目标节点为叶子节点，此时目标节点不可能为根节点，即父节点不可能为空
        if(targetNode.leftNode == null && targetNode.rightNode == null){//目标节点为叶子节点
            //targetNode是parentNode的左子节点
            if(parentNode.leftNode != null && parentNode.leftNode.value == value){
                parentNode.leftNode = null;
            }else{
                parentNode.rightNode = null;
            }
        }else if(targetNode.leftNode != null && targetNode.rightNode != null){//目标节点包含两颗子树
            int minValue = delMinNode(targetNode.rightNode);//找出targetNode右子树中value最小的minNode,并返回minNode.value
            targetNode.value = minValue;
        }else{//目标节点包含一颗子树，此时需要注意父节点可能为空
            if(parentNode != null){
                if(parentNode.leftNode != null && parentNode.leftNode.value == targetNode.value){//targetNode是parentNode的左子节点
                    if(targetNode.leftNode != null){//targetNode具有左子树
                        parentNode.leftNode = targetNode.leftNode;
                    }else{//targetNode具有右子树
                        parentNode.leftNode = targetNode.rightNode;
                    }
                }else{//targetNode是parentNode的右子节点
                    if(targetNode.leftNode != null){//targetNode具有左子树
                        parentNode.rightNode = targetNode.leftNode;
                    }else{//targetNode具有右子树
                        parentNode.rightNode = targetNode.rightNode;
                    }
                }
            }else{//如果父节点为空，此时targetNode为root
                if(targetNode.leftNode != null){//targetNode具有左子树
                    root = targetNode.leftNode;
                }else{//targetNode具有右子树
                    root = targetNode.rightNode;
                }
            }
        }
    }

    //删除以node节点为根的树中value最小的Node，并返回最小value
    public int delMinNode(Node node){
        Node temp = node;
        //向左子树寻找
        while(temp.leftNode != null){
            temp = temp.leftNode;
        }
        //此时temp指向value最小的Node
        int minValue = temp.value;
        delNode(minValue);
        return minValue;
    }

    //中序遍历
    public void infixOrder() {
        if (root != null) {
            root.infixOrder();
        } else {
            System.out.println("二叉排序树为空，无法遍历");
        }
    }

}

//创建一个节点
class Node {
    int value;//节点存储数据
    Node leftNode;//左子节点
    Node rightNode;//右子节点

    public Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }
    
    //返回以当前节点为根节点的整棵树的高度
    public int getHeight(){
        return Math.max(this.leftNode == null ? 0 : this.leftNode.getHeight(), this.rightNode == null ? 0 : this.rightNode.getHeight()) + 1;
    }

    //返回以当前节点为根节点的左子树的高度
    public int getLeftTreeHeight(){
        if(this.leftNode == null){
            return 0;//左子树为空
        }else{
            return this.leftNode.getHeight();
        }
    }

    //返回以当前节点为根节点的右子树的高度
    public int getRightTreeHeight(){
        if(this.rightNode == null){
            return 0;//右子树为空
        }else{
            return this.rightNode.getHeight();
        }
    }

    //左旋转，降低右子树的高度
    public void leftRotate(){

        //以旋转节点的值，创建新的节点
        Node newNode = new Node(this.value);
        //把新的节点的左子树设置成旋转节点的左子树
        newNode.leftNode = this.leftNode;
        //把新的节点的右子树设置成旋转节点的右子节点的左子树
        newNode.rightNode = this.rightNode.leftNode;
        //把旋转节点的值替换成旋转节点右子节点的值
        this.value = this.rightNode.value;
        //把旋转节点的左子树设置成新节点
        this.leftNode = newNode;
        //把旋转节点的右子树设置成旋转节点右子节点的右子树
        this.rightNode = this.rightNode.rightNode;
    }

    //右旋转，降低左子树的高度
    public void rightRotate(){

        //以旋转节点的值，创建新的节点
        Node newNode = new Node(this.value);
        //把新的节点的右子树设置成旋转节点的右子树
        newNode.rightNode = this.rightNode;
        //把新的节点的左子树设置成旋转节点的左子节点的右子树
        newNode.leftNode = this.leftNode.rightNode;
        //把旋转节点的值替换成旋转节点左子节点的值
        this.value = this.leftNode.value;
        //把旋转节点的右子树设置成新节点
        this.rightNode = newNode;
        //把旋转节点的左子树设置成旋转节点左子节点的左子树
        this.leftNode = this.leftNode.leftNode;
    }

    //添加节点
    /*
    比较待添加节点和当前节点value的大小；
    如果小于，且左节点为空，则挂在左子节点，如果不为空，则向左递归；
    如果小于，且左节点为空，则挂在左子节点，如果不为空，则向左递归；
     */
    public void addNode(Node node) {
        //比较待添加节点和当前节点value的大小
        if (this.value > node.value) {//当前结点的值大于添加的结点的值
            if (this.leftNode == null) {
                this.leftNode = node;//如果左子节点为空，挂左边
            } else {
                this.leftNode.addNode(node);//否则向左子树递归
            }
        } else {//当前结点的值不大于添加的结点的值
            if (this.rightNode == null) {
                this.rightNode = node;//如果右子节点为空，挂右边
            } else {
                this.rightNode.addNode(node);//否则向右子树递归
            }
        }

        //为了构建平衡二叉树
        /*
        在添加完一个节点后，需要判断以当前节点为根节点的左右子树的高度差，即平衡因子:
            如果(右子树的高度-左子树的高度) > 1 , 需要左旋转, 降低右子树的高度；
                左旋转之前，需要判断当前节点的右子节点的左右子树的高度关系：
                    如果(左子树的高度 > 右子树的高度):
                        需要先以当前节点的右子节点为旋转节点，进行右旋转，再以当前节点为旋转节点，进行左旋转；
                    如果(左子树的高度 <= 右子树的高度):
                        直接以当前节点为旋转节点，进行左旋；
            如果(左子树的高度-右子树的高度) > 1 , 需要右旋转, 降低左子树的高度；
                右旋转之前，需要判断当前节点的左子节点的左右子树的高度关系：
                    如果(右子树的高度 > 左子树的高度):
                        需要先以当前节点的左子节点为旋转节点，进行左旋转，再以当前节点为旋转节点，进行右旋转；
                    如果(右子树的高度 <= 左子树的高度):
                        直接以当前节点为旋转节点，进行右旋；
         */
        if ((this.getRightTreeHeight() - this.getLeftTreeHeight()) > 1) {//左旋转
            //左旋转之前，需要判断当前节点的右子节点的左右子树的高度关系, 如果(左子树的高度 > 右子树的高度)
            if (this.rightNode.getLeftTreeHeight() > this.rightNode.getRightTreeHeight()) {
                //需要先以当前节点的右子节点为旋转节点，进行右旋转
                this.rightNode.rightRotate();
                //再以当前节点为旋转节点，进行左旋转
                this.leftRotate();
            } else {//如果(左子树的高度 <= 右子树的高度)
                //直接以当前节点为旋转节点，进行左旋
                this.leftRotate();
            }
            return;
        }

        if((this.getLeftTreeHeight() - this.getRightTreeHeight()) > 1){//右旋转
            //右旋转之前，需要判断当前节点的左子节点的左右子树的高度关系， 如果(右子树的高度 > 左子树的高度)
            if (this.leftNode.getRightTreeHeight() > this.leftNode.getLeftTreeHeight()) {
                //需要先以当前节点的左子节点为旋转节点，进行左旋转
                this.leftNode.leftRotate();
                //再以当前节点为旋转节点，进行右旋转
                this.rightRotate();
            } else {//如果(右子树的高度 <= 左子树的高度)
                //直接以当前节点为旋转节点，进行右旋
                this.rightRotate();
            }
        }
    }

    //根据指定value值查找相应的节点
    public Node searchNode(int value) {
        //当前节点即为待查找节点
        if (this.value == value) {
            return this;
        }
        if (value < this.value && this.leftNode != null) {
            return this.leftNode.searchNode(value);//向左子树递归
        } else if (value >= this.value && this.rightNode != null) {
            return this.rightNode.searchNode(value);//向右子树递归
        } else {
            return null;//不存在
        }
    }

    //根据指定value值查找相应节点的父节点
    public Node searchParentNode(int value) {
        if (this.leftNode != null && this.leftNode.value == value || this.rightNode != null && this.rightNode.value == value) {
            return this;//当前节点即为待查找节点
        }
        if (value < this.value && this.leftNode != null) {
            return this.leftNode.searchParentNode(value);//向左子树递归
        } else if (value >= this.value && this.rightNode != null) {
            return this.rightNode.searchParentNode(value);//向右子树递归
        } else {
            return null;//不存在
        }
    }


    //中序遍历
    public void infixOrder() {
        //左子树递归
        if (this.leftNode != null) {
            this.leftNode.infixOrder();
        }
        //输出当前节点
        System.out.println(this);
        //右子树递归
        if (this.rightNode != null) {
            this.rightNode.infixOrder();
        }
    }
}
