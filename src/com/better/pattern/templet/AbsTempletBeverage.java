package com.better.pattern.templet;

/**
 * 模板
 * Created by zhaoyu on 2016/11/6.
 */

public abstract class AbsTempletBeverage {
	protected final void prepareRecipe() {
		boilWater();
		brew();
		pourInCup();
		addCondiments();
	}

	protected abstract void addCondiments();

	protected abstract void brew();

	public void boilWater() {
		System.out.println("煮沸水 Boiling water ");
	}


	public void pourInCup() {
		System.out.println("倒入杯中 Pouring into cup");
	}
}
