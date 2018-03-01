package com.better.pattern.strategy.after;

import com.better.pattern.strategy.after.behavior.FlyWithWings;
import com.better.pattern.strategy.after.behavior.Quack;

/**
 * Created by zhaoyu on 16/9/19.
 */
public class MallardDuck extends Duck {

	/**
	 * 构造方法中，指定行为
	 */
	public MallardDuck() {
		quackBehavior = new Quack();
		flyBehavior = new FlyWithWings();
	}

	@Override
	public void display() {
		System.out.println("I' m a Mallard Duck!!");
	}
}
