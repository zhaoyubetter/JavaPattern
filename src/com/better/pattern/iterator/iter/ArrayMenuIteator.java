package com.better.pattern.iterator.iter;

import com.better.pattern.iterator.Iterator;
import com.better.pattern.iterator.common.MenuItem;

/**
 * Created by zhaoyu on 2016/11/9.
 */

public class ArrayMenuIteator implements Iterator<MenuItem> {

	MenuItem[] menusItems;
	int position;

	public ArrayMenuIteator(MenuItem[] menusItems) {
		this.menusItems = menusItems;
	}

	@Override
	public boolean haxNext() {
		if (position < menusItems.length && menusItems[position] != null) {
			return true;
		}
		return false;
	}

	@Override
	public MenuItem next() {
		return menusItems[position++];
	}
}
