package com.better.pattern.factory.pizza;

import com.better.pattern.factory.abs.Pizza;

/**
 * Created by zhaoyu on 16/10/24.
 */
public class ChicagoStyleCheesePizza extends Pizza {
	public ChicagoStyleCheesePizza() {
		name = "Chicago Style Deep Dish Cheese Pizza";
		dough = "Extra Thick Crust Dough";
		sauce = "Plum Tomato Sauce";

		toppings.add("Chicago toppings");
	}

	@Override
	protected void cut() {
		// super.cut();
		System.out.println("Cutting the pizza into square slices");
	}
}
