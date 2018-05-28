package com.better.concurrency.part_2_base;

import com.better.Utils;

import java.util.*;

/**
 * 隐藏迭代器
 */
public class Test3_hidden_iterator {

    static synchronized void add(Set set, int i) {
        set.add(i);
    }

    static synchronized void delete(Set set) {
        if (set.size() > 0) {
            set.remove(0);
        } else {
            System.exit(0);
        }
    }

    static void addTenNumber(final Set set) {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            add(set, random.nextInt());
        }
        Utils.println(set.toArray());       // 注意这里的问题，
    }

    public static void main(String... args) throws Exception {

        final Set<Integer> vector = new HashSet<>();

        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        final int next = new Random().nextInt(300);
                        Utils.println(String.format("Thread name:%s, value:%s", Thread.currentThread(), next));
                        addTenNumber(vector);
                        try {
                            Thread.sleep(next);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        delete(vector);
                    }
                }
            }).start();
        }

        Thread.sleep(10000);
    }
}
