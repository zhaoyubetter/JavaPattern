package com.better.pattern.iterator.common;

import com.better.pattern.iterator.Iterator;
import com.better.pattern.iterator.iter.ArrayMenuIteator;
import com.better.pattern.iterator.iter.Menu;

/**
 * Created by zhaoyu on 2016/11/9.
 */

public class ArrayMenu implements Menu<MenuItem>{

	static final int MAX_ITEMS = 6;

	int numberOfItems = 0;


	MenuItem[] menuItems;

	public ArrayMenu() {
		this.menuItems = new MenuItem[MAX_ITEMS];
		addItem("炒鸡肉", "土鸡呢", false, 50);
		addItem("跳跳蛙", "肚子有点饿了", false, 48);
		addItem("小葱豆腐", "小时候的味道", true, 12);
		addItem("炒白菜", "正宗大火炒", true, 15);
	}


	public void addItem(String name, String desc, boolean vegetarian, double price) {
		MenuItem item = new MenuItem(name, desc, vegetarian, price);
		if (numberOfItems > MAX_ITEMS) {
			throw new RuntimeException("菜谱满了");
		}

		menuItems[numberOfItems++] = item;
	}

	/*
	public MenuItem[] getMenuItems() {
		return menuItems;
	}
	*/

	public Iterator<MenuItem> createIterator() {
		return new ArrayMenuIteator(menuItems);
	}
}
