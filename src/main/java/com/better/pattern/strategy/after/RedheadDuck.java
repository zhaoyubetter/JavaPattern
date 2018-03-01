package com.better.pattern.strategy.after;

import com.better.pattern.strategy.after.behavior.FlyWithWings;
import com.better.pattern.strategy.after.behavior.Quack;

/**
 * Created by zhaoyu on 16/9/19.
 */
public class RedheadDuck extends Duck {

	public RedheadDuck() {
		this.flyBehavior = new FlyWithWings();
		this.quackBehavior = new Quack();
	}

	@Override
	public void display() {
		System.out.println("I'm a redhead duck.");
	}
}
