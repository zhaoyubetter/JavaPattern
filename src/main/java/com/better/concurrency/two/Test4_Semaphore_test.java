package com.better.concurrency.two;

import com.better.Utils;
import org.junit.Test;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

/**
 * 限流器
 */
public class Test4_Semaphore_test {
    @Test
    public void test1() throws InterruptedException {
        final ObjPool<Long, String> pool = new ObjPool<Long, String>(10, 2L);
        pool.exec(aLong -> {
            Utils.println(aLong);
            return aLong.toString();
        });
    }

    static class ObjPool<T, R> {
        final List<T> pool;
        final Semaphore sem;

        /**
         * @param size 限流大小
         * @param t
         */
        public ObjPool(int size, T t) {
            this.pool = new Vector<T>();        // 必须使用 vector
            for (int i = 0; i < size; i++) {
                pool.add(t);
            }
            this.sem = new Semaphore(size);
        }

        R exec(Function<T, R> func) throws InterruptedException {
            T t = null;
            sem.acquire();
            try {
                // 信号量支持多个线程进入临界区，执行list的add和remove方法时可能是多线程并发执行
                t = pool.remove(0);
                return func.apply(t);
            } finally {
                pool.add(t);
                sem.release();
            }
        }
    }
}
