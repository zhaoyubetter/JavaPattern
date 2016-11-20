package com.better.pattern.adapter;

import com.better.pattern.adapter.abs.Turkey;

/**
 * Created by zhaoyu on 2016/11/5.
 */

public class WildTurkey implements Turkey {
	@Override
	public void gobble() {
		System.out.println("Gobble gobble");
	}

	@Override
	public void fly() {
		System.out.println("I'm flying a short distance");
	}
}
