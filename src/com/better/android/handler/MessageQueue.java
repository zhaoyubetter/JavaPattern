package com.better.android.handler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by zhaoyu on 2017/3/7.
 */
public class MessageQueue {

    private BlockingQueue<Message> messageQueue = new LinkedBlockingQueue<>();

    public Message next() {
        try {
            return messageQueue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void queueMessage(Message msg) {
        try {
            messageQueue.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
