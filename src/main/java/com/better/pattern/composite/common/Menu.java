package com.better.pattern.composite.common;


import com.better.pattern.composite.abs.MenuComponent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 菜单
 * Created by zhaoyu on 2016/11/11.
 */
public class Menu extends MenuComponent {

	/**
	 * 子菜单项
	 */
	List<MenuComponent> menuComponents = new ArrayList<>();
	String name;
	String desc;

	public Menu(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}

	@Override
	public void add(MenuComponent menuComponent) {
		menuComponents.add(menuComponent);
	}

	@Override
	public void remove(MenuComponent menuComponent) {
		menuComponents.remove(menuComponent);
	}

	@Override
	public MenuComponent getChild(int i) {
		return menuComponents.get(i);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDesc() {
		return desc;
	}

	@Override
	public void print() {
		System.out.print("\n" + getName());
		System.out.println(", " + getDesc());
		System.out.println("-----------------------");

		Iterator<MenuComponent> iterator = menuComponents.iterator();
		while (iterator.hasNext()) {
			MenuComponent item = iterator.next();
			item.print();
		}
	}

	@Override
	public  Iterator<MenuComponent> createIterator() {
		return  menuComponents.iterator();
		// return new CompositeIterator(menuComponents.iterator()); 不能使用这个
	}
}
