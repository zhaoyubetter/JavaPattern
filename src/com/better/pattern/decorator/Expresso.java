package com.better.pattern.decorator;

/**
 * 浓缩 coffee
 * Created by zhaoyu on 16/10/11.
 */
public class Expresso extends Beverage {

	public Expresso() {
		descripiton = "浓缩coffee";
	}

	@Override
	public double cost() {
		return 1.99;
	}
}
