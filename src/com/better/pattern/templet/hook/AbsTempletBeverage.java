package com.better.pattern.templet.hook;

/**
 * 模板，加入钩子方法
 * Created by zhaoyu on 2016/11/8.
 */

abstract class AbsTempletBeverage {
	protected final void prepareRecipe() {
		boilWater();
		brew();
		pourInCup();
		if (customerWantsCondiments()) {
			addCondiments();
		}
	}

	protected boolean customerWantsCondiments() {
		return true;
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
