package com.better.pattern.strategy.after.behavior;

/**
 * 叫的行为
 * Created by zhaoyu on 16/9/19.
 */
public class Quack implements QuackBehavior {
	@Override
	public void quack() {
		System.out.println("叫的行为：嘎嘎嘎...");
	}
}
