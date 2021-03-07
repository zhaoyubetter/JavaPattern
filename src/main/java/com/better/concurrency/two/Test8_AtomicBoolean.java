package com.better.concurrency.two;

import com.better.Utils;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * https://www.jianshu.com/p/ae25eb3cfb5d
 * 乐观锁
 */
public class Test8_AtomicBoolean {
    @Test
    public void test() throws InterruptedException {
        A a = new A();
        Thread t1 = new Thread(a);
        Thread t2 = new Thread(a);

        t1.start();
        t2.start();
        t2.join();

    }

    private AtomicBoolean flag = new AtomicBoolean(true);

    class A implements Runnable {
        @Override
        public void run() {
            System.out.println("thread:" + Thread.currentThread().getName() + ";flag:" + flag.get());
            if (flag.compareAndSet(true, false)) {
                System.out.println(Thread.currentThread().getName() + "" + flag.get());
                try {
                    // 这里造成cpu空转5s
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                flag.set(true);
            } else {
                System.out.println("retry thread:" + Thread.currentThread().getName() + ";flag:" + flag.get());
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                run();      // 自旋
            }
        }
    }
}
