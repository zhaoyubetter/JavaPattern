package com.better.pattern.factory;

import com.better.pattern.factory.abs.Pizza;
import com.better.pattern.factory.abs.PizzaStore;
import com.better.pattern.factory.store.ChicagoStylePizzaStore;
import com.better.pattern.factory.store.NYStylePizzaStore;

/**
 * Created by zhaoyu on 16/10/24.
 */

public class ClientMain {
	public static void main(String[] args) {
		PizzaStore nyStore = new NYStylePizzaStore();
		PizzaStore chicagoStore = new ChicagoStylePizzaStore();

		Pizza pizza = nyStore.orderPizza("cheese");
		System.out.println("CC ordered a " + pizza.getName() + "\n");

		System.out.println("---------------------------");

		pizza = chicagoStore.orderPizza("cheese");
		System.out.println("Better ordered a " + pizza.getName() + "\n");
	}
}
