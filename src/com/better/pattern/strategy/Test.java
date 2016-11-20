package com.better.pattern.strategy;

import com.better.pattern.strategy.after.Duck;
import com.better.pattern.strategy.after.MallardDuck;
import com.better.pattern.strategy.after.RubberDuck;
import com.better.pattern.strategy.after.behavior.FlyByRocket;

/**
 * Created by zhaoyu on 16/9/19.
 */
public class Test {

	@org.junit.Test
	public void test1() {
		Duck duck = new MallardDuck();
		duck.display();
		duck.performFly();
		duck.performQuack();
	}

	@org.junit.Test
	public void test2() {
		Duck duck = new RubberDuck();
		duck.display();
//		duck.performFly();

		duck.setFlyBehavior(new FlyByRocket());		// 设置新行为
		duck.performFly();
		duck.performQuack();
		duck.swim();
	}
}
