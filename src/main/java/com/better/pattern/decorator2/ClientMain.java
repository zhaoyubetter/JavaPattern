package com.better.pattern.decorator2;

import com.better.pattern.decorator2.abs.IBread;
import com.better.pattern.decorator2.common.NormalBread;
import com.better.pattern.decorator2.decorator.SweetDecoratorBread;
import com.better.pattern.decorator2.decorator.YellowDecoratorBread;

/**
 * Created by zhaoyu on 16/10/23.
 */

public class ClientMain {
	public static void main(String[] args) {
		IBread bread = new NormalBread();
		bread.process();

		System.out.println(" --------------------------------- ");

		bread = new YellowDecoratorBread(bread);		// 装饰
		bread.process();

		System.out.println(" --------------------------------- ");

		bread = new SweetDecoratorBread(bread);			// 装饰
		bread.process();

		System.out.println(" --------------------------------- ");
	}
}
