package com.better.pattern.factory.abs;

import java.util.ArrayList;

/**
 * 抽象接口
 * Created by zhaoyu on 16/10/24.
 */
public abstract class Pizza {

	/**
	 * 名称
	 */
	protected  String name;
	/**
	 * 面团类型
	 */
	protected String dough;
	/**
	 * 酱料类型
	 */
	protected String sauce;
	/**
	 * 一堆佐料
	 */
	protected ArrayList toppings = new ArrayList();

	protected void prepare() {
		System.out.println("Preparing " + name);
		System.out.println("Tossing dough (揉面)... ");
		System.out.println("Adding sauce(添加酱料)... ");
		System.out.println("Adding toppings(添加佐料)... ");
		for (int i = 0; i < toppings.size(); i++) {
			System.out.println("		" + toppings.get(i));
		}
	}

	protected void bake() {
		System.out.println("Bake for 25 minutes at 350");
	}

	protected void cut() {
		System.out.println("Cutting the pizza into diagonal slices");
	}

	protected void box() {
		System.out.println("Place pizza in official PizzaStore box");
	}

	public String getName() {
		return name;
	}
}
