package com.renhao.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**逆波兰计算器
 * 1.输入一个中缀表达式
 * 2.将中缀表达式转为后缀表达式
 * 3.使用栈计算后缀表达式结果
 * ps:1)支持小括号和多位数整数
 *
 * @author RenHao
 * @create 2022-09-16 17:38
 */
public class PolandNotation {


    public static void main(String[] args) {
        //测试calculate(List<String> ls)
        //ArrayList [1,+,(,(,2,+,3,),*,4,),-,5]  => ArrayList [1,2,3,+,4,*,+,5,–] -> 16
//        List<String> suffixExpressionList = toExpressionList("1,2,3,+,4,*,+,5,-");
//        System.out.printf("SuffixExpression[1,2,3,+,4,*,+,5,–] = %d", calculate(suffixExpressionList));
        //将中缀表达式str -> 中缀表达式List
        List<String> infixExpressionList = toExpressionList("1,+,(,(,2,+,3,),*,4,),-,5");
//        System.out.println(infixExpressionList);
        //中缀表达式List -> 后缀表达式List
        //ArrayList [1,+,(,(,2,+,3,),*,4,),-,5]  => ArrayList [1,2,3,+,4,*,+,5,–] -> 16
        List<String> suffixExpressionList = parseSuffixExpressionList(infixExpressionList);
//        System.out.println(suffixExpressionList);
        System.out.printf("SuffixExpression[1,2,3,+,4,*,+,5,–] = %d", calculate(suffixExpressionList));
    }

//    //将输入的中缀表达式String -> 中缀表达式List
//    //String [1,+,(,(,2,+,3,),*,4,),-,5] -> ArrayList [1,+,(,(,2,+,3,),*,4,),-,5]
//    public static List<String> toInfixExpressionList(String infixExpressionStr){
//        return null;
//    }

    //将中缀表达式List -> 后缀表达式List
    //ArrayList [1,+,(,(,2,+,3,),*,4,),-,5]  -> ArrayList [1,2,3,+,4,*,+,5,-]
    /*
    1.初始化两个栈：运算符栈s1(只存储运算符)，储存中间结果的栈s2(存储运算符和数字)
    2.从左至右遍历中缀表达式，直至最后一个
    3.遇到数字时，将其压入s2
    4.遇到运算符时，判断s1是否为空：
        为空，则直接入s1栈；
        不为空，判断此时s1栈顶是否为括号：
            是括号，则直接入栈；
            如果不是括号，则比较当前运算符与s1栈顶运算符的优先级：
                如果高于栈顶优先级，则入s1栈；
                否则，弹出s1栈顶运算符，入s2栈，回第四步
    5.遇到括号时：
        如果是"(",则直接入s1栈；
        如果是")",则依次弹出s1栈顶的运算符，并压入s2栈，直到遇到左括号为止，此时将这一对括号丢弃
    6.重复步骤2-5，直到遍历结束
    7.遍历结束后，将s1中剩余的运算符依次弹出并压入s2
    8.依次弹出s2中的元素，弹出字符串的逆序即为中缀表达式对应的后缀表达式
     */
    public static List<String> parseSuffixExpressionList(List<String> ls) {
        Stack<String> s1 = new Stack<>();//存储运算符
        //说明：因为s2栈，在整个转换过程中，没有pop操作，而且后面我们还需要逆序输出
        //因此比较麻烦，这里我们可以不用Stack，直接使用List
        Stack<String> s2 = new Stack<>();//存储中间结果
        ArrayList<String> suffixExpressionList = new ArrayList<>();//存储最终的后缀表达式List
        for(String item : ls){
            if(item.matches("\\d+")){//匹配多位数
                s2.push(item);//遇到数字时，将其压入s2
            } else if ("(".equals(item)) {//如果是"(",则直接入s1栈；
                s1.push(item);

            } else if (")".equals(item)) {// 如果是")"
                while(!"(".equals(s1.peek())){//直到遇到左括号为止
                    s2.push(s1.pop());//依次弹出s1栈顶的运算符，并压入s2栈
                }
                s1.pop();//丢弃s1栈中的"("
            }else{
                while(true){
                    if(s1.isEmpty()){//为空，则直接入s1栈；
                        s1.push(item);
                        break;//判断下一个item
                    }else{//不为空，判断此时s1栈顶是否为括号：
                        if("(".equals(s1.peek()) || ")".equals(s1.peek())){//是括号，则直接入栈；
                            s1.push(item);
                            break;
                        }else{
                            if(priority(item.charAt(0)) > priority(s1.peek().charAt(0))){//如果高于栈顶优先级，则入s1栈；
                                s1.push(item);
                                break;
                            }else{
                                s2.push(s1.pop());//循环比较s1中下一个
                            }
                        }
                    }
                }
            }
        }
        //遍历结束后，将s1中剩余的运算符依次弹出并压入s2
        while(!s1.isEmpty()){
            s2.push(s1.pop());
        }
        for(String item : s2){//此时是从栈底开始遍历，刚好实现逆序输出
            suffixExpressionList.add(item);
        }
        return suffixExpressionList;
    }

    //完成对后缀表达式（逆波兰表达式）的计算
    //ArrayList [1,+,(,(,2,+,3,),*,4,),-,5]  =》 ArrayList [1,2,3,+,4,*,+,5,–]
    /*
    1)从左至右扫描，将数字压入栈
    2）遇到运算符，弹出数字栈的前两个数字，进行相应的操作，将运算结果入数字栈
    3）重复1）-2），直至扫描完成
    4）此时数字栈中应只剩下一个元素，为最后运算结果，将运算结果出栈即可
     */
    public static int calculate(List<String> ls){
        Stack<String> integerStack = new Stack<>();//存储后缀表达式中的数字以及暂时结果
        //存储pop中的数据
        int num1 = 0;
        int num2 = 0;
        int res = 0;//存储运算结果
        for(String item : ls){
            if(item.matches("\\d+")){//匹配多位数字，满足则压入数字栈
                integerStack.push(item);
            }else{//如果item不是数字
                //pop出前两个数据，进行运算
                num1 = Integer.parseInt(integerStack.pop());
                num2 = Integer.parseInt(integerStack.pop());
                switch(item){
                    case "+":
                        res = num2 + num1;
                        break;
                    case "-":
                        res = num2 - num1;
                        break;
                    case "*":
                        res = num2 * num1;
                        break;
                    case "/":
                        res = num2 / num1;
                        break;
                }
                //将运算结果存入数字栈
                integerStack.push(String.valueOf(res));
            }
//            if("+".equals(item)){
//                //pop出前两个数据，进行运算
//                num1 = Integer.parseInt(integerStack.pop());
//                num2 = Integer.parseInt(integerStack.pop());
//                res = num1 + num2;
//                //将运算结果存入数字栈
//                integerStack.push(String.valueOf(res)));
//            }
        }
        //遍历完成，此时数字栈中应只剩下一个元素，为最后运算结果，将运算结果出栈即可
        return Integer.parseInt(integerStack.pop());
    }

    //将中缀或者后缀表达式String -> 中缀或者后缀表达式List
    private static List<String> toExpressionList(String expressionStr){
        String[] split = expressionStr.split(",");//将String按","分开，返回String[]
        List<String> expressionList = new ArrayList<>();
        for(String item : split){
            expressionList.add(item);
        }
        return expressionList;
    }

    //判断四种运算符的优先级
    private static int priority(int oper){
        if(oper == '*' || oper == '/'){
            return 1;
        } else if (oper == '+' || oper == '-') {
            return 0;
        }
        //如果不是上述四个运算符，则返回-1
        return -1;
    }
}
