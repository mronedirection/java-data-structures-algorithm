package com.renhao.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author RenHao
 * @create 2022-10-27 20:54
 */
public class GraphDemo {
    public static void main(String[] args) {

        //顶点数组
//        String[] vertexs = {"A", "B", "C", "D", "E"};
        String vertexs[] = {"1", "2", "3", "4", "5", "6", "7", "8"};

        Graph graph = new Graph(vertexs.length);
        for (int i = 0; i < vertexs.length; i++) {
            graph.insertVertex(vertexs[i]);
        }

        //添加边：A-B A-C B-C B-D B-E
        //0-A 1-B 2-C 3-D 4-E
//        graph.insertEdge(0, 1, 1);
//        graph.insertEdge(0, 2, 1);
//        graph.insertEdge(1, 2, 1);
//        graph.insertEdge(1, 3, 1);
//        graph.insertEdge(1, 4, 1);
        //图2:
        graph.insertEdge(0, 1, 1);
        graph.insertEdge(0, 2, 1);
        graph.insertEdge(1, 3, 1);
        graph.insertEdge(1, 4, 1);
        graph.insertEdge(3, 7, 1);
        graph.insertEdge(4, 7, 1);
        graph.insertEdge(2, 5, 1);
        graph.insertEdge(2, 6, 1);
        graph.insertEdge(5, 6, 1);

        //显示邻接矩阵
//        graph.showGraph();
        //dfs:深度优先遍历
//        System.out.println("深度优先遍历：");
//        graph.dfs();
        //bfs:广度优先遍历
        System.out.println("广度优先遍历：");
        graph.bfs();

    }
}

//创建一个图
class Graph{

    private ArrayList<String> vertexList;//存储图的所有顶点
    private int[][] edges;//存储图的所有边之间的关系
    private int numsOfEdges;//存储图的所有边的数目
    private boolean[] isVisited;//存储节点是否被访问过的信息


    public Graph(int n){
        vertexList = new ArrayList<>(n);
        edges = new int[n][n];
        numsOfEdges = 0;
        isVisited = new boolean[n];
    }

    //获取顶点数量
    public int getNumsOfVertex(){
        return vertexList.size();
    }

    //获取边的数目
    public int getNumsOfEdges(){
        return numsOfEdges;
    }

    //返回下标i对应的顶点，与顶点加入的顺序有关
    //例如：0->"A" 1->"B" 2->"C"
    public String getVertexByIndex(int i){
        return vertexList.get(i);
    }

    //返回顶点v1和v2之间的权值
    public int getWeight(int v1, int v2){
        return edges[v1][v2];
    }

    //添加顶点
    public void insertVertex(String vertex){
        vertexList.add(vertex);
    }

    //添加边，即两个顶点之间的关系
    /**
     *
     * @param v1 第一个顶点
     * @param v2 第二个顶点
     * @param weight 两个顶点之间的权值
     */
    public void insertEdge(int v1, int v2, int weight){
        edges[v1][v2] = edges[v2][v1] = weight;
        numsOfEdges++;//边的数量加1
    }

    //显示图对应的邻接矩阵
    public void showGraph(){
        for(int[] link : edges){
            System.out.println(Arrays.toString(link));
        }
    }

    //获取index节点的邻接节点
    /**
     *
     * @param index 获取index节点的邻接节点
     * @param startIndex 从第 startIndex 节点开始查起, startIndex:[0, getNumsOfVertex())
     * @return 邻接节点的索引，未找到则返回-1
     */
    public int getNeighbor(int index, int startIndex){
        for (int i = startIndex; i < getNumsOfVertex(); i++) {
            if(edges[index][i] > 0){
                return i;
            }
        }
        return -1;
    }

    //dfs:图的深度优先遍历
    /*
    [0, 1, 1, 0, 0]
    [1, 0, 1, 1, 1]
    [1, 1, 0, 0, 0]
    [0, 1, 0, 0, 0]
    [0, 1, 0, 0, 0]
     */
    //重载dfs, 以每一个节点为起点，进行一次深度优先遍历，以防止遇到非连通图
    //对于非联通图，需要以每一个节点为起点，进行深度优先遍历
    //对于连通图，只需要任意找一个顶点作为起点开始遍历，即可完成对图的深度优先遍历
    public void dfs(){
        for (int i = 0; i < getNumsOfVertex(); i++) {//以每一个节点为起点尝试进行一次遍历
            if(!isVisited[i]){//如果i节点没有被访问过，则从第i个节点开始深度优先遍历
                dfs(isVisited, i);
            }
        }
    }
    /**
     *
     * @param index 从第index节点开始进行一次深度优先遍历
     */
    public void dfs(boolean[] isVisited, int index){
        //先输出当前索引指向的节点
        System.out.print(getVertexByIndex(index) + "->");
        //将当前节点状态设为已访问
        isVisited[index] = true;
        //获取index节点的第一个邻接顶点
        int firstNeighbor = getNeighbor(index, 0);
        while(firstNeighbor != -1){//当前节点存在邻接节点
            if(!isVisited[firstNeighbor]){//如果firstNeighbor没有被访问过
                dfs(isVisited, firstNeighbor);//递归
            }else{//如果firstNeighbor被访问过
                firstNeighbor = getNeighbor(index, firstNeighbor + 1);//查找index节点的下一个邻接节点
            }
        }
    }

    //bfs:图的广度优先遍历
    //重载bfs, 以图中的每一个节点为起点，进行广度优先遍历
    //对于非联通图，需要以每一个节点为起点，进行广度优先遍历
    //对于连通图，只需要任意找一个顶点作为起点开始遍历，即可完成对图的广度优先遍历
    public void bfs(){
        for (int i = 0; i < getNumsOfVertex(); i++) {//以每一个节点为起点尝试进行一次遍历
            if(!isVisited[i]){//如果i节点没有被访问过，则从第i个节点开始广度优先遍历
                bfs(isVisited, i);
            }
        }
    }
    /**
     * 从第index个节点开始，对图进行一次广度优先遍历
     * @param isVisited 记录每个节点的状态信息（是否已访问）
     * @param index 从第index个节点开始遍历
     */
    public void bfs(boolean[] isVisited, int index){
        //新建一个队列，按访问顺序存储已经访问过的节点
        LinkedList<Integer> queue = new LinkedList<Integer>();
        int firstIndex;//存储队首节点索引
        int firstNeighbor;//存储firstIndex节点的第一个邻接节点
        System.out.print(getVertexByIndex(index) + "->");//输出当前节点
        isVisited[index] = true;//将当前节点的状态置为已访问
        queue.addLast(index);//将当前已访问过的index节点加入队尾
        while(!queue.isEmpty()){//如果队列不为空
            firstIndex = queue.removeFirst();//取出队首节点
            firstNeighbor = getNeighbor(firstIndex, 0);//获取firstIndex节点的第一个邻接节点
            while(firstNeighbor != -1){//如果firstNeighbor存在
                if(!isVisited[firstNeighbor]){//如果该节点未被访问过
                    System.out.print(getVertexByIndex(firstNeighbor) + "->");//输出firstNeighbor指向的节点
                    isVisited[firstNeighbor] = true;//状态置为已访问
                    queue.addLast(firstNeighbor);//加入队尾
                }
                //未使用递归，因此体现的是广度优先遍历
                firstNeighbor = getNeighbor(firstIndex, firstNeighbor + 1);//从(firstNeighbor + 1)节点开始，查找firstIndex节点的下一个邻接节点
            }
        }
    }
}