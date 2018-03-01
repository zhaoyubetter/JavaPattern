package com.better.pattern.decorator;

/**
 * 调料装饰类
 * Created by zhaoyu on 16/10/11.
 */
public abstract class CondimentDecorator extends Beverage {

	/**
	 * 每个调料都有名称
	 *
	 * @return
	 */
	public abstract String getDescription();
}
