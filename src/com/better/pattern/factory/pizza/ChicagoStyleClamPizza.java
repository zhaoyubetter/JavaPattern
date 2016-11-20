package com.better.pattern.factory.pizza;

import com.better.pattern.factory.abs.Pizza;

/**
 * Created by zhaoyu on 16/10/24.
 */

public class ChicagoStyleClamPizza extends Pizza {
	public ChicagoStyleClamPizza() {
		name = "Chicago Style Deep Dish Clam Pizza";
		dough = "Extra Thick Crust Dough";
		sauce = "Plum Tomato Sauce";

		toppings.add("Chicago toppings");
	}
}
