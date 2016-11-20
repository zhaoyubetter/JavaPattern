package com.better.pattern.strategy.after.behavior;

/**
 * Created by zhaoyu on 16/9/19.
 */
public class FlyWithWings implements FlyBehavior {
	@Override
	public void fly() {
		System.out.println("飞行行为：使用翅膀飞...");
	}
}
