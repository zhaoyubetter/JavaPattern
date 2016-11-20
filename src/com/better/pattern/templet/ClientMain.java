package com.better.pattern.templet;

/**
 * Created by zhaoyu on 2016/11/6.
 */

public class ClientMain {
	public static void main(String[] args) {
		Coffee coffee = new Coffee();
		Tea tea = new Tea();

		coffee.prepareRecipe();

		System.out.println("-----------------------------");

		tea.prepareRecipe();

	}
}
