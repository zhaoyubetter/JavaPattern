package com.better.pattern.iterator.iter;

import com.better.pattern.iterator.Iterator;

/**
 * Created by zhaoyu on 2016/11/9.
 */

public interface Menu<E> {
	public Iterator<E> createIterator();
}
