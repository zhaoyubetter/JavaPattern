package com.better.concurrency.two;

import com.better.Utils;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ThreadLocal
 * <p>
 * https://zhuanlan.zhihu.com/p/139214244
 */
public class Test14_thread_local {

    @Test
    public void test1() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            Utils.println(Thread.currentThread().getName() + "->" + ThreadId.get());
            Utils.println(Thread.currentThread().getName() + "->" + ThreadId.get());
            ThreadId.tl2.set("abc");
        }
        );

        Thread t2 = new Thread(() -> {
            ThreadId.tl2.set("123");
            Utils.println(Thread.currentThread().getName() + "->" +
                    ThreadId.get());
        }
        );
        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    static class ThreadId {
        // 1.所用线程使用同一份 ThreadLocal 对象
        // 2.每个线程都有自己的 ThreadLocalMap 对象，通过 Entry 保存 key/value，key 为 threadLocal 对象, value 为值；
        // 3.如果有多个变量，得再创建一个 ThreadLocal 对象，也就是每个 ThreadLocal 放一个线程局部变量，
        //   多个值都放在 ThreadLocalMap 对象中，具体保存在 其 table 中；
        // 4.为什么 ThreadLocal，也就是 key 使用弱引用？如果不使用，如果外界设置 threadLocal 为null，但是，由于 table 中引用了
        //   实际GC是回收不了key的？
        // 5.那么为何 value 不使用弱引用呢？
        //   a.如使用弱引用，获取值时：使用不便；
        //   b.另如果弱引用，当GC可能就会回收value，而此时 threadLocal 还在使用，那么获取就是空指针异常；
        // 6.当不在使用时，记得 remove value，避免内存泄露

        static final AtomicInteger newxtId = new AtomicInteger(0);
        static final ThreadLocal<Integer> tl = ThreadLocal.withInitial(() -> newxtId.getAndIncrement());
        static final ThreadLocal<String> tl2 = new ThreadLocal<>();

        static {
        }

        static int get() {
            return tl.get();
        }
    }

}
