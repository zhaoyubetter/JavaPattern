package com.better.android.handler;

/**
 * 参考：
 * http://blog.csdn.net/qingchunweiliang/article/details/50448365
 * Created by zhaoyu on 2017/3/7.
 */
public class Message {
    Handler target;
    Object obj;
    int what;

    @Override
    public String toString() {
        return "[what: " + what + ", obj: " + obj + "]";
    }
}
