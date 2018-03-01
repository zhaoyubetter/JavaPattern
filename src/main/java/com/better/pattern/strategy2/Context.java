package com.better.pattern.strategy2;

import java.text.DecimalFormat;

/**
 * Created by zhaoyu on 16/9/20.
 */

public class Context {
	private IPriceStrategy strategy;

	public void setStrategy(IPriceStrategy strategy) {
		this.strategy = strategy;
	}

	public double getRealPrice(double price) {
		double realPrice = strategy.getPrice(price);
		DecimalFormat df = new DecimalFormat("#.##");
		return Double.parseDouble(df.format(realPrice));
	}
}
