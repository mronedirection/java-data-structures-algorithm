package com.renhao.recursion;

/**
 * @author RenHao
 * @create 2022-09-22 22:50
 */
public class MiGong {

    public static void main(String[] args) {
        //先创建一个8*7的二维数组，模拟迷宫
        //地图
        int[][] map = new int[8][7];
        //使用1表示墙
        //上下全部置为1
        for (int i = 0; i < 7; i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }

        //左右全部置为1
        for (int i = 0; i < 8; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }

        //设置挡板，挡板用1表示
        map[3][1] = 1;
        map[3][2] = 1;
//		map[1][2] = 1;
//		map[2][2] = 1;

        //输出地图
        System.out.println("地图的情况");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }

        setWay2(map, 1, 1);

        //输出轨迹
        System.out.println("输出轨迹");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }


    }

    //使用递归回溯来给小球找路
    /**说明
     * 1.map表示地图
     * 2.i, j 表示从地图的哪个位置开始出发 (1, 1)
     * 3.如果小球能到map[6][5]位置，则说明通路找到
     * 4.约定：当map[i][j]为 0 表示该点没有走过，当为 1 表示墙；2 表示通路可以走；3 表示该点已经走过，但是走不通（上下左右都不能走）
     * 5.在走迷宫时，需要确定一个策略（方法）下-右-上-左，如果该点走不通，再回溯
     */
    public static boolean setWay(int[][] map, int i, int j){
        if(map[6][5] == 2){//已经走到出口
            return true;
        }else{
            if(map[i][j] == 0){
                map[i][j] = 2;
                if(setWay(map, i + 1, j)){//判断向下能不能走
                    return true;
                } else if (setWay(map, i, j + 1)) {//如果向下走不了，向右走
                    return true;
                } else if (setWay(map, i - 1, j)) {//如果下右走不了，向上走
                    return true;
                } else if (setWay(map, i, j - 1)) {//如果下右上走不了，向左走
                    return true;
                }else{
                    //说明该点走不通，置为3，再返回上一个点
                    map[i][j] = 3;
                    return false;
                }
            }else{//如果下一个点不为0，则代表该点可能是1，2，3，均不能走，返回上一个点
                return false;
            }
        }
    }

    //修改找路的策略，改成上-右-下-左
    public static boolean setWay2(int[][] map, int i, int j){
        if(map[6][5] == 2){//已经走到出口
            return true;
        }else{
            if(map[i][j] == 0){
                map[i][j] = 2;
                if(setWay2(map, i - 1, j)){//判断向上能不能走
                    return true;
                } else if (setWay2(map, i, j + 1)) {//如果向上走不了，向右走
                    return true;
                } else if (setWay2(map, i + 1, j)) {//如果上右走不了，向下走
                    return true;
                } else if (setWay2(map, i, j - 1)) {//如果上右下走不了，向左走
                    return true;
                }else{
                    //说明该点走不通，置为3，再返回上一个点
                    map[i][j] = 3;
                    return false;
                }
            }else{//如果下一个点不为0，则代表该点可能是1，2，3，均不能走，返回上一个点
                return false;
            }
        }
    }


}
