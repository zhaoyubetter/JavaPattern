package com.better.pattern.strategy.after.behavior;

/**
 * Created by zhaoyu on 16/9/19.
 */
public class Squeak implements QuackBehavior {
	@Override
	public void quack() {
		System.out.println("叫的行为：吱吱吱...");
	}
}
