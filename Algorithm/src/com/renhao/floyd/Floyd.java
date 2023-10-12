package com.renhao.floyd;

import java.util.Arrays;

/**
 * @author RenHao
 * @create 2022-11-11 10:45
 */
public class Floyd {
    public static void main(String[] args) {
        //顶点数组
        char[] vertex = { 'A', 'B', 'C', 'D', 'E', 'F', 'G' };
        //创建邻接矩阵
        int[][] matrix = new int[vertex.length][vertex.length];
        final int N = 65535;
        matrix[0] = new int[] { 0, 5, 7, N, N, N, 2 };
        matrix[1] = new int[] { 5, 0, N, 9, N, N, 3 };
        matrix[2] = new int[] { 7, N, 0, N, 8, N, N };
        matrix[3] = new int[] { N, 9, N, 0, N, 4, N };
        matrix[4] = new int[] { N, N, 8, N, 0, 5, 4 };
        matrix[5] = new int[] { N, N, N, 4, 5, 0, 6 };
        matrix[6] = new int[] { 2, 3, N, N, 4, 6, 0 };

        //创建图
        Graph myGraph = new Graph(vertex, matrix);
//        myGraph.showMatrix();
        //测试FloydAlgorithm
        FloydAlgorithm floydAlgorithm = new FloydAlgorithm(myGraph);
        floydAlgorithm.showResult();

    }
}

//floydAlgorithm
class FloydAlgorithm{

    Graph graph;//代表一个图
    int[][] preVertex;//保存图中每个顶点的前驱顶点
    int[][] distance;//保存图中每个顶点到其它顶点的距离

    //初始化preVertex和distance
    public FloydAlgorithm(Graph graph){
        this.graph = graph;
        int vertexNum = graph.vertexNum;//顶点数
        preVertex = new int[vertexNum][vertexNum];
        distance = new int[vertexNum][vertexNum];
        //初始化preVertex
        for (int i = 0; i < vertexNum; i++) {
            Arrays.fill(preVertex[i], i);
        }
        //初始化distance
        for (int i = 0; i < vertexNum; i++) {
            for (int j = 0; j < vertexNum; j++) {
                distance[i][j] = graph.matrix[i][j];
            }
        }
        //调用floyd算法，更新distance和preVertex
        floyd();
    }

    //floyd算法核心，更新前驱顶点矩阵和距离矩阵
    public void floyd(){
        int len = 0;//记录出发顶点经过中间顶点到终点的路径长度
        for (int i = 0; i < graph.vertexNum; i++) {//中间顶点
            for (int j = 0; j < graph.vertexNum; j++) {//出发顶点
                for (int k = 0; k < graph.vertexNum; k++) {//终点
                    len = distance[j][i] + distance[i][k];
                    if(len < distance[j][k]){//如果len小于distance矩阵中之前存储的j到k顶点之间的距离
                        distance[j][k] = len;//更新出发顶点j到终点k之间的距离
                        preVertex[j][k] = preVertex[i][k];//更新出发顶点j和终点k之间的中间顶点，更新k的前驱顶点
                    }
                }
            }
        }
    }

    //展示最终结果：preVertex和distance
    public void showResult(){
        System.out.println("前驱顶点矩阵：");
        for (int i = 0; i < preVertex.length; i++) {
            for (int j = 0; j < preVertex[i].length; j++) {
                System.out.printf("%3c", graph.vertexs[preVertex[i][j]]);
            }
            System.out.println();
        }
        System.out.println("最短距离矩阵：");
        for (int i = 0; i < distance.length; i++) {
            for (int j = 0; j < distance[i].length; j++) {
                System.out.printf("%3d", distance[i][j]);
            }
            System.out.println();
        }
    }

}

//创建图
class Graph{

    int vertexNum;//顶点数
    char[] vertexs;//顶点数组
    int[][] matrix;//邻接矩阵

    /**
     * 初始化图
     * @param vertexs 顶点数组
     * @param matrix 邻接矩阵
     */
    public Graph(char[] vertexs, int[][] matrix){
        this.vertexNum = vertexs.length;
        this.vertexs = new char[vertexNum];
        this.matrix = new int[vertexNum][vertexNum];
        for (int i = 0; i < vertexNum; i++) {
            this.vertexs[i] = vertexs[i];
        }
        for (int i = 0; i < vertexNum; i++) {
            for (int j = 0; j < vertexNum; j++) {
                this.matrix[i][j] = matrix[i][j];
            }
        }
    }

    //显示图的邻接矩阵
    public void showMatrix(){
        for (int i = 0; i < vertexNum; i++) {
            for (int j = 0; j < vertexNum; j++) {
                System.out.printf("%8d", matrix[i][j]);
            }
            System.out.println();
        }
    }

}