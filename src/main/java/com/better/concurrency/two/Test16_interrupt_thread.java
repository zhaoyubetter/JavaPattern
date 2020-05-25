package com.better.concurrency.two;

import com.better.Utils;
import org.junit.Test;

/**
 * 优雅的终止线程
 * 使用两阶段终止模式终止线程
 */
public class Test16_interrupt_thread {

    @Test
    public void test1() throws InterruptedException {
        final Test2 test2 = new Test2();
        test2.start();
        Thread.sleep(5000);
        test2.stop();
    }

    @Test
    public void test2() throws InterruptedException {
        final Test3 test3 = new Test3();
        test3.start();
        Thread.sleep(7000);
        test3.stop();
    }

    /**
     * 此示例能解决当前问题；但是不建议使用；
     * 原因在于：
     * 1.在实际项目中，run方法可能调用第三方库的方法，而我们无法保证第三方库争取处理线程的中断异常，
     * 假设其在捕获到 Thread.sleep() 方法抛出的中断异常后，但没有设置线程中断状态，那么就会导诊线程不能正常终止；
     * 所以我们还是推荐使用 Test3
     */
    static class Test2 {
        boolean started = false;
        Thread workThread;

        synchronized void start() {
            if (started) {
                return;
            }
            started = true;
            workThread = new Thread(() -> {
                // 2.通过标记位判断线程中断状态来停止线程
                while (!Thread.currentThread().isInterrupted()) {
                    Utils.println("do work...");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        // 调用 intercept() 后，线程会恢复到 runnable 状态,
                        // 并且 JVM 的异常处理会清除线程中断状态
                        // 所以需要加下以下代码，重新设置中断状态
                        Thread.currentThread().interrupt();
                    }
                }
                //执行到此处说明线程马上终止
                started = false;
            });
            workThread.start();
        }

        // 1.发送线程终止信号
        synchronized void stop() {
            if(workThread != null) {
                workThread.interrupt();
            }
        }
    }

    /**
     * 推荐: 终止标志位  与 线程的中断状态 配合使用
     */
    static class Test3 {
        //线程终止标志位，这里需要 volatile 为何呢？
        volatile boolean terminated = false;
        boolean started = false;
        Thread workThread;

        synchronized void start() {
            if (started) {
                return;
            }
            started = true;
            workThread = new Thread(() -> {
                // 2.通过标记位判断线程中断状态来停止线程
                while (!terminated) {
                    Utils.println("do work...");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        //重新设置线程中断状态
                        Thread.currentThread().interrupt();
                    }
                }
                //执行到此处说明线程马上终止
                started = false;
            });
            workThread.start();
        }

        // 1.发送线程终止信号
        synchronized void stop() {
            terminated = true;
            if(workThread != null) {
                workThread.interrupt();
            }
        }
    }
}
