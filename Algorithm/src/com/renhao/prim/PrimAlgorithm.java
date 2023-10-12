package com.renhao.prim;

import java.util.Arrays;

/**
 * @author RenHao
 * @create 2022-11-08 21:04
 */
public class PrimAlgorithm {

    public static void main(String[] args) {

        char[] vertexs = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};//顶点
        int[][] weight=new int[][]{//边
                {10000,5,7,10000,10000,10000,2},
                {5,10000,10000,9,10000,10000,3},
                {7,10000,10000,10000,8,10000,10000},
                {10000,9,10000,10000,10000,4,10000},
                {10000,10000,8,10000,10000,5,4},
                {10000,10000,10000,4,5,10000,6},
                {2,3,10000,10000,4,6,10000}};
        //创建图
        Graph myGraph = new Graph(vertexs);
        myGraph.createWeight(weight);//初始化邻接矩阵
//        myGraph.showWeight();//显示邻接矩阵
        //测试Prim算法
        MinTree minTree = new MinTree();
        minTree.prim(myGraph, 0);
    }



}

//创建最小生成树
class MinTree{

    //Prim算法
    /**
     *
     * @param graph 欲生成最小生成树的图
     * @param start 开始顶点
     */
    public void prim(Graph graph, int start){
        int vertexs = graph.vertex;//图的顶点数量
        //isVisited保存顶点是否被访问过
        int[] isVisited = new int[vertexs];
        isVisited[start] = 1;//将开始顶点置为已访问过
        int minWeight;//保存遍历过程中的最小权重
        int startVertex = -1;//开始顶点
        int endVertex = -1;//结束顶点
        for(int m = 0; m < vertexs - 1; m++){//Prim算法生成的边的数量是顶点数量减1
            minWeight = 10000;//开始寻找前，先重置
            for (int i = 0; i < vertexs; i++) {//表示已经访问过的顶点
                for (int j = 0; j < vertexs; j++) {//表示未访问过的顶点
                    //如果当前遍历到的已访问顶点和未访问顶点之间的权重更小
                    if(isVisited[i] == 1 && isVisited[j] == 0 && graph.weight[i][j] < minWeight){
                        minWeight = graph.weight[i][j];//则重置minWeight
                        startVertex = i;//保存开始顶点
                        endVertex = j;//保存结束顶点
                    }
                }
            }
            isVisited[endVertex] = 1;//将结束顶点置为已访问过
            System.out.println("第" + (m+1) + "条边：<" + graph.vertexData[startVertex] + ", " + graph.vertexData[endVertex] + ">, 权重为：" + graph.weight[startVertex][endVertex]);
        }
    }

}

//创建图
class Graph{
    int vertex;//顶点数
    char[] vertexData;//顶点
    int[][] weight;//边/权重

    //初始化图
    public Graph(char[] vertexs){
        this.vertex = vertexs.length;
        vertexData = new char[vertexs.length];
        weight = new int[vertexs.length][vertexs.length];
        for (int i = 0; i < vertexs.length; i++) {
            vertexData[i] = vertexs[i];
        }
    }

    //创建邻接矩阵
    public void createWeight(int[][] weight){
        for (int i = 0; i < vertex; i++) {
            for (int j = 0; j < vertex; j++) {
                this.weight[i][j] = weight[i][j];
            }
        }
    }

    //显示邻接矩阵
    public void showWeight(){
        for(int[] item : weight){
            System.out.println(Arrays.toString(item));
        }
    }

}