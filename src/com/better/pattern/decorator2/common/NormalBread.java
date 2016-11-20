package com.better.pattern.decorator2.common;

import com.better.pattern.decorator2.abs.IBread;

/**
 * 正常面包
 * Created by zhaoyu on 16/10/23.
 */

public class NormalBread implements IBread {
	@Override
	public void prepare() {
		System.out.println("----> 准备材料");
	}

	@Override
	public void knead() {
		System.out.println("----> 和面");
	}

	@Override
	public void steadmed() {
		System.out.println("----> 蒸");
	}

	@Override
	public void process() {
		prepare();
		knead();
		steadmed();
	}
}
