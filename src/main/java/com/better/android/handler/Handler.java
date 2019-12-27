package com.better.android.handler;

/**
 * Created by zhaoyu on 2017/3/7.
 */
public class Handler {

    private MessageQueue messageQueue;

    public Handler() {
        Looper looper = Looper.myLooper();
        if (looper == null) {
            throw new RuntimeException("Can't generate handler inside thread that has not called Looper.prepare()");
        }
        messageQueue = looper.messageQueue;
    }

    public void sendMessage(Message msg) {
        msg.target = this;
        messageQueue.queueMessage(msg);
    }

    public void handleMessage(Message msg) {

    }
}
