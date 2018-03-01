package com.better.pattern.decorator;

/**
 * houseBlend coffee
 * Created by zhaoyu on 16/10/11.
 */
public class HouseBlend extends Beverage {

	public HouseBlend() {
		descripiton = "HouseBlend coffee";
	}

	@Override
	public double cost() {
		return 0.99;
	}
}
