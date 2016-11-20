package com.better.pattern.composite.common;

import com.better.pattern.composite.abs.MenuComponent;
import com.better.pattern.composite.iterator.NullIterator;

import java.util.Iterator;

/**
 * 菜单项
 * Created by zhaoyu on 2016/11/11.
 */
public class MenuItem extends MenuComponent {
	String name;
	String desc;
	boolean vegetarian;
	double price;

	public MenuItem(String name, String desc, boolean vegetarian, double price) {
		this.name = name;
		this.desc = desc;
		this.vegetarian = vegetarian;
		this.price = price;
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
	public double getPrice() {
		return price;
	}

	@Override
	public boolean isVegetarian() {
		return vegetarian;
	}

	@Override
	public void print() {
		System.out.print("	" + getName());
		if (isVegetarian()) {
			System.out.print("(v)");
		}
		System.out.println(", " + getPrice());
		System.out.println("			-- " + getDesc());
	}

	@Override
	public Iterator<MenuComponent> createIterator() {
		return new NullIterator();
	}
}
