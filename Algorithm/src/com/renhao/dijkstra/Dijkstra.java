package com.renhao.dijkstra;

import java.util.Arrays;

/**
 * 使用迪杰斯特拉算法解决最短路径问题
 * @author RenHao
 * @create 2022-11-10 17:50
 */
public class Dijkstra {
    public static void main(String[] args) {
        //顶点
        char[] vertex = { 'A', 'B', 'C', 'D', 'E', 'F', 'G' };
        //邻接矩阵
        int[][] matrix = new int[vertex.length][vertex.length];
        final int N = 65535;// 表示不可以连接
        matrix[0]=new int[]{N,5,7,N,N,N,2};
        matrix[1]=new int[]{5,N,N,9,N,N,3};
        matrix[2]=new int[]{7,N,N,N,8,N,N};
        matrix[3]=new int[]{N,9,N,N,N,4,N};
        matrix[4]=new int[]{N,N,8,N,N,5,4};
        matrix[5]=new int[]{N,N,N,4,5,N,6};
        matrix[6]=new int[]{2,3,N,N,4,6,N};

        Graph myGraph = new Graph(vertex, matrix);
//        myGraph.showMatrix();
        //测试dijkstra算法，从索引为6的顶点开始
        dijkstra(myGraph, 2);
    }


    /**
     *
     * @param graph 带权无向图
     * @param beginIndex 出发顶点的索引
     */
    public static void dijkstra(Graph graph, int beginIndex){
        //根据开始顶点beginIndex初始化每个顶点的状态，包括isVisited，preVertex，distance
        VertexInfo vertexInfo = new VertexInfo(graph.vertexNum, beginIndex);
//        showDistance(vertexInfo, graph.getVertex(beginIndex));
        updateVertexInfo(vertexInfo, graph, beginIndex);//根据beginIndex更新beginIndex到其它顶点的信息
        for (int i = 0; i < graph.vertexNum - 1; i++) {//保证每个顶点都作为开始顶点，进行一次updateVertexInfo
            int nextIndex = vertexInfo.getNextIndex();//获取下一个开始顶点的索引（广度优先思想）
            updateVertexInfo(vertexInfo, graph, nextIndex);//从下一个开始顶点更新所有顶点的状态信息
        }
        showDistance(vertexInfo, graph.getVertex(beginIndex));//显示最终结果
    }

    //更新每个顶点的状态信息:根据index顶点到周围顶点的距离更新distance和preVertex
    public static void updateVertexInfo(VertexInfo vertexInfo, Graph graph, int index){
        int distance = 0;//记录出发顶点经过index顶点最终到每个顶点的距离
        //遍历index顶点到每个顶点的距离，判断是否需要更改index顶点到每个顶点的距离
        for (int i = 0; i < graph.vertexNum; i++) {
            //出发顶点到i顶点的距离 = 出发顶点到index顶点的距离 + index顶点到i顶点的距离
            distance = vertexInfo.getDistance(index) + graph.matrix[index][i];
            //如果i顶点没有被访问过，且出发顶点经index顶点最终再到i顶点的距离 小于 数组中原先存储的出发顶点到i顶点的距离
            if(vertexInfo.isVisited[i] == 0 && distance < vertexInfo.getDistance(i)){
                vertexInfo.setPreVertex(index, i);//将i顶点的前驱顶点设为index顶点
                vertexInfo.setDistance(i, distance);//将出发顶点到i顶点的距离设为distance
            }
        }

    }

    //展示dijkstra的最终结果，即出发顶点到每个顶点的最短距离以及每个顶点的前驱节点
    /**
     *
     * @param vertexInfo 顶点状态信息
     * @param beginVertex 出发顶点
     */
    public static void showDistance(VertexInfo vertexInfo, char beginVertex){
        vertexInfo.showDistance(beginVertex);
    }

}

//创建图
class Graph{

    int vertexNum;//顶点数
    char[] vertexs;//存储顶点
    int[][] matrix;//邻接矩阵

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

    //显示邻接矩阵
    public void showMatrix(){
        for (int i = 0; i < vertexNum; i++) {
            for (int j = 0; j < vertexNum; j++) {
                System.out.printf("%8d", matrix[i][j]);
            }
            System.out.println();
        }
    }

    //根据索引获得顶点
    public char getVertex(int index){
        return vertexs[index];
    }

}

//记录每个顶点的状态信息：是否已访问，前驱顶点，到该顶点的距离
class VertexInfo{

    int[] isVisited;//记录每个顶点是否被访问过；1 表示已访问；0 表示未访问；
    int[] preVertex;//记录每个顶点的前驱顶点
    int[] distance;//记录开始顶点到每个顶点的距离

    /**
     * 构造器
     * @param length 顶点数目
     * @param index 出发顶点
     */
    public VertexInfo(int length, int index){
        isVisited = new int[length];
        preVertex = new int[length];
        distance = new int[length];
        isVisited[index] = 1;//将出发顶点状态设为已访问
        Arrays.fill(distance, 65535);//将初始距离设为65535
        distance[index] = 0;//将distance[index, index]设为0
    }

    //广度优先，最短路径的思想，获取下一个开始顶点
    public int getNextIndex(){
        int minDistance = 65535;//存储最小距离
        int index = 0;//存储下一个开始顶点
        for (int i = 0; i < isVisited.length; i++) {//遍历所有顶点的状态
            if(isVisited[i] == 0 && distance[i] < minDistance){//找出在所有未访问过的顶点中，出发顶点到哪个顶点的距离最小，广度优先
                minDistance = distance[i];
                index = i;
            }
        }
        isVisited[index] = 1;//将该点状态设为已访问
        return index;
    }

    //更改出发顶点到某个顶点的距离:将出发顶点到index顶点的距离设为distance
    public void setDistance(int index, int distance){
        this.distance[index] = distance;
    }

    //更改前驱顶点:将curVertex顶点的前驱顶点设为preVertex
    public void setPreVertex(int preVertex, int curVertex){
        this.preVertex[curVertex] = preVertex;
    }

    //获取出发顶点到index顶点的距离
    public int getDistance(int index){
        return distance[index];
    }

    //展示最终结果
    /**
     *
     * @param vertex 出发顶点
     */
    public void showDistance(char vertex){
        //展示每个顶点是否已访问过
        System.out.println("每个顶点是否被访问过：");
        System.out.println(Arrays.toString(isVisited));
        //展示每个顶点的前驱节点
        System.out.println("每个顶点的前驱节点：");
        System.out.println(Arrays.toString(preVertex));
        //展示出发顶点到每个顶点的距离
        System.out.printf("%c到每个顶点的距离为：", vertex);
        System.out.println(Arrays.toString(distance));
    }

}