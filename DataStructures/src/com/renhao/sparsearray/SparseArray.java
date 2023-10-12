package com.renhao.sparsearray;

/**
 * @author RenHao
 * @create 2022-09-09 21:11
 */
/*
二维数组转稀疏数组的思路
1.遍历原始的二维数组，得到有效数据的个数sum
2.根据sum就可以创建稀疏数组sparseArr int[sum + 1][3]
3.将二维数组的有效数据存入到稀疏数组

稀疏数组转原始的二维数组的思路

1.先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组，比如上面的chessArr2 = int[11][11]
2.在读取稀疏数组后几行的数据，并赋给原始的二维数组即可

 */
public class SparseArray {

    public static void main(String[] args) {
        //创建一个原始的二维数组 11*11
        //0:表示没有棋子，1 表示黑子 2 表示蓝子
        int[][] oriArr = new int[11][11];
        oriArr[1][2] = 1;
        oriArr[2][3] = 2;
        oriArr[4][5] = 2;
        //输出原始的二维数组
        System.out.println("输出原始数组：");
        for (int[] row : oriArr) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }
        //将二维数组转稀疏数组的思想
        //1.先遍历二维数组，得到非0数据的个数
        int sum = 0;//存放原始数组中非零元素个数
        for (int i = 0; i < oriArr.length; i++) {
            for (int j = 0; j < oriArr[i].length; j++) {
                if (oriArr[i][j] != 0) {
                    sum++;
                }
            }
        }
        // 2.创建对应的稀疏数组
        int[][] sparseArr = new int[sum + 1][3];
        //给稀疏数组赋值
        sparseArr[0][0] = 11;
        sparseArr[0][1] = 11;
        sparseArr[0][2] = sum;//存放原始数组中非零元素个数
        //遍历二维数组，将非0的值存放到sparseArr中
        int count = 0;//记录第几个非0元素
        for (int i = 0; i < oriArr.length; i++) {
            for (int j = 0; j < oriArr[i].length; j++) {
                if (oriArr[i][j] != 0) {
                    sparseArr[++count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = oriArr[i][j];
                }
            }
        }
        //输出稀疏数组的形式
        System.out.println("输出稀疏数组：");
        for (int i = 0; i < sparseArr.length; i++) {
//            System.out.println(sparseArr[i][0] + "\t" + sparseArr[i][1] + "\t" + sparseArr[i][2]);
            System.out.printf("%d\t%d\t%d\n", sparseArr[i][0], sparseArr[i][1], sparseArr[i][2]);
        }
        //将稀疏数组 --> 恢复成二维数组
        /*
        1. 先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组，比如上面的  chessArr2 = int [11][11]
		2. 再读取稀疏数组后几行的数据，并赋给 原始的二维数组 即可.
         */
        //1. 先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组
        int[][] preArr = new int[sparseArr[0][0]][sparseArr[0][1]];
        //2. 在读取稀疏数组后几行的数据(从第二行开始)，并赋给 原始的二维数组 即可
        for (int i = 1; i < sparseArr.length; i++) {
            preArr[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }
        // 输出恢复后的二维数组
        System.out.println("输出恢复后的二维数组");
        for(int[] row : preArr){
            for(int data : row){
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }
    }
}
