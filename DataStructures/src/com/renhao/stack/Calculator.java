package com.renhao.stack;

/**
 * 使用栈来模拟计算器
 * 1.分别构造存放数字和字符的栈
 * 2.遍历输入的表达式，如果是数字则入数字栈，如果是字符则入字符栈
 * 3.入栈过程：
 *      从字符串中取出一个字符，判断是操作符还是数字；
 *      如果是数字：
 *          由于数字可能有多位，因此需要判断下一个字符是否为数字，如果为数字：
 *              则将两个字符连接起来，继续判断其后是否为数字，直到不为数字，将数字入栈；
 *          如果不为数字：
 *              则直接入数字栈；
 *     如果是字符：
 *          判断操作符栈是否为空：
 *              为空：直接入操作符栈
 *              不为空：将此操作符的优先级与操作符栈顶的操作符进行比较：
 *                  如果优先级高：则直接入操作符栈
 *                  如果优先级低：则从操作符栈取出一个操作符，从数字栈取出两个数字，进行运算，将运算结果存入数字栈中
 *    如果扫描到表达式的最后，则停止入栈
 * 4.表达式扫描完成后，出栈进行运算：
 *      先从操作符栈中取出一个操作符，从数字栈中取出两个数，进行运算，运算结果入数字栈
 *      重复此操作，直至运算符栈中没有运算符，此时取出数字栈中应只剩一个数字，取出此数字，即为运算结果
 *
 * @author RenHao
 * @create 2022-09-15 17:28
 */

public class Calculator {

    public static void main(String[] args) {

        //输入表达式
        String expression = new String("100+5*6-60/2-1");
        //造存放表达式的数字栈和字符栈
        ArrayStack2 numStack = new ArrayStack2(10);
        ArrayStack2 opeStack = new ArrayStack2(10);
        int index = 0;//遍历表达式的指针
        int operator = 0;//存储待运算的操作符
        int num1 = 0;//存储待运算的第一个数字
        int num2 = 0;//存储待运算的第二个数字
        int res = 0;//存储运算结果
        String temp = "";//拼接多位数字
        //遍历存入数字和字符
        while(true){
            char c = expression.charAt(index++);//取出index指的当前字符，并自增
            //判断取出的字符是否是操作符
            if(opeStack.isOper(c)){//是操作符
                //判断操作符栈是否为空
                if(opeStack.isEmpty()){//操作符栈为空
                    opeStack.push(c);//直接入栈
                }else {//操作符不为空
                    //判断当前字符和操作符栈顶字符的优先级
                    if(opeStack.priority(c) <= opeStack.priority(opeStack.peek())){//当前字符的优先级不高于栈顶字符的优先级
                        //取出操作符栈的栈顶字符，取出数字栈中的两个数字，进行运算，结果存入数字栈
                        //取数据
                        operator = opeStack.pop();
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        res = opeStack.cal(num1, num2, operator);//运算
                        numStack.push(res);//结果入数字栈
                        opeStack.push(c);//此时操作符栈顶字符的优先级肯定低于待入的字符，因此字符直接入操作符栈
                    }else{//当前字符的优先级高于栈顶字符的优先级
                        opeStack.push(c);//直接入操作符栈
                    }
                }
            }else{//如果不是操作符,即为数字，要判断后面一位是否是数字，因为数字可能不止一位
                temp += c;
                if(index != expression.length()){//如果index没有到达expression的最后一位
                    while(true){
                        //取出下一位字符，只看，index的值不能动
                        c = expression.charAt(index);
                        //判断是否为操作符
                        if(opeStack.isOper(c)) {//如果是
                            break;//退出循环
                        }
                        temp += c;//如果不是字符，将其与上一个拼接起来，继续判断
                        index++;
                    }
                }
                numStack.push(Integer.parseInt(temp));//直接入数字栈,字符‘1’转为十进制存储在底层表现为(int)49
                temp = "";//temp置空
            }
            if(index == expression.length()){
                break;//遍历结束，终止循环
            }
        }
        //入栈完成后，开始顺序出栈，计算最终结果
        while(true){
            //首先判断操作符栈是否为空
            if(opeStack.isEmpty()){//为空
                break;//退出循环
            }
            //此处用栈处理会有bug，应该正序计算
            //如果不为空
            //取出操作符栈的栈顶字符，取出数字栈中的两个数字，进行运算，结果存入数字栈
            //取数据
            operator = opeStack.pop();
            num1 = numStack.pop();
            num2 = numStack.pop();
            res = opeStack.cal(num1, num2, operator);//运算
            numStack.push(res);//结果入数字栈
        }
        //运算完成，取出数字栈中的数据，即为最终运算结果
        res = numStack.pop();
        System.out.println(expression + '=' + res);
    }

}

//先创建一个栈，用于存放数字和运算符
class ArrayStack2 {
    //属性：最大容量，数组，指针
    private int maxSize;//栈的容量
    private int[] stack;//存放数据
    private int top = -1;//栈顶指针

    //构造器
    public ArrayStack2(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[this.maxSize];
    }

    //方法

    //查看栈顶的数据，不取出
    public int peek(){
        return stack[top];//取出栈顶的数据，栈顶指针并不动
    }

    //判断栈是否为空
    public boolean isEmpty(){
        return top == -1;//指针没有动，代表栈为空
    }
    //判断栈是否满
    public boolean isFull(){
        return top == maxSize - 1;//指针指向数组最顶端，代表栈满
    }
    //入栈
    public void push(int value){
        //先判断栈是否满
        if(isFull()){
            System.out.println("栈已满，无法添加");
            return;
        }
        //不满，则将value添加进stack[],同时移动top
        stack[++top] = value;
    }
    //出栈
    public int pop(){
        //先判断栈是否空
        if(isEmpty()){
            throw new RuntimeException("栈空，无法出栈");
        }
        //不空，则取出栈顶的数据, 同时移动top
        return stack[top--];
    }

    //返回运算符的优先级，优先级使用数字表示
    //数字越大，则优先级越高
    public int priority(int oper){
        if(oper == '*' || oper == '/'){
            return 1;
        } else if (oper == '+' || oper == '-') {
            return 0;
        }
        //如果不是上述四个运算符，则返回-1
        return -1;
    }

    //判断是不是一个运算符
    public boolean isOper(char val){//满足任意一个，返回true
        return val == '+' || val == '-' || val == '*' || val == '/';
    }

    //计算方法
    public int cal(int num1, int num2, int oper){
        //switch-case比较的实际是ASCII值
        switch(oper){
            case '+':
                return num1 + num2;
            case '-':
                return num2 - num1;//后出栈的数减去前一个出栈的数
            case '*':
                return num1 * num2;
            case '/':
                return num2 / num1;
        }
        return 0;//运算符不匹配
    }
}

