package com.better.android.handler;

/**
 * Created by zhaoyu on 2017/3/7.
 */
public class Looper {

    private static final ThreadLocal<Looper> threadLocal = new ThreadLocal<>();
    // 一个Looper对应一个消息队列
    final MessageQueue messageQueue;

    private Looper() {
        messageQueue = new MessageQueue();
    }

    public static void prepare() {
        if (threadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        threadLocal.set(new Looper());
    }

    public static void loop() {
        Looper looper = myLooper();
        if (looper != null) {
            for (; ; ) {
                Message message = looper.messageQueue.next();
                message.target.handleMessage(message);
            }
        } else {
            throw new RuntimeException("prepare not called");
        }
    }

    public static Looper myLooper() {
        return threadLocal.get();
    }

}
