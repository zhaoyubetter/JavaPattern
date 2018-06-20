package com.better.concurrency.part_6_lock;

import com.better.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 协作对象之间的死锁问题，不容易发现的死锁
 * 也就是2个线程安全不同的顺序来获取锁
 */
public class Test5_union_deadlock {
    public static void main(String... a) throws InterruptedException {
        final int taxi_size = 10;
        final Dispatcher dispatcher = new Dispatcher();
        final Taxi[] taxis = new Taxi[taxi_size];
        for (int i = 0; i < taxi_size; i++) {
            taxis[i] = new Taxi(dispatcher);
            dispatcher.notifyAvailable(taxis[i]);
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    taxis[new Random().nextInt(taxi_size)].getLocation();   // 获取锁
                    taxis[new Random().nextInt(taxi_size)].setLocation(new Point(2,2));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        dispatcher.getImage();   // 锁嵌套
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    static class Taxi {
        private Point location, dest;
        private final Dispatcher dispatcher;

        public Taxi(Dispatcher dispatcher) {
            this.dispatcher = dispatcher;
            dest = new Point(2, 2);
        }

        public synchronized Point getLocation() throws InterruptedException {
            Thread.sleep(new Random().nextInt(100));
            Utils.println(Thread.currentThread().getName() + "获取地址。。。");
            return location;
        }

        public synchronized void setLocation(Point location) {
            this.location = location;
            if (location.equals(dest)) {
                dispatcher.notifyAvailable(this);
            }
        }
    }

    static class Dispatcher {
        private final Set<Taxi> taxis;
        private final Set<Taxi> availableTaxis;

        public Dispatcher() {
            taxis = new HashSet<>();
            availableTaxis = new HashSet<>();
        }

        public synchronized void notifyAvailable(Taxi taxi) {
            availableTaxis.add(taxi);
        }

        public synchronized void getImage() throws InterruptedException {
            for (Taxi s : availableTaxis) {
                Thread.sleep(200);
                Utils.println(Thread.currentThread().getName() + "获取图片： " + s.getLocation());
            }
        }
    }

}
