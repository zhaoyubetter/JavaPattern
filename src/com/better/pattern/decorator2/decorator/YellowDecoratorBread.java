package com.better.pattern.decorator2.decorator;

import com.better.pattern.decorator2.abs.DecoratorBread;
import com.better.pattern.decorator2.abs.IBread;

/**
 * Created by zhaoyu on 16/10/23.
 */

public class YellowDecoratorBread extends DecoratorBread {

	public YellowDecoratorBread(IBread bread) {
		super(bread);
	}

	public void paint() {
		System.out.println("----> 加入点黄颜色的染色剂，制造黄颜色的面包");
	}


	public void knead() {
		paint();
		super.knead();
	}
}
