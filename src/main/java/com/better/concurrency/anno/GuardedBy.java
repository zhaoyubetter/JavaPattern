package com.better.concurrency.anno;

/**
 * 保护锁
 */
public @interface GuardedBy {
    String value();
}
