package com.better.concurrency.part_2_base;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 实现2方之间的数据交换，每方在完成一定的事务后想与对方交换数据，第一个先拿出数据的人将一直等待第第二方拿着数据到来时，才能彼此交换数据。
 */
public class Test13_Exchanger_1 {
    public static void main(String... args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final Exchanger<String> exchanger = new Exchanger();
        service.execute(new Runnable() {
            public void run() {
                try {
                    Thread.sleep((long) (Math.random() * 10000));
                    String data1 = "abc";
                    System.out.println("thread: " + Thread.currentThread().getName() + "正在把数据" + data1 + "换出去");
                    String data2 =  exchanger.exchange(data1);
                    System.out.println("thread: " + Thread.currentThread().getName() + "换回的数据为" + data2);

                } catch (Exception e) {
                }
            }
        });

        service.execute(new Runnable() {
            public void run() {
                try {
                    Thread.sleep((long) (Math.random() * 10000));
                    String data1 = "def";
                    System.out.println("thread: " + Thread.currentThread().getName() + "正在把数据" + data1 + "换出去");
                    String data2 =  exchanger.exchange(data1);
                    System.out.println("thread: " + Thread.currentThread().getName() + "换回的数据为" + data2);
                } catch (Exception e) {
                }
            }

        });
    }

}
