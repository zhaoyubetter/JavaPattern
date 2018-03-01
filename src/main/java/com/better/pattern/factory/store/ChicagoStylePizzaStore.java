package com.better.pattern.factory.store;

import com.better.pattern.factory.abs.Pizza;
import com.better.pattern.factory.abs.PizzaStore;
import com.better.pattern.factory.pizza.ChicagoStyleCheesePizza;
import com.better.pattern.factory.pizza.ChicagoStyleClamPizza;
import com.better.pattern.factory.pizza.ChicagoStylePepperoniPizza;

/**
 * Chicago
 * Created by zhaoyu on 16/10/24.
 */
public class ChicagoStylePizzaStore extends PizzaStore {
	@Override
	protected Pizza createPizza(String type) {
		Pizza pizza = null;
		if ("cheese".equalsIgnoreCase(type)) {
			pizza = new ChicagoStyleCheesePizza();
		} else if ("pepperoni".equalsIgnoreCase(type)) {
			pizza = new ChicagoStylePepperoniPizza();
		} else if ("clam".equalsIgnoreCase(type)) {
			pizza = new ChicagoStyleClamPizza();
		}

		return pizza;
	}
}
