package com.better.concurrency.part_8_lock;

import com.better.concurrency.anno.ThreadSafe;

@ThreadSafe
public abstract class BaseBoundedBuffer<V> {
    private final V[] buffer;
    private int tail;
    private int head;
    private int count;

    protected BaseBoundedBuffer(int capacity) {
        buffer = (V[]) new Object[capacity];
    }

    protected synchronized final void doPut(V v) {
        buffer[tail] = v;
        if (++tail == buffer.length) {
            tail = 0;
        }
        count++;
    }

    protected synchronized final V doTake() {
        V v = buffer[head];
        buffer[head] = null;
        if (++head == buffer.length) {
            head = 0;
        }
        --count;
        return v;
    }

    public synchronized final boolean isFull() {
        return count == buffer.length;
    }

    public synchronized final boolean isEmpty() {
        return count == 0;
    }
}
