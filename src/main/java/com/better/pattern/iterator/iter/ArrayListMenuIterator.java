package com.better.pattern.iterator.iter;

import com.better.pattern.iterator.Iterator;
import com.better.pattern.iterator.common.MenuItem;

import java.util.ArrayList;

/**
 * Created by zhaoyu on 2016/11/9.
 */

public class ArrayListMenuIterator implements Iterator<MenuItem> {

	ArrayList<MenuItem> items;
	int position = 0;

	public ArrayListMenuIterator(ArrayList<MenuItem> items) {
		this.items = items;
	}

	@Override
	public boolean haxNext() {
		if (position < items.size()) {
			return true;
		}
		return false;
	}

	@Override
	public MenuItem next() {
		return items.get(position++);
	}
}
