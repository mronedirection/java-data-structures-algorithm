package com.renhao.huffmancode;


import java.io.*;
import java.util.*;

/**赫夫曼编码；
 * 一、压缩过程：
 * 1)统计待压缩字符串中出现的”字符“以及相应的”出现次数“
 * 2)根据"字符"以及"出现次数"构建赫夫曼树：
 *      1)根据根据"字符"以及"出现次数"构建由node组成的List
 *      2)根据List中不同node的"出现次数"构建赫夫曼树
 * 3)根据赫夫曼树得到每个字符的赫夫曼编码方式
 * 4)根据每个字符的赫夫曼编码方式得到待压缩字符串的赫夫曼编码串（StringBuilder），并将赫夫曼编码串转变为以byte[]的方式进行存储
 *
 * 二、解压缩过程：
 * 1)将以赫夫曼编码方式编码后的byte[]转为赫夫曼编码串（StringBuilder）
 * 2)根据赫夫曼编码串（StringBuilder）以及赫夫曼编码表(Map<String, Byte>)得到原始字符串(byte[])
 * 在处理最后一位byte时，要特别注意，不能少0也不能多0
 * @author RenHao
 * @create 2022-10-24 14:45
 */
public class HuffmanCode {
    public static void main(String[] args) {
        //测试压缩文件
//        String souFile = "src.bmp";
//        String desFile = "src.zip";
//        String souFile = "souFile.txt";
//        String desFile = "desFile.zip";
        //如果文件本身就是经过压缩的，那么使用赫夫曼编码再压缩效率不会有明显变化，
        //比如视频，ppt等文件
        String souFile = "1.pptx";
        String desFile = "1.zip";
        zipFile(souFile, desFile);
        //测试解压文件
//        String zipFile = "src.zip";
//        String decodeFile = "decodeFile.bmp";
//        String zipFile = "desFile.zip";
//        String decodeFile = "decodeFile.txt";
        String zipFile = "1.zip";
        String decodeFile = "decodeFile1.pptx";
        unzipFile(zipFile, decodeFile);
    }


    /**
     * 编写一个方法用于解压文件
     * @param zipFile 待解压文件
     * @param decodeFile 解压后的保存路径
     */
    public static void unzipFile(String zipFile, String decodeFile){
        //打开文件流
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        FileOutputStream fos = null;
        try {
            //打开文件流，以对象的方式读入
            fis = new FileInputStream(zipFile);
            ois = new ObjectInputStream(fis);
            //读出写入的对象
            byte[] bytes = (byte[])ois.readObject();
            Map<Byte, String> huffmanMap = (Map<Byte, String>)ois.readObject();
            int actualLen = (int) ois.readObject();
            //解压文件
            byte[] decodeBytes = decode(bytes, huffmanMap, actualLen);
            //将解压出的数据写入目标文件
            fos = new FileOutputStream(decodeFile);
            fos.write(decodeBytes);
            System.out.println("解压缩成功");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }finally{
            try {
                if(fos != null)
                    fos.close();
                if(ois != null)
                    ois.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //编写一个方法用于压缩文件
    /**
     *
     * @param souFile 源文件路径
     * @param desFile 压缩后文件路径
     */
    public static void zipFile(String souFile, String desFile){
        //打开文件流
        FileInputStream fis = null;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fis = new FileInputStream(souFile);
            //每次读取文件长度
            byte[] buffer = new byte[fis.available()];
            //读入文件
            fis.read(buffer);
            //压缩
            byte[] zipBytes = zip(buffer);
            //将zipBytes, byteStringMap以对象流的方式写出至目标文件
            fos = new FileOutputStream(desFile);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(zipBytes);//写出压缩后的byte数组
            oos.writeObject(byteStringMap);//写出赫夫曼编码表
            oos.writeObject(actualLen);//写出byte数组中最后一位的实际有效位数
            System.out.println("压缩成功");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }finally{
            try {
                if(oos != null)
                    oos.close();
                if(fis != null)
                    fis.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    /**
     * 整合压缩过程
     * @param originalBytes 待压缩的byte[]
     * @return 赫夫曼压缩后的byte[]
     */
    public static byte[] zip(byte[] originalBytes){
        //获取originalBytes的词频
        Map<Byte, Integer> charFrequency = getCharFrequency(originalBytes);
        //获取huffman编码表，byteString，存放每个字符的赫夫曼编码
        Node huffmanTreeRoot = getHuffmanTree(charFrequency);
        getSinCharHuffmanCode(huffmanTreeRoot);
        //根据huffman编码表对originalBytes进行压缩
        byte[] bytes = huffmanZip(originalBytes);
        return bytes;
    }
    //一、压缩过程：
    //1)统计待压缩字符串中出现的”字符“以及相应的”出现次数“
    /**
     *
     * @param bytes 存储待压缩字符串的byte[]
     * @return key存放出现的"字符“, value存放相应的"出现次数"
     */
    public static Map<Byte, Integer> getCharFrequency(byte[] bytes){
        HashMap<Byte, Integer> map = new HashMap<>();
        Integer count;//统计字符出现次数
        for(byte b : bytes){
            count = map.get(b);
            if(count == null){//避免空指针
                map.put(b, 1);
            }else{
                map.put(b, count + 1);
            }
        }
        return map;
    }

    //2)根据"字符"以及"出现次数"构建赫夫曼树：
    /**
     *
     * @param map 存放"字符"以及"出现次数"的map
     * @return 赫夫曼树的根节点
     */
    public static Node getHuffmanTree(Map<Byte, Integer> map){
        ArrayList<Node> nodes = new ArrayList<>();
        //1.构建List
        for(Map.Entry<Byte, Integer> entries : map.entrySet()){
            nodes.add(new Node(entries.getKey(), entries.getValue()));
        }
        //2.根据weight构建赫夫曼树
        while(nodes.size() > 1){
            //排序（升序）
            Collections.sort(nodes);
            //取出末尾两个元素
            Node leftNode = nodes.get(0);
            Node rightNode = nodes.get(1);
            //构建父节点
            Node parentNode = new Node(null,leftNode.weight + rightNode.weight);
            parentNode.leftNode = leftNode;
            parentNode.rightNode = rightNode;
            //从List中删除左右两个子节点，保留父节点
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            nodes.add(parentNode);
        }
        Node root = nodes.get(0);
        return root;
    }

    //3)根据赫夫曼树得到每个字符的赫夫曼编码方式
    static Map<Byte, String> byteStringMap = new HashMap<>();//存放每个字符的赫夫曼编码
    //重载getSinCharHuffmanCode(Node node, String code, StringBuilder builder)
    public static void getSinCharHuffmanCode(Node root){
        StringBuilder builder = new StringBuilder();
        if(root != null){
            getSinCharHuffmanCode(root, "", builder);
        }
    }
    /**
     *
     * @param node 递归传入赫夫曼树的一个节点
     * @param code 向左递归追加"0", 向右递归追加"1"
     * @param builder 在上层递归结束后的StringBuilder基础上追加新的字符
     */
    public static void getSinCharHuffmanCode(Node node, String code, StringBuilder builder){
//        Map<Byte, String> byteStringMap = new HashMap<>();
        StringBuilder stringBuilder = new StringBuilder(builder);
        stringBuilder.append(code);
        if(node != null){//如果node为空，不做处理
            if(node.data == null){//判断是否为叶子节点
                //如果不是叶子节点，向左递归
                getSinCharHuffmanCode(node.leftNode, "0", stringBuilder);
                //向右递归
                getSinCharHuffmanCode(node.rightNode, "1", stringBuilder);
            }else{
                byteStringMap.put(node.data, stringBuilder.toString());//如果是叶子节点，将当前节点存储的字符以及对应的赫夫曼编码方式放入map
            }
        }
    }
    static int actualLen;//记录最后一个字节的实际位数

    //4)根据每个字符的赫夫曼编码方式得到待压缩的原始字符串的赫夫曼编码串
    public static byte[] huffmanZip(byte[] oriBytes){
        //1.将原始字符串转为用赫夫曼方式编码的字符串
        StringBuilder builder = new StringBuilder();
        for(byte b : oriBytes){
            builder.append(byteStringMap.get(b));
        }
//        System.out.println("huffmanZip:" + builder);
        //2.将builder转为byte[]
        //获取转换后的数组长度
        int len;//保存转换后的数组长度
        if(builder.length() % 8 == 0){
            len = builder.length() / 8;
        }else{
            len = builder.length() / 8 + 1;
        }
        //每次从builder中取8位，以二进制转十进制的形式，转为byte，存入byte[]
        byte[] bytes = new byte[len];
        int index = 0;//当前指针指向位置
        for (int i = 0; i < builder.length(); i += 8) {
            if(i + 8 < builder.length()){
                bytes[index++] = (byte)Integer.parseInt(builder.substring(i, i + 8), 2);//String -以二进制形式转换-> Integer --> byte
            }else{
                //记录最后一个字节实际位数
                actualLen = builder.length() - i;//记录最后一个字节的实际位数
                bytes[index++] = (byte)Integer.parseInt(builder.substring(i), 2);
            }
        }
        return bytes;
    }

    //二、解压缩过程：
//            * 1)将以赫夫曼编码方式编码后的byte[]转为赫夫曼编码串（StringBuilder）
//            * 2)根据赫夫曼编码串（StringBuilder）以及赫夫曼编码表(Map<String, Byte>)得到原始字符串(byte[])

    /**
     *
     * @param bytes 待解码的byte[]
     * @param map 赫夫曼编码表
     * @param actualLen 记录最后一个字节的实际位数
     * @return 解码后的原始字符byte[]
     */
    public static byte[] decode(byte[] bytes, Map<Byte, String> map, int actualLen){
        //1)将以赫夫曼编码方式编码后的byte[]转为赫夫曼编码串（StringBuilder）
        StringBuilder builder = new StringBuilder();//存储赫夫曼编码串（StringBuilder）
        for (int i = 0; i < bytes.length; i++) {
            //判断是否为最后一位，如果是最后一位，则endFlag置为true
            boolean endFlag = (i == bytes.length - 1);
            builder.append(byteToBinaryString(bytes[i], endFlag, actualLen));
        }
        //2)根据赫夫曼编码串（StringBuilder）以及赫夫曼编码表(Map<String, Byte>)得到原始字符串(byte[])
//        System.out.println(builder.toString());
        //将原来的赫夫曼map反转
        HashMap<String, Byte> reverseMap = new HashMap<>();//存储反转后的赫夫曼map
        for(Map.Entry<Byte, String> entries : map.entrySet()){
            reverseMap.put(entries.getValue(), entries.getKey());
        }
        //根据反转后的赫夫曼map解码出原始字符串
        ArrayList<Byte> byteArrayList = new ArrayList<>();//存储原始字符串的List
        //定义第二个指针
        for (int i = 0; i < builder.length(); ) {
            int index = 1;//每次取[i, index)之间的String，并在reverseMap中查找
            while(true){
                String substring = builder.substring(i, i + index);//每次取[i, index)之间的String
                Byte aByte = reverseMap.get(substring);//并在reverseMap中查找
                if(aByte != null){
                    byteArrayList.add(aByte);//如果不为空，将解码出的字符暂时存放入List
                    break;
                }
                index++;
            }
            i += index;
        }
        //解码完成后，所有被解码出的字符都存在List中
        //此时，将List中的数据转入byte[]数组中
        byte[] sourceBytes = new byte[byteArrayList.size()];//存储原始字符
        int sourceBytesIndex = 0;//指向当前存储位置
        for(Byte item : byteArrayList){
            sourceBytes[sourceBytesIndex++] = item;
        }
        return sourceBytes;
    }
    /**
     *
     * @param b 待转换的字节数据
     * @param endFlag 代表是否为最后一个字节
     * @param actualLen 记录最后一个字节的实际位数
     * @return 转换后的二进制String
     */
    public static String byteToBinaryString(byte b, boolean endFlag, int actualLen){
        //先用int存储b，方便调用Integer中的toBinaryString()方法
        int temp = b;
        //先 |256, 以保存高位的0
        temp |= 256;//按位 |256  0000 0000 0001 | 0001 0000 0000 = 0001 0000 0001, 此时再取后八位，就保存了高位的七个0
        String string = Integer.toBinaryString(temp);//此方法返回的是temp对应的二进制形式的补码
        if(!endFlag){//如果不是最后一位
            string = string.substring(string.length() - 8);//取出后8位
        }else{//如果是最后一位
            //如果是最后一位，取出后actualLen位
            string = string.substring(string.length() - actualLen);//取出后actualLen位
        }
        return string;
    }

    //前序遍历
    public static void preOrder(Node root){
        if(root != null){
            root.preOrder();
        }else{
            System.out.println("赫夫曼树为空，无法遍历");
        }
    }
}

//节点
class Node implements Comparable<Node>{//为使用Collections中的sort方法
    Byte data;//字符
    int weight;//出现次数
    Node leftNode;//左子节点
    Node rightNode;//右子节点

    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    //前序遍历
    public void preOrder(){
        System.out.println(this);
        if(this.leftNode != null){
            this.leftNode.preOrder();
        }
        if(this.rightNode != null){
            this.rightNode.preOrder();
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "c=" + data +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        //按weight大小升序排序
        return this.weight - o.weight;
    }
}
