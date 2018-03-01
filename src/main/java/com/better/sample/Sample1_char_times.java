package com.better.sample;

/**
 * 统计字符串中各个字符出现的个数
 * Created by zhaoyu on 2017/2/8.
 */
public class Sample1_char_times {
    public static void main(String[] args) {
        String str = "Welcome to beijing!";
        char[] charArray = str.toCharArray();
        int[] timesArray = new int[256];

        for (char c : charArray) {
            timesArray[c] = timesArray[c] + 1;
        }

        for(int i=0; i<timesArray.length; i++) {
            if (timesArray[i] > 0) {
                System.out.println(((char) i) + ": " + timesArray[i]);
            }
        }
    }
}
