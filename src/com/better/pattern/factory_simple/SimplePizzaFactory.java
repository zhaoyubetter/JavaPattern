package com.better.pattern.factory_simple;

import com.better.pattern.factory_simple.abs.Pizza;
import com.better.pattern.factory_simple.common.CheesePizza;
import com.better.pattern.factory_simple.common.GreekPizza;
import com.better.pattern.factory_simple.common.PepperoniPizza;

/**
 * 简单工厂
 * Created by zhaoyu on 16/10/24.
 */
public class SimplePizzaFactory {

	public static Pizza createPizza(String type) {
		Pizza pizza = null;

		if ("cheese".equalsIgnoreCase(type)) {
			pizza = new CheesePizza();
		} else if ("greek".equalsIgnoreCase(type)) {
			pizza = new GreekPizza();
		} else if ("pepperoni".equalsIgnoreCase(type)) {
			pizza = new PepperoniPizza();
		}

		return pizza;
	}
}
