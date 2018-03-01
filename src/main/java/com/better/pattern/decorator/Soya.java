package com.better.pattern.decorator;

/**
 * 豆浆装饰
 * Created by zhaoyu on 16/10/11.
 */

public class Soya extends CondimentDecorator {

	private Beverage beverage;

	public Soya(Beverage beverage) {
		this.beverage = beverage;
	}

	public int getSize() {
		return beverage.getSize();
	}

	@Override
	public String getDescription() {
		return beverage.getDescription() + ", soya";
	}

	@Override
	public double cost() {
		double cost = beverage.cost();
		if (getSize() == Beverage.TALL) {
			cost += 0.2;
		} else if (getSize() == Beverage.GRANDE) {
			cost += 0.5;
		} else if (getSize() == Beverage.VENTI) {
			cost += 0.8;
		}
		return cost;
	}
}
