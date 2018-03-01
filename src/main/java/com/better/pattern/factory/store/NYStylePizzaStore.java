package com.better.pattern.factory.store;

import com.better.pattern.factory.abs.Pizza;
import com.better.pattern.factory.abs.PizzaStore;
import com.better.pattern.factory.pizza.NYStyleCheesePizza;
import com.better.pattern.factory.pizza.NYStyleClamPizza;
import com.better.pattern.factory.pizza.NYStylePepperoniPizza;

/**
 * NY
 * Created by zhaoyu on 16/10/24.
 */
public class NYStylePizzaStore extends PizzaStore {
	@Override
	protected Pizza createPizza(String type) {
		Pizza pizza = null;
		if ("cheese".equalsIgnoreCase(type)) {
			pizza = new NYStyleCheesePizza();
		} else if ("pepperoni".equalsIgnoreCase(type)) {
			pizza = new NYStylePepperoniPizza();
		} else if ("clam".equalsIgnoreCase(type)) {
			pizza = new NYStyleClamPizza();
		}

		return pizza;
	}
}
