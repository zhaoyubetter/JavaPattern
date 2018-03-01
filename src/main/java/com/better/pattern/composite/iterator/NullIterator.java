package com.better.pattern.composite.iterator;

import com.better.pattern.composite.abs.MenuComponent;

import java.util.Iterator;

/**
 * Created by zhaoyu on 2016/11/12.
 */

public class NullIterator implements Iterator<MenuComponent> {
	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public MenuComponent next() {
		return null;
	}
}
