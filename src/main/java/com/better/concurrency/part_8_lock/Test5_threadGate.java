package com.better.concurrency.part_8_lock;

import com.better.Utils;

/**
 * 闭锁打开后，无法关闭，这里是阀门类（通过使用条件等待）
 */
public class Test5_threadGate {


    private static class TestThread extends Thread {
        public void run() {
            System.out.println(this.getName() + "子线程开始");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this.getName() + "子线程结束");
        }
    }

    private static class TestThread2 extends Thread {

        private ThreadGate gate;
        public TestThread2(ThreadGate gate) {
            this.gate = gate;
        }

        public void run() {
            gate.close();
            System.out.println(this.getName() + "子线程开始");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this.getName() + "子线程结束");
            gate.open();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        // 没有等待子线程执行完成，主线程就继续执行了；
        /*
        Test5_threadGate a = new Test5_threadGate();
        Thread thread = new TestThread();
        thread.start();
        Utils.println("主线程执行...");
        */
        // 2.

        Test5_threadGate a = new Test5_threadGate();
        ThreadGate gate = new ThreadGate();
        Thread thread = new TestThread2(gate);
        thread.start();
        gate.await();
        Utils.println("主线程执行...");
    }


    private static class ThreadGate {
        // 条件：open-since(n) (isOpen || generation > n)
        private boolean isOpen;
        private int generation;

        public synchronized void close() {
            isOpen = false;
        }

        public synchronized void open() {
            ++generation;
            isOpen = true;
            notifyAll();
        }

        public synchronized void await() throws InterruptedException {
            int arrivalGeneration = generation;
            while (!isOpen && arrivalGeneration == generation) {
                wait();
            }
        }
    }

}
