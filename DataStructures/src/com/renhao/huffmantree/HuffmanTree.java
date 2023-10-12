package com.renhao.huffmantree;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author RenHao
 * @create 2022-10-20 16:57
 */
public class HuffmanTree {
    public static void main(String[] args) {

        int arr[] = { 13, 7, 8, 3, 29, 6, 1 };
        //构造赫夫曼树
        Node huffmanTreeRoot = HuffmanTree.createHuffmanTree(arr);
        //前序遍历赫夫曼树
        HuffmanTree.preOrder(huffmanTreeRoot);
    }

    //将一个数组创建成赫夫曼树的方法
    /*
    1)根据数组中的元素值，构建Node，并将Node放入List(便于使用Collections.sort())
    2)对List按升序排序
    3)取出权值最小的两个Node，根据两者的权值和构建其父节点，让父节点指向左右两个子节点
    4)将构造出的父节点添加进List，同时从List中删除这两个子节点
    5)循环2)-4),直至List中剩余一个节点
    6)循环结束，赫夫曼树构建完毕，返回赫夫曼树的根节点
     */
    /**
     *
     * @param arr 待构建成赫夫曼树的数组
     * @return 赫夫曼树的根节点
     */
    public static Node createHuffmanTree(int[] arr) {
        ArrayList<Node> nodes = new ArrayList<>();//存储node的List
        //1)根据数组中的元素值，构建Node，并将Node放入List(便于使用Collections.sort())
        for (int i = 0; i < arr.length; i++) {
            nodes.add(new Node(arr[i]));
        }
        //5)循环2)-4),直至List中剩余一个节点
        while(nodes.size() > 1){
            //2)对List按升序排序
            Collections.sort(nodes);
            //3)取出权值最小的两个Node，根据两者的权值和构建其父节点，让父节点指向左右两个子节点
            Node leftNode = nodes.get(0);
            Node rightNode = nodes.get(1);
            Node parentNode = new Node(leftNode.value + rightNode.value);
            parentNode.left = leftNode;
            parentNode.right = rightNode;
            //4)将构造出的父节点添加进List，同时从List中删除这两个子节点
            nodes.add(parentNode);
            nodes.remove(leftNode);
            nodes.remove(rightNode);
        }
        //6)循环结束，赫夫曼树构建完毕，返回赫夫曼树的根节点
        return nodes.get(0);//循环结束后，nodes中只剩一个node
    }

    //调用Node类中的前序遍历方法，对赫夫曼树进行前序遍历
    public static void preOrder(Node root){
        if(root != null){
            root.preOrder();
        }else{
            System.out.println("赫夫曼树为空，无法遍历");
        }
    }
}


class Node implements Comparable<Node>{
    int value;//节点权值
    //属性
    char c;//字符，用于赫夫曼编码

    Node left;//左子节点
    Node right;//右子节点

    public Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    //判断自然排序顺序的方法
    //默认排列顺序：this, o
    //return -1 代表不用交换顺序，此时o.value > this.value, 因此是按照升序排列
    @Override
    public int compareTo(Node o) {
        //按照节点的value值，升序排列
        return this.value - o.value;
    }

    //前序遍历
    public void preOrder(){
        //1)输出当前节点
        System.out.println(this);
        //2)向左递归
        if(this.left != null){
            this.left.preOrder();
        }
        //3)向右递归
        if(this.right != null){
            this.right.preOrder();
        }
    }
}
