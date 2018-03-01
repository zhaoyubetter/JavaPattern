package com.better.pattern.factory_simple.common;

import com.better.pattern.factory_simple.abs.Pizza;

/**
 * Created by zhaoyu on 16/10/24.
 */

public class CheesePizza implements Pizza {
	public void prepare() {
		System.out.println(" CheesePizza -----> prepare() ");
	}

	public void bake() {
		System.out.println(" CheesePizza -----> bake() ");
	}

	public void cut() {
		System.out.println(" CheesePizza -----> cut() ");
	}

	public void box() {
		System.out.println(" CheesePizza -----> box() ");
	}
}
