package com.better.pattern.composite;

import com.better.pattern.composite.abs.MenuComponent;

import java.util.Iterator;

/**
 * Created by zhaoyu on 2016/11/12.
 */

public class Waitress {
	MenuComponent allMenus;

	public Waitress(MenuComponent allMenus) {
		this.allMenus = allMenus;
	}

	public void printMenu() {
		allMenus.print();
	}

	public void printVegetarianMenu() {
		Iterator<MenuComponent> iterator = allMenus.createIterator();
		System.out.println("======---- 所有蔬菜 item");
		while (iterator.hasNext()) {
			MenuComponent componet = iterator.next();

			try {
				if (componet.isVegetarian()) {
					componet.print();
				}
			} catch (UnsupportedOperationException e) {
			}
		}
	}
}
