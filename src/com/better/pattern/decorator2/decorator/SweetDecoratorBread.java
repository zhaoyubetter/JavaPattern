package com.better.pattern.decorator2.decorator;

import com.better.pattern.decorator2.abs.DecoratorBread;
import com.better.pattern.decorator2.abs.IBread;

/**
 * Created by zhaoyu on 16/10/23.
 */

public class SweetDecoratorBread extends DecoratorBread {
	public SweetDecoratorBread(IBread bread) {
		super(bread);
	}

	public void paint() {
		System.out.println(" ----> 加入点甜蜜素");
	}

	public void knead() {
		paint();
		super.knead();
	}

	@Override
	public void process() {
		super.process();
	}
}
