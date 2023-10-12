package com.renhao.tree;

/**
 * 顺序存储二叉树
 * @author RenHao
 * @create 2022-10-18 14:22
 */
public class ArrBinaryTreeDemo {
    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        ArrBinaryTree arrBinaryTree = new ArrBinaryTree(arr);
        //测试顺序存储二叉树的前序遍历
//        arrBinaryTree.preOrder();//1, 2, 4, 5, 3, 6, 7
        //测试顺序存储二叉树的中序遍历
//        arrBinaryTree.infixOrder(0);//4, 2, 5, 1, 6, 3, 7
        //测试顺序存储二叉树的后序遍历
        arrBinaryTree.postOrder(0);//4, 5, 2, 6, 7, 3, 1

    }
}

/*
顺序存储二叉树的特点：
1）顺序存储二叉树通常只考虑完全二叉树（只有最后一层存在空缺节点，且集中在右侧）
2）第n个元素的左子节点为2*n + 1
3）第n个元素的右子节点为2*n + 2
4）第n个元素的父节点为（n-1）/ 2
 */
class ArrBinaryTree{

    private int[] arr;//初始数组，用于存储数据

    public ArrBinaryTree(int[] arr){
        this.arr = arr;
    }

    //重载preOrder,不用输出开始下标
    public void preOrder(){
        this.preOrder(0);
    }

    //对输入的数组arr使用前序遍历顺序存储二叉树的方式输出
    /**
     * 编写一个方法，完成顺序存储二叉树的前序遍历
     * @param index 开始下标，数组的下标
     */
    public void preOrder(int index){
        //判断数组是否为空
        if(arr == null || arr.length <= 0){
            System.out.println("数组为空，无法前序遍历");
            return;
        }
        // 不为空，则先输出根节点
        System.out.println(arr[index]);
        //尝试向左递归
        if((2 * index + 1) < arr.length){
            preOrder(2 * index + 1);
        }
        //尝试向右递归
        if((2 * index + 2) < arr.length){
            preOrder(2 * index + 2);
        }
    }

    //中序遍历
    public void infixOrder(int index){
        //判断数组是否为空
        if(arr == null || arr.length <= 0){
            System.out.println("数组为空，无法前序遍历");
        }
        //尝试向左递归
        if((2 * index + 1) < arr.length){
            infixOrder(2 * index + 1);
        }
        //输出根节点
        System.out.println(arr[index]);
        //尝试向右递归
        if((2 * index + 2) < arr.length){
            infixOrder(2 * index + 2);
        }
    }

    //后续遍历
    public void postOrder(int index) {
        //判断数组是否为空
        if(arr == null || arr.length <= 0){
            System.out.println("数组为空，无法前序遍历");
        }
        //尝试向左递归
        if((2 * index + 1) < arr.length){
            postOrder(2 * index + 1);
        }
        //尝试向右递归
        if((2 * index + 2) < arr.length){
            postOrder(2 * index + 2);
        }
        //输出根节点
        System.out.println(arr[index]);
    }

}
