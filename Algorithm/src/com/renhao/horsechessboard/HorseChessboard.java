package com.renhao.horsechessboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 马踏棋盘算法
 * @author RenHao
 * @create 2022-11-11 17:14
 */
public class HorseChessboard {
    public static void main(String[] args) {

        //初始化棋盘
        HorseChess horseChess = new HorseChess(8, 8);
        long start = System.currentTimeMillis();
        //调用马踏棋盘算法，第一步从第一行第一列的点开始，step要从1开始
        horseChess.horseChessboardAlgorithm(0, 0, 1);
        long end = System.currentTimeMillis();
        System.out.println("共耗时：" + (end - start) + "ms");
        //输出最终结果
        horseChess.showChessboard();

    }

}

//马踏棋盘算法
class HorseChess{

    int[][] chessBoard;//表示棋盘
    int chessLength;//棋盘长度
    int chessWidth;//棋盘宽度
    boolean[] isVisited;//表示棋盘上的每个点是否被访问过
    boolean finished;//代表棋盘上的每个点是否都被访问过，如果不加此变量，整个棋盘会被回溯到初始状态


    /**
     * 初始化棋盘数据
     * @param chessLength 棋盘长度
     * @param chessWidth 棋盘宽度
     */
    public HorseChess(int chessLength, int chessWidth){
        //初始化棋盘
        chessBoard = new int[chessLength][chessWidth];
        this.chessLength = chessLength;
        this.chessWidth = chessWidth;
        //初始化棋盘上的每个点是否被访问过
        isVisited = new boolean[chessLength * chessWidth];

    }

    /**
     *
     * @param beginRow 起点所在行，从0开始
     * @param beginCol 起点所在列，从0开始
     * @param step 表示当前正在走的是第几步，从1开始
     */
    int count;
    public void horseChessboardAlgorithm(int beginRow, int beginCol, int step){
        //先把当前位置状态设为已访问
        chessBoard[beginRow][beginCol] = step;
        isVisited[beginRow * chessWidth + beginCol] = true;
        //获取从当前点出发下一步可以走的点的集合
        ArrayList<Point> nextPoints = getNextPoints(new Point(beginCol, beginRow));
        //使用贪心算法优化
        sortNextPoints(nextPoints);
        //遍历nextPoints
        while(!nextPoints.isEmpty()){//如果nextPoints不为空
            Point firstPoint = nextPoints.remove(0);//按顺序取出第一个Point
            //y坐标代表所在行，x坐标代表所在列
            if(!isVisited[firstPoint.y * chessWidth + firstPoint.x]){//如果当前点还没有被访问过，则以该点为出发点进行迭代
                System.out.println("我执行了" + count++);
                horseChessboardAlgorithm(firstPoint.y, firstPoint.x, step + 1);//迭代
            }
        }
        //当nextPoints为空，即从该点出发，无路可走时，判断是否走完整个棋盘
        if(step < chessLength * chessWidth && !finished){//如果没有走完整个棋盘，但是无路可走时
            //将该点状态设为未访问过，然后回溯到上一步
            chessBoard[beginRow][beginCol] = 0;
            isVisited[beginRow * chessWidth + beginCol] = false;
        }else{
            finished = true;//如果整个棋盘已经走完，则将fininshed置为true，不再回溯
        }
    }

    //使用贪心算法的思想优化马踏棋盘算法
    //在选择下一步走哪个点时，选取方法为：当选择该点时，以该点为出发点，下一步可走的点的集合长度最小
    //根据原则对nextPoints中的点进行排序
    public void sortNextPoints(ArrayList<Point> nextPoints){
        nextPoints.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {//根据o1和o2下一步可走的点的集合的长度进行升序排序
                ArrayList<Point> nextPoints1 = getNextPoints(o1);
                ArrayList<Point> nextPoints2 = getNextPoints(o2);
                if(nextPoints1.size() < nextPoints2.size()){
                    return -1;
                } else if (nextPoints1.size() == nextPoints2.size()) {
                    return 0;
                }else{
                    return 1;
                }
            }
        });
    }

    //获取从当前点出发，下一步可以走的点
    public ArrayList<Point> getNextPoints(Point curPoint){
        ArrayList<Point> nextPoints = new ArrayList<>();//存储从curPoint出发，下一步可以走的点的集合
        Point tempPoint = new Point();//临时变量
        //表示从curPoint出发，下一步可以走5所在的位置
        if((tempPoint.x = curPoint.x - 2) >= 0 && (tempPoint.y = curPoint.y - 1) >= 0){
            nextPoints.add(new Point(tempPoint));
        }
        //表示从curPoint出发，下一步可以走6所在的位置
        if((tempPoint.x = curPoint.x - 1) >= 0 && (tempPoint.y = curPoint.y - 2) >= 0){
            nextPoints.add(new Point(tempPoint));
        }
        //表示从curPoint出发，下一步可以走7所在的位置
        if((tempPoint.x = curPoint.x + 1) < chessWidth && (tempPoint.y = curPoint.y - 2) >= 0){
            nextPoints.add(new Point(tempPoint));
        }
        //表示从curPoint出发，下一步可以走0所在的位置
        if((tempPoint.x = curPoint.x + 2) < chessWidth && (tempPoint.y = curPoint.y - 1) >= 0){
            nextPoints.add(new Point(tempPoint));
        }
        //表示从curPoint出发，下一步可以走1所在的位置
        if((tempPoint.x = curPoint.x + 2) < chessWidth && (tempPoint.y = curPoint.y + 1) < chessLength){
            nextPoints.add(new Point(tempPoint));
        }
        //表示从curPoint出发，下一步可以走2所在的位置
        if((tempPoint.x = curPoint.x + 1) < chessWidth && (tempPoint.y = curPoint.y + 2) < chessLength){
            nextPoints.add(new Point(tempPoint));
        }
        //表示从curPoint出发，下一步可以走3所在的位置
        if((tempPoint.x = curPoint.x - 1) >= 0 && (tempPoint.y = curPoint.y + 2) < chessLength){
            nextPoints.add(new Point(tempPoint));
        }
        //表示从curPoint出发，下一步可以走4所在的位置
        if((tempPoint.x = curPoint.x - 2) >= 0 && (tempPoint.y = curPoint.y + 1) < chessLength){
            nextPoints.add(new Point(tempPoint));
        }
        return nextPoints;//返回接下来可以走的点的集合
    }

    //输出棋盘状态
    public void showChessboard(){
        //输出棋盘中每个点是否被访问过
        System.out.println("棋盘中每个点是否被访问过：");
        System.out.println(Arrays.toString(isVisited));
        //输出棋盘状态
        System.out.println("棋盘状态：");
        for(int[] item : chessBoard){
            System.out.println(Arrays.toString(item));
        }
    }

}
