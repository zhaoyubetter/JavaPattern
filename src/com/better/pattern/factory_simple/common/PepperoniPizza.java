package com.better.pattern.factory_simple.common;

import com.better.pattern.factory_simple.abs.Pizza;

/**
 * Created by zhaoyu on 16/10/24.
 */

public class PepperoniPizza implements Pizza {
	public void prepare() {
		System.out.println(" PepperoniPizza -----> prepare() ");
	}

	public void bake() {
		System.out.println(" PepperoniPizza -----> bake() ");
	}

	public void cut() {
		System.out.println(" PepperoniPizza -----> cut() ");
	}

	public void box() {
		System.out.println(" PepperoniPizza -----> box() ");
	}
}
