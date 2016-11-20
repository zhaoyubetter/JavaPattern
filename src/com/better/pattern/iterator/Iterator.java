package com.better.pattern.iterator;

/**
 * 迭代器接口
 * Created by zhaoyu on 2016/11/9.
 */
public interface Iterator<T> {
	boolean haxNext();

	T next();
}
