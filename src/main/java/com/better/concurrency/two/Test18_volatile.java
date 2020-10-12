package com.better.concurrency.two;

import com.better.groovy.app.lineCount.Main;
import org.junit.Test;

/**
 * 来自郭林的例子
 * https://mp.weixin.qq.com/s/WtMeB-4sXOYQtvYKUx6c5Q
 *
 * @author zhaoyu1
 * @date 2020/10/12 10:44 AM
 */
public class Test18_volatile {

    /**
     * 可见性测试
     */
    static class V1 {
        /*volatile*/ static  boolean flag = false;

        static class T1 extends Thread {
            @Override
            public void run() {
                while(true) {
                    if (flag) {
                        System.out.println("Thread 1 change to false");
                        flag = false;
                    }
                }
            }
        }

        static class T2 extends Thread {
            @Override
            public void run() {
                while(true) {
                    if (!flag) {
                        System.out.println("Thread 2 change to true");
                        flag = true;
                    }
                }
            }
        }
    }


    /**
     * 理论上来说，这两个线程同时运行，那么就应该一直交替打印，你改我的值，我再给你改回去。
     * 但现实是这样：
     *  运行一段时间后，卡住了。。。死循环了。这就是可见性问题
     * 原因：
     *  因为 Thread1 和 Thread2 的 CPU高速缓存中各有一份 flag 值，其中Thread1中缓存的flag值是false，
     *  Thread2 中缓存的flag值是true，所以两边就都不会打印了。
     * 如何解决：
     *  使用 volatile 关键字修饰 flag
     *
     * @param aa
     */
    public static void main(String[] aa) {
        Thread t1 = new V1.T1();
        Thread t2 = new V1.T2();
        t1.start();
        t2.start();
    }

    ////////////////////////////////////////////////
    ////////////////////////////////////////////////
    ////////////////////////////////////////////////

    /**
     * 指令重排测试
     */
    static class V2 {
        static boolean init;
        static String value;

        static class T1 extends Thread {
            @Override
            public void run() {
                value = "inited value";
                init = true;
            }
        }

        static class T2 extends Thread {
            @Override
            public void run() {
                while(!init) {
                    // 等待初始化
                }
                System.out.println(value);
            }
        }

        /**
         * Thread1中value和init这两个变量之间是没有先后顺序的。如果CPU将这两条指令进行了重排，
         * 那么就可能出现初始化已完成，
         * 但是value还没有赋值的情况。这样Thread2的while循环就会跳出，然后在操作value的时候出现空指针异常。
         * @param a
         */
        public static void main(String... a) {
            Thread t1 = new V2.T1();
            Thread t2 = new V2.T2();
            t1.start();
            t2.start();
        }
    }

    /**
     * 禁止指令重拍
     */
    public void testCmdSort() {

    }
}
