package com.better.pattern.adapter.array;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * Created by zhaoyu on 2016/11/5.
 */

public class EnumIteratorAdapter<E> implements Enumeration<E> {

	Iterator<E> iterator;

	public EnumIteratorAdapter(Iterator iterator) {
		this.iterator = iterator;
	}

	@Override
	public boolean hasMoreElements() {
		return iterator.hasNext();
	}

	@Override
	public E nextElement() {
		return iterator.next();
	}
}
