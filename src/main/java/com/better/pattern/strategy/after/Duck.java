package com.better.pattern.strategy.after;

import com.better.pattern.strategy.after.behavior.FlyBehavior;
import com.better.pattern.strategy.after.behavior.QuackBehavior;

/**
 * 鸭子抽象基类
 * Created by zhaoyu on 16/9/19.
 */
public abstract class Duck {


	/*
	public void quack() {
		System.out.println("嘎嘎嘎...");
	}

	public void fly() {
		System.out.println("鸭子飞起了......");
	}*/


	/**
	 * 需要变动的部分封装起来，采用接口，来表示每个行为，面向接口编程
	 * 并提供设置行为方法
	 */
	protected FlyBehavior flyBehavior;
	protected QuackBehavior quackBehavior;


	public void setFlyBehavior(FlyBehavior behavior) {
		this.flyBehavior = behavior;
	}

	public void setQuackBehavior(QuackBehavior behavior) {
		this.quackBehavior = behavior;
	}


	public void swim() {
		System.out.println("鸭子游泳去了......");
	}


	/**
	 * 外观抽象方法
	 */
	public abstract void display();

	/**
	 * 叫
	 */
	public void performQuack() {
		if (quackBehavior != null) {
			quackBehavior.quack();
		}
	}

	/**
	 * 飞
	 */
	public void performFly() {
		if (flyBehavior != null) {
			flyBehavior.fly();
		}
	}
}
