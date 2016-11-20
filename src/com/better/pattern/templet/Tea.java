package com.better.pattern.templet;

/**
 * Created by zhaoyu on 2016/11/6.
 */

public class Tea extends AbsTempletBeverage {

	/*
	void prepareRecipe() {
		boilWater();
		steepTeaBg();
		pourInCup();
		addLemon();
	}*/

	@Override
	protected void addCondiments() {
		System.out.println("加点柠檬 Adding Lemon");
	}

	@Override
	protected void brew() {
		System.out.println("泡茶 Steeping the tea");
	}
}
