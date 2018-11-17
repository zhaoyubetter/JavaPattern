package com.better.io;

import java.util.Scanner;

/**
 * 进行字符串分割
 * from: https://blog.csdn.net/uucai/article/details/10050129
 */
public class ScannerTest {
    public static void main(String... aa) {
//        test1();
//        test2();
//        test3();
        test4_re();
    }

    private static void test1() {
        // 输入 a b c   aa   测试，将分别打印出来。 说明Scanner默认使用的分隔符是空格、回车和tab
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            System.out.println("hasNext()判断为true,我还有下一个：");
            System.out.println("下一个输出是：" + sc.next());
        }
    }

    private static void test2() {
        // 输入 a b c  测
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("a");       // 使用a作为分割
        while (sc.hasNext()) {
            System.out.println("hasNext()判断为true,我还有下一个：");
            System.out.println("下一个输出是：" + sc.next());   // block, input a then output b c
        }
    }

    private static void test3() {
        Scanner sc = new Scanner("a b c \nd\te");
        sc.useDelimiter("a");  // use a
        while (sc.hasNext()) {
            System.out.println("hasNext()判断为true,我还有下一个：");
            System.out.println("下一个输出是：" + sc.next());
        }
    }

    private static void test4_re() {
        Scanner sc = new Scanner("1 fish 2 fish red fish blue fish");
        sc.useDelimiter("\\s*fish\\s*");  // use a
        while (sc.hasNext()) {
            System.out.println("hasNext()判断为true,我还有下一个：");
            System.out.println("下一个输出是：" + sc.next());      // 1,2,red,blue
        }
    }

}
