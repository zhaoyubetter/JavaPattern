package com.better.pattern.strategy2;

/**
 * 满 1000 - 200
 * Created by zhaoyu on 16/9/20.
 */
public class OverCutDownStrategy implements IPriceStrategy {
	@Override
	public double getPrice(double price) {
		return price > 1000 ? price - 200 : price;
	}
}
