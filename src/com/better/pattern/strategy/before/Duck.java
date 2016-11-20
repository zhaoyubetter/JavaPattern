package com.better.pattern.strategy.before;

/**
 * 鸭子基类
 * Created by zhaoyu on 16/9/19.
 */
public abstract class Duck {

	public void quack() {
		System.out.println("嘎嘎嘎...");
	}

	public void swim() {
		System.out.println("鸭子游泳去了......");
	}

	public void fly() {
		System.out.println("鸭子飞起了......");
	}

	/**
	 * 外观抽象方法
	 */
	public abstract void display();
}