package com.renhao.kruskal;

/**
 * @author RenHao
 * @create 2022-11-09 20:27
 */
public class Kruskal {
    private static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {
        //顶点
        char[] vertexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        //邻接矩阵
        int matrix[][] = {
                /*A*//*B*//*C*//*D*//*E*//*F*//*G*/
                /*A*/ {   0,  12, INF, INF, INF,  16,  14},
                /*B*/ {  12,   0,  10, INF, INF,   7, INF},
                /*C*/ { INF,  10,   0,   3,   5,   6, INF},
                /*D*/ { INF, INF,   3,   0,   4, INF, INF},
                /*E*/ { INF, INF,   5,   4,   0,   2,   8},
                /*F*/ {  16,   7,   6, INF,   2,   0,   9},
                /*G*/ {  14, INF, INF, INF,   8,   9,   0}};
        //生成图
        Graph myGraph = new Graph(vertexs, matrix);
//        myGraph.listWeightMatrix();
        //测试kruskal算法
        Kruskal.kruskal(myGraph);

    }

    //kruskal算法：生成最小生成树
    public static void kruskal(Graph graph){

        int edgeNum = graph.getEdgeNum();//获取图中边的数目
        Edge[] edges = graph.getEdges(edgeNum);//将边构成数组
        graph.sortEdgesByWeight(edges);//按照权重对边进行排序

        int index = 0;//指向resultEdges当前位置的索引
        Edge[] resultEdges = new Edge[edgeNum];//存储最终最小生成树的结果，可能会有多余
        //很关键
        int[] roots = new int[graph.vertexNum];//记录每一个顶点的根节点

        for (int i = 0; i < edgeNum; i++) {//遍历图中所有的边
            //取出边的起点和终点
            int start = edges[i].start;
            int end = edges[i].end;
            //判断起点和终点的根是否相同
            int startRoot = Kruskal.getRoot(roots, start);
            int endRoot = Kruskal.getRoot(roots, end);
            if(startRoot != endRoot){//如果该条边的起点和终点的根不相同，则没有构成回路，因此可以加入
                //注意此处是将start的终点指向end的终点!!!
                roots[startRoot] = endRoot;//将startRoot的终点存入roots
                resultEdges[index++] = new Edge(start, end, graph.weightMatrix[start][end]);
            }
        }
        //输出最小生成树
        System.out.println("最小生成树为：");
        for (int i = 0; i < index; i++) {
            System.out.println(resultEdges[i].toString(graph));
        }

    }

    //获取某一个顶点的根节点
    public static int getRoot(int[] roots, int vertex){
        while(roots[vertex] != 0){
            vertex = roots[vertex];
        }
        return vertex;
    }

}

//创建边：表示一条边
class Edge{

    int start;//起点
    int end;//终点
    int weight;//权重

    public Edge(int start, int end, int weight){
        this.start = start;
        this.end = end;
        this.weight = weight;
    }


    public String toString(Graph graph) {
        return "Edge{" +
                "start=" + graph.getVertex(start) +
                ", end=" + graph.getVertex(end) +
                ", weight=" + weight +
                '}';
    }

}
//创建图
class Graph{

    int vertexNum;//顶点数
    char[] vertexs;//顶点数组
    int[][] weightMatrix;//邻接矩阵

    public Graph(char[] vertexs, int[][] weightMatrix){
        vertexNum = vertexs.length;
        this.vertexs = new char[vertexNum];
        this.weightMatrix = new int[vertexNum][vertexNum];
        //初始化顶点
        for (int i = 0; i < vertexNum; i++) {
            this.vertexs[i] = vertexs[i];
        }
        //初始化邻接矩阵
        for (int i = 0; i < vertexNum; i++) {
            for (int j = 0; j < vertexNum; j++) {
                this.weightMatrix[i][j] = weightMatrix[i][j];
            }
        }
    }

    //获取某个索引对应的顶点
    public char getVertex(int verterIndex){
        return vertexs[verterIndex];
    }

    //遍历邻接矩阵
    public void listWeightMatrix() {
        for (int i = 0; i < vertexNum; i++) {
            for (int j = 0; j < vertexNum; j++) {
                System.out.printf("%12d", this.weightMatrix[i][j]);
            }
            System.out.println();
        }
    }

    //获取图中边的数目
    public int getEdgeNum(){
        int edges = 0;//记录边的数目
        for (int i = 0; i < vertexNum; i++) {
            for (int j = i+1; j < vertexNum; j++) {
                if(weightMatrix[i][j] != Integer.MAX_VALUE){
                    edges++;
                }
            }
        }
        return edges;
    }

    //将图中的所有边构建成数组，方便排序
    /**
     *
     * @param edgeNum 图中有效边的数目
     * @return
     */
    public Edge[] getEdges(int edgeNum){
        int index = 0;//指向edges当前位置的索引
        Edge[] edges = new Edge[edgeNum];//记录所有边的信息
        for (int i = 0; i < vertexNum; i++) {
            for (int j = i+1; j < vertexNum; j++) {
                if(weightMatrix[i][j] != Integer.MAX_VALUE){
                    edges[index++] = new Edge(i, j, weightMatrix[i][j]);//将有效边填入edges
                }
            }
        }
        return edges;
    }

    //将图中的边按照权重升序排序
    public void sortEdgesByWeight(Edge[] edges){
        int edgeNum = edges.length;//边的数目
        Edge tempEdge = null;//临时变量，辅助交换
        //冒泡排序
        for (int i = 0; i < edgeNum - 1; i++) {//总共需要比较的轮数
            for (int j = 0; j < edgeNum - 1 - i; j++) {
                if(edges[j].weight > edges[j+1].weight){//升序排序
                    tempEdge = edges[j];
                    edges[j] = edges[j+1];
                    edges[j+1] = tempEdge;
                }
            }
        }
    }

}
