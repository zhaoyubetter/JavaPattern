package com.better.pattern.decorator;

/**
 * 牛奶coffee 装饰着
 * Created by zhaoyu on 16/10/11.
 */

public class Milk extends CondimentDecorator {

	private Beverage beverage;

	public Milk(Beverage beverage) {
		this.beverage = beverage;
	}

	@Override
	public String getDescription() {
		return beverage.getDescription() + ", Milk";
	}

	@Override
	public double cost() {
		return 1.3 + beverage.cost();        // 返回加入牛奶后的价格
	}
}
