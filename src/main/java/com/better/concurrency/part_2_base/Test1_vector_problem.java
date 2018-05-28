package com.better.concurrency.part_2_base;

import com.better.Utils;

import java.util.*;

/**
 * 交替使用时 getlast、deletelast，发生问题
 */
public class Test1_vector_problem {

    public static void main(String... args) throws Exception {



        final Vector<Integer> vector = new Vector<>();
        for (int i = 0; i < 100; i++) {
            vector.add(i);
        }

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        final int next = new Random().nextInt(100);
                        Utils.println(String.format("Thread name:%s, value:%s", Thread.currentThread(), next));
                        getLast(vector);
                        try {
                            Thread.sleep(next);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        deleteLast(vector);
                    }
                }
            }).start();
        }

        Thread.sleep(10000);
    }

    static  Object getLast(Vector vector) {
        int lastIndex = vector.size() - 1;
        if (lastIndex > 0) {
            return vector.get(lastIndex);
        }
        System.exit(0);
        return null;
    }

    static  void deleteLast(Vector vector) {
        int lastIndex = vector.size() - 1;
        if (lastIndex > 0) {
            vector.remove(lastIndex);
        }
    }

}
