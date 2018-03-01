package com.better.pattern.templet;

/**
 * 冲泡coffee
 * Created by zhaoyu on 2016/11/6.
 */
public class Coffee extends AbsTempletBeverage {

	/*
	public void prepareRecipe() {
		boilWater();
		brewCoffeeGrinds();
		pourInCup();
		addSugarAndMilk();
	}*/

	@Override
	protected void addCondiments() {
		System.out.println("添加牛奶和糖 Adding Sugar and Milk");
	}

	@Override
	protected void brew() {
		System.out.println("冲咖啡  Dripping Coffee through filter");
	}
}
