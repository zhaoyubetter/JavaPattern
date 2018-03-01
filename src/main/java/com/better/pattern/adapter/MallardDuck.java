package com.better.pattern.adapter;

import com.better.pattern.adapter.abs.Duck;

/**
 * Created by zhaoyu on 2016/11/5.
 */

public class MallardDuck implements Duck {
	@Override
	public void quack() {
		System.out.println("Quack");
	}

	@Override
	public void fly() {
		System.out.println("I'm flying");
	}
}
