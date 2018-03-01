package com.better.pattern.strategy2;

/**
 * 满 200, 超出 200 部分 以上8折策略
 * Created by zhaoyu on 16/9/20.
 */
public class OverRebateStrategy implements IPriceStrategy {

	private float rebate = 0.8f;

	public void setRebate(float rebate) {
		this.rebate = rebate;
	}

	@Override
	public double getPrice(double price) {
		return price < 200 ? price : 200 + (price - 200) * rebate;
	}

}
