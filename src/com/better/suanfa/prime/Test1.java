package com.better.suanfa.prime;

/**
 * Created by zhaoyu on 2018/2/25.
 */
public class Test1 {
    public static void main(String... ar) {
        comPrime1_2(10);
    }

    private static void comPrime1_2(int N) {
        System.out.println("小于" + N + "的质数有：");
        for (int i = N; i >= 2; i--) {
            int j = 2;
            for (; j <= (int) Math.sqrt(i); j++) {
                if (i % j == 0)
                    break;
            }
            if (j > (int) Math.sqrt(i))
                System.out.println(i);
        }
    }
}
