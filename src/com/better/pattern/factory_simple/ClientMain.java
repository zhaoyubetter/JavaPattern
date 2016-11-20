package com.better.pattern.factory_simple;

import com.better.pattern.factory_simple.abs.Pizza;

/**
 * Created by zhaoyu on 16/10/24.
 */

public class ClientMain {

	public static void main(String[] args) {
		// orderPizza("cheese");

		// 通过工厂来创建pizza
		PizzaStore store = new PizzaStore(new SimplePizzaFactory());
		store.orderPizza("greek");
	}

	/**
	 * 根据传递的类型，返回对应的pizza
	 *
	 * @param type
	 * @return
	 */
	public static Pizza orderPizza(String type) {
		Pizza pizza = null;

		/*, 如果类型过多，这里会有很多判断
		if ("cheese".equalsIgnoreCase(type)) {
			pizza = new CheesePizza();
		} else if ("greek".equalsIgnoreCase(type)) {
			pizza = new GreekPizza();
		} else if ("pepperoni".equalsIgnoreCase(type)) {
			pizza = new PepperoniPizza();
		}*/

		// 替换成简单工厂，判断移入简单工厂类里头
		pizza = SimplePizzaFactory.createPizza(type);

		// 开始烘焙
		pizza.prepare();
		pizza.bake();
		pizza.cut();
		pizza.box();

		return pizza;
	}

}
