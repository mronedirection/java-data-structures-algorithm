package com.renhao.hashtable;

import java.util.Scanner;

/**
 * @author RenHao
 * @create 2022-10-13 20:01
 */
public class HashTabDemo {

    public static void main(String[] args) {

        //创建哈希表(包含7个链表)
        HashTable hashTable = new HashTable(7);

        //写一个简单的菜单
        String key = "";
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("add : 添加雇员");
            System.out.println("list: 显示雇员");
            System.out.println("find: 查找雇员");
            System.out.println("exit: 退出系统");

            System.out.print("请输入选项：");
            key = scanner.next();
            switch (key){
                case "add":
                    System.out.print("输入id:");
                    int id = scanner.nextInt();
                    System.out.print("输入名字：");
                    String name = scanner.next();
                    //创建 雇员
                    Employee emp = new Employee(id, name);
                    hashTable.add(emp);
                    break;
                case "list":
                    hashTable.list();
                    break;
                case "find":
                    System.out.print("请输入要查找的id:");
                    id = scanner.nextInt();
                    hashTable.findEmpById(id);
                    break;
                case "exit":
                    scanner.close();
                    System.out.println("程序退出");
                    System.exit(0);
            }
        }
    }

}

//创建HashTable管理多条链表
class HashTable{
    private EmployeeLinkedList[] empLinkedLists;//表示HashTable
    private int size;//HashTable中包含几条链表

    //构造器
    public HashTable(int size) {
        this.size = size;
        empLinkedLists = new EmployeeLinkedList[size];//数组初始化
        for (int i = 0; i < size; i++) {
            empLinkedLists[i] = new EmployeeLinkedList();//对数组中每个元素进行初始化，给每个元素一个引用地址，否则直接调用会出现空指针
        }
    }

    //添加雇员
    public void add(Employee emp){
        //根据员工id，得到该员工应该添加到哪条链表
        int empLinkedListNo = hashFun(emp.id);
        empLinkedLists[empLinkedListNo].add(emp);//添加进链表中
    }

    //遍历HashTable，即遍历所有的链表
    public void list(){
        for (int i = 0; i < size; i++) {
            empLinkedLists[i].list(i+1);//list出第i+1条链表包含的全部雇员
        }
    }

    //根据输入的id，查找雇员
    public void findEmpById(int id){
        //根据id判断该雇员位于哪条链表
        int empLinkedListNo = hashFun(id);
        //输出该雇员信息
        Employee empTemp = empLinkedLists[empLinkedListNo].findEmpById(id);
        if(empTemp == null){
            System.out.println("在哈希表中，未找到此雇员");
        }else{
            System.out.println("在第" + empLinkedListNo + "条链表中找到此雇员，雇员信息为：" + empTemp);
        }
    }

    //编写散列函数，使用一个简单的取模法
    private int hashFun(int id){
        return id % size;
    }

}

//创建EmployeeLinkedList，表示一个单链表
class EmployeeLinkedList{

    private Employee head;//头节点，指向第一个Employee，默认为null

    //添加雇员到链表
    public void add(Employee emp){
        if(head == null){//链表为空
            head = emp;
            return;
        }
        Employee empTemp = head;//head节点不能动
        while(empTemp.next != null){
            empTemp = empTemp.next;//后移
        }
        empTemp.next = emp;//当前节点指向emp
    }

    //遍历HashTable中第no个链表的雇员信息
    public void list(int no){
        if(head == null){
            System.out.printf("第%d条链表为空\n", no);
        }else{
            Employee empTemp = head;//head节点不动
            System.out.printf("第%d条链表包含的雇员为:\n", no);
            while(empTemp != null){
                System.out.println(empTemp);
                empTemp = empTemp.next;
            }
        }
    }

    //根据id查找雇员，如果找到，返回Employee，未找到返回null
    public Employee findEmpById(int id) {
        if(head == null){
            return null;
        }else{
            Employee empTemp = head;
            while(empTemp != null){
                if(empTemp.id == id){
                    return empTemp;
                }
                empTemp = empTemp.next;//后移
            }
        }
        return null;
    }
}

//表示一个雇员（节点）
class Employee{

    public int id;//序号
    private String name;//姓名
    public Employee next;//指向下一个节点

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}


