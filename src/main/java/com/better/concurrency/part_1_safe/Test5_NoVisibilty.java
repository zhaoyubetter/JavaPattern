package com.better.concurrency.part_1_safe;

import com.better.Utils;
import com.better.concurrency.anno.NotThreadSafe;

/**
 * 可见性,
 * 一个线程写，一个线程读，没有使用同步策略
 * 很有可能获取到的是 失效数据 的值
 */
@NotThreadSafe
public class Test5_NoVisibilty {

    private static boolean ready;
    private static int number;

    private static class ReadyThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            Utils.println(number);
        }
    }

    public static void main(String... args) throws Exception {
        new ReadyThread().start();
        number = 42;
        ready = true;
    }
}
