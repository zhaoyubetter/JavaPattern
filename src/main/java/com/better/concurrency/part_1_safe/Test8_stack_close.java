package com.better.concurrency.part_1_safe;

import com.better.Utils;
import com.better.concurrency.anno.GuardedBy;
import com.better.concurrency.anno.ThreadSafe;

import java.lang.ref.WeakReference;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 栈封闭例子，如何解决呢？加入同步代码块解决
 */
public class Test8_stack_close {


    private synchronized static void check(final Map<String, Integer> cMap, final String key, final int exceptValue) {
        int cValue = cMap.get(key);
        if (cValue != exceptValue) {
            Utils.println("同步问题");
            throw new ConcurrentModificationException("except: " + exceptValue + ", but is: " + cValue);
//           System.exit(0);
        }
    }

    public static void main(String... args) throws Exception {

        final Map<String, Integer> map = new HashMap<>();
        map.put("key1", 1);
        map.put("key2", 2);


        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    final int next = new Random().nextInt(100);
                    try {
                        Thread.sleep(next);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Utils.println(String.format("Thread name:%s, value:%s", Thread.currentThread(), next));
                    synchronized (map) {
                        map.put("key1", next);

                        try {
                            Thread.sleep(next);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // 使用
                        check(map, "key1", next);
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    final int next = new Random().nextInt(100);
                    try {
                        Thread.sleep(next);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Utils.println(String.format("Thread name:%s, value:%s", Thread.currentThread(), next));
                    synchronized (map) {
                        map.put("key1", next);

                        try {
                            Thread.sleep(next);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // 使用
                        check(map, "key1", next);
                    }
                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    final int next = new Random().nextInt(100);
                    try {
                        Thread.sleep(next);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Utils.println(String.format("Thread name:%s, value:%s", Thread.currentThread(), next));

                    synchronized (map) {
                        map.put("key1", next);

                        try {
                            Thread.sleep(next);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // 使用
                        check(map, "key1", next);
                    }
                }
            }
        });


        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(10000);
    }
}
