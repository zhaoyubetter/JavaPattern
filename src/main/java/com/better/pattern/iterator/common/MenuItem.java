package com.better.pattern.iterator.common;

/**
 * Created by zhaoyu on 2016/11/9.
 */

public class MenuItem {
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

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public boolean isVegetarian() {
		return vegetarian;
	}

	public double getPrice() {
		return price;
	}
}
