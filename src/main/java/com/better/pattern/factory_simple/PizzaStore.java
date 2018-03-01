package com.better.pattern.factory_simple;

import com.better.pattern.factory_simple.abs.Pizza;

/**
 * Created by zhaoyu on 16/10/24.
 */

public class PizzaStore {
	SimplePizzaFactory factory;

	public PizzaStore(SimplePizzaFactory factory) {
		this.factory = factory;
	}

	public Pizza orderPizza(String type) {
		Pizza pizza = factory.createPizza(type);

		// 开始烘焙程序
		pizza.prepare();
		pizza.bake();
		pizza.cut();
		pizza.box();

		return pizza;
	}
}
