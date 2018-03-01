package com.better.pattern.iterator.common;

import com.better.pattern.iterator.Iterator;
import com.better.pattern.iterator.iter.ArrayListMenuIterator;
import com.better.pattern.iterator.iter.Menu;

import java.util.ArrayList;

/**
 * Created by zhaoyu on 2016/11/9.
 */

public class ArrayListMenu implements Menu<MenuItem> {
	ArrayList<MenuItem> menuItems;


	public ArrayListMenu() {
		this.menuItems = new ArrayList<>();
		addItem("红烧肉", "毛式红烧肉", false, 38);
		addItem("红烧鱼", "红烧大草<。)#)))≦", false, 55);
		addItem("清炒空心菜", "可选择是否放大蒜", true, 12);
		addItem("酸辣土豆丝", "有酸有辣", true, 10);

	}

	public void addItem(String name, String desc, boolean vegetarian, double price) {
		MenuItem item = new MenuItem(name, desc, vegetarian, price);
		menuItems.add(item);
	}

	/*
	public ArrayList<MenuItem> getMenuItems() {
		return menuItems;
	}*/


	public Iterator<MenuItem> createIterator() {
		return new ArrayListMenuIterator(menuItems);
	}
}
