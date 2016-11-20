package com.better.pattern.factory.pizza;

import com.better.pattern.factory.abs.Pizza;

/**
 * Created by zhaoyu on 16/10/24.
 */

public class NYStyleCheesePizza extends Pizza {
	public NYStyleCheesePizza() {
		name = "NY Style Cheese Pizza";
		dough = "Thin Crust Dough";
		sauce = "Marinara Sauce";

		toppings.add("NY Style toppings");
	}
}
