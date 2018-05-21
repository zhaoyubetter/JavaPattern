package com.better.concurrency.part_1_safe;

import com.better.Utils;
import com.better.concurrency.anno.NotThreadSafe;
import com.better.concurrency.anno.ThreadSafe;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 虽然每个方法，都是原子方法
 * 但，复合操作时，还是会出现竞态条件
 */
public class Test4_vector_unsafe {

    private static int value = 0;

    public static void main(String... args) throws Exception {

        UnSafeVector vector = new UnSafeVector();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(new Random().nextInt(8));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    final int next = new Random().nextInt(100);
                    Utils.println(String.format("Thread name:%s, value:%s", Thread.currentThread(), next));
                    vector.putValue(next);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    final int next = new Random().nextInt(100);
                    Utils.println(String.format("Thread name:%s, value:%s", Thread.currentThread(), next));
                    vector.putValue(next);
                    try {
                        Thread.sleep(new Random().nextInt(10));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(new Random().nextInt(5));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    final int next = new Random().nextInt(100);
                    Utils.println(String.format("Thread name:%s, value:%s", Thread.currentThread(), next));
                    vector.putValue(next);
                }
            }
        });


        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(10000);
    }

    @NotThreadSafe
    private static class UnSafeVector {
        private Vector<Integer> vector = new Vector<>();
        final Set<Integer> set = Collections.synchronizedSet(new HashSet<>(1000));

        /**
         * // 如何解决，需要额外枷锁
         *
         * @param num
         */
        public void putValue(int num) {
            boolean exist = vector.contains(num);
            // 在2个同步方法之间加点延迟
            try {
                Thread.sleep(new Random().nextInt(9));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!exist) {
                vector.add(num);
                check(set, num);
            }
        }

        private void check(Set<Integer> set, int num) {
            if (set.contains(num)) {
                Utils.println("有重复数据：" + num);
                System.exit(0);
            } else {
                set.add(num);
            }
        }
    }
}
