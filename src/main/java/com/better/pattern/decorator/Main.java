package com.better.pattern.decorator;

/**
 * Created by zhaoyu on 16/10/11.
 */

public class Main {

	public static void main(String[] arg) {
		// 来一杯黑色coffee
		Beverage beverage = new BlackCoffee();
		System.out.println(String.format("description: %s, price: %s", beverage.getDescription(), beverage.cost()));

		// 加点牛奶
		Milk milk = new Milk(beverage);
		System.out.println(String.format("description: %s, price: %s", milk.getDescription(), milk.cost()));

		// 再加点豆浆
		Soya soya = new Soya(milk);
		System.out.println(String.format("description: %s, price: %s", soya.getDescription(), soya.cost()));


		// 来个浓缩coffee
		Beverage expresso = new Expresso();
		System.out.println(String.format("description: %s, price: %s", expresso.getDescription(), expresso.cost()));


		// 来个大杯的豆浆coffee
		Beverage beverage3 = new HouseBlend();
		beverage3.setSize(Beverage.VENTI);
		beverage3 = new Soya(beverage3);
		System.out.println(String.format("description: %s, price: %s", beverage3.getDescription(), beverage3.cost()));
	}
}
