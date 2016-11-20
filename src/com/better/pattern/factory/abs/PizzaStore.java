package com.better.pattern.factory.abs;


/**
 * 改变 Store 为抽象类，创建pizza交给子类来做，
 * createPizza放回
 * Created by zhaoyu on 16/10/24.
 */

public abstract class PizzaStore {
	public Pizza orderPizza(String type) {
		Pizza pizza = createPizza(type);

		// 开始烘焙程序
		pizza.prepare();
		pizza.bake();
		pizza.cut();
		pizza.box();

		return pizza;
	}

	/**
	 * 如同工厂
	 *
	 * @param type
	 * @return
	 */
	protected abstract Pizza createPizza(String type);
}
