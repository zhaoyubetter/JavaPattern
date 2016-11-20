package com.better.pattern.factory_simple.common;

import com.better.pattern.factory_simple.abs.Pizza;

/**
 * Created by zhaoyu on 16/10/24.
 */

public class GreekPizza implements Pizza {
	public void prepare() {
		System.out.println(" GreekPizza -----> prepare() ");
	}

	public void bake() {
		System.out.println(" GreekPizza -----> bake() ");
	}

	public void cut() {
		System.out.println(" GreekPizza -----> cut() ");
	}

	public void box() {
		System.out.println(" GreekPizza -----> box() ");
	}
}
