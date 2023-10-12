package com.renhao.binarysorttree;


/**
 * @author RenHao
 * @create 2022-10-25 21:35
 */
public class BinarySortTreeDemo {
    public static void main(String[] args) {
        int[] arr = {7, 3, 10, 12, 5, 1, 9, 2};
        BinarySortTree binarySortTree = new BinarySortTree();
        for (int i = 0; i < arr.length; i++) {
            binarySortTree.add(new Node(arr[i]));
        }
        //中序遍历
        binarySortTree.infixOrder();
        //测试searchNode和searchParentNode
//        System.out.println(binarySortTree.searchNode(2));
//        System.out.println(binarySortTree.searchParentNode(2));
        //测试delNode
        binarySortTree.delNode(2);
        binarySortTree.delNode(5);
        binarySortTree.delNode(9);
        binarySortTree.delNode(12);
        binarySortTree.delNode(7);
        binarySortTree.delNode(3);
        binarySortTree.delNode(1);
        binarySortTree.delNode(10);
        //中序遍历
        System.out.println("删除后：");
        System.out.println("root:" + binarySortTree.getRoot());
        binarySortTree.infixOrder();

    }
}

//创建二叉排序树
class BinarySortTree {
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

//创建节点
class Node{
    int value;//节点存储数据
    Node leftNode;//左子节点
    Node rightNode;//右子节点

    public Node(int value){
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    //添加节点
    /*
    比较待添加节点和当前节点value的大小；
    如果小于，且左节点为空，则挂在左子节点，如果不为空，则向左递归；
    如果小于，且左节点为空，则挂在左子节点，如果不为空，则向左递归；
     */
    public void addNode(Node node){
        //比较待添加节点和当前节点value的大小
        if(this.value > node.value){//当前结点的值大于添加的结点的值
            if(this.leftNode == null){
                this.leftNode = node;//如果左子节点为空，挂左边
            }else{
                this.leftNode.addNode(node);//否则向左子树递归
            }
        }else{//当前结点的值不大于添加的结点的值
            if(this.rightNode == null){
                this.rightNode = node;//如果右子节点为空，挂右边
            }else{
                this.rightNode.addNode(node);//否则向右子树递归
            }
        }
    }

    //根据指定value值查找相应的节点
    public Node searchNode(int value){
        //当前节点即为待查找节点
        if(this.value == value){
            return this;
        }
        if(value < this.value && this.leftNode != null){
            return this.leftNode.searchNode(value);//向左子树递归
        }else if(value >= this.value && this.rightNode != null){
            return this.rightNode.searchNode(value);//向右子树递归
        }else{
            return null;//不存在
        }
    }

    //根据指定value值查找相应节点的父节点
    public Node searchParentNode(int value){
        if(this.leftNode != null && this.leftNode.value == value || this.rightNode != null && this.rightNode.value == value){
            return this;//当前节点即为待查找节点
        }
        if(value < this.value && this.leftNode != null){
            return this.leftNode.searchParentNode(value);//向左子树递归
        }else if(value >= this.value && this.rightNode != null){
            return this.rightNode.searchParentNode(value);//向右子树递归
        }else{
            return null;//不存在
        }
    }


    //中序遍历
    public void infixOrder(){
        //左子树递归
        if(this.leftNode != null){
            this.leftNode.infixOrder();
        }
        //输出当前节点
        System.out.println(this);
        //右子树递归
        if(this.rightNode != null){
            this.rightNode.infixOrder();
        }
    }
}
