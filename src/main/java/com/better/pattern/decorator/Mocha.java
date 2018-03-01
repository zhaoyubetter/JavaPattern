package com.better.pattern.decorator;

/**
 * 摩卡 装饰者
 * Created by zhaoyu on 16/10/11.
 */
public class Mocha extends CondimentDecorator {

	/**
	 * 原始对象
	 */
	private Beverage beverage;

	/**
	 * 传入原始对象
	 *
	 * @param beverage
	 */
	public Mocha(Beverage beverage) {
		this.beverage = beverage;
	}

	/**
	 * 装饰方式
	 *
	 * @return
	 */
	@Override
	public String getDescription() {
		return this.beverage.getDescription() + ", Mocha ";
	}

	@Override
	public double cost() {
		return 0.2 + beverage.cost();        // 摩卡价格 + 被装饰对象价格
	}
}
