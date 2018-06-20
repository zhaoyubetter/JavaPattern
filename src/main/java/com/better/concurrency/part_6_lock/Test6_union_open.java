package com.better.concurrency.part_6_lock;

import com.better.Utils;

import java.awt.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 公平调用，避免死锁
 */
public class Test6_union_open {
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
                    taxis[new Random().nextInt(taxi_size)].getLocation();
                    taxis[new Random().nextInt(taxi_size)].setLocation(new Point(2, 2));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        dispatcher.getImage();
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
            return location;
        }

        // 锁仅用与保护涉及共享状态的操作
        public void setLocation(Point location) {
            Utils.println(Thread.currentThread().getName() + "设置地址 " + location);
            boolean isEquls;

            // 缩小保护区域
            synchronized (this) {
                this.location = location;
                isEquls = location.equals(dest);
            }

            if (isEquls) {
                Utils.println(Thread.currentThread().getName() + "通知 " + location);
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

        public void getImage() throws InterruptedException {
            Set<Taxi> copy;
            synchronized (this) {
                copy = new HashSet<>(availableTaxis);
            }

            for (Taxi s : copy) {
                Thread.sleep(200);
                Utils.println(Thread.currentThread().getName() + "获取图片： " + s.getLocation());
            }
        }
    }

}
