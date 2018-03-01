package com.better.pattern.strategy2;

/**
 * 整体8折策略
 * Created by zhaoyu on 16/9/20.
 */
public class RebateStrategy implements IPriceStrategy {

	private float rebate = 0.8f;

	public void setRebate(float rebate) {
		this.rebate = rebate;
	}

	@Override
	public double getPrice(double price) {
		return price * rebate;
	}
}
