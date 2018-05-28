package com.better.concurrency.part_2_base;

import com.better.Utils;

import java.util.*;

public class Test2_concurrent_modify_exception {

    public static void main(String... args) throws Exception {


        final List<Integer> vector = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            vector.add(i);
        }

        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        final int next = new Random().nextInt(10);
                        Utils.println(String.format("Thread name:%s, value:%s", Thread.currentThread(),  getLast(vector)));
                        try {
                            Thread.sleep(next);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        deleteFirst(vector);
                    }
                }
            }).start();
        }

        Thread.sleep(10000);
    }

    static  Object getLast(List vector) {
        ListIterator listIterator = vector.listIterator();
        while (listIterator.hasNext()) {
            Utils.print(listIterator.next());
        }
        Utils.println("==");
        return null;
    }

    static  void deleteFirst(List vector) {
        ListIterator listIterator = vector.listIterator();
        if (listIterator.hasNext()) {
            listIterator.next();        // 必要的next
            listIterator.remove();
            Utils.println(vector.toString());
        } else {
            System.exit(0);
        }
    }
}
