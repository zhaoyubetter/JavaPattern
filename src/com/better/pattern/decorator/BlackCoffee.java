package com.better.pattern.decorator;

/**
 * Created by zhaoyu on 16/10/11.
 */

public class BlackCoffee extends Beverage {

	public BlackCoffee() {
		this.descripiton = "BlackCoffee 黑色咖啡";
	}

	@Override
	public double cost() {
		return 5.55;
	}
}
