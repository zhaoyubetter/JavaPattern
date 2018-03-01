package com.better.sample;

/**
 * Created by zhaoyu1 on 2016/11/7.
 */
public class MyClass3 {
    public static void main(String[] args) {
        printOut(8909);
    }

    private static void printOut(int n) {
        if(n >= 10) {
            printOut( n / 10);
        }
        printDigit(n % 10);
    }

    private static void printDigit(int num) {
        System.out.println(num);
    }
}
