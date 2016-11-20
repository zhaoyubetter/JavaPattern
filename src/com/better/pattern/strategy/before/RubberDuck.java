package com.better.pattern.strategy.before;

/**
 * 橡皮鸭子
 * Created by zhaoyu on 16/9/19.
 */
public class RubberDuck extends Duck {
	@Override
	public void display() {
		System.out.println("外观：我是橡皮鸭子");
	}

	@Override
	public void quack() {
		System.out.println("吱吱吱...");
	}

	/**
	 * 橡皮鸭子不会飞
	 */
	@Override
	public void fly() {
	}
}
