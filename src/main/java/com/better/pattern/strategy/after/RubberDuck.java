package com.better.pattern.strategy.after;

import com.better.pattern.strategy.after.behavior.FlyNoWay;
import com.better.pattern.strategy.after.behavior.Squeak;

/**
 * Created by zhaoyu on 16/9/19.
 */
public class RubberDuck extends Duck {

	public RubberDuck() {
		this.flyBehavior = new FlyNoWay();
		this.quackBehavior = new Squeak();
	}


	@Override
	public void display() {
		System.out.println("I'm a rubber duck.");
	}
}
