package com.better.pattern.iterator;

import com.better.pattern.iterator.common.MenuItem;
import com.better.pattern.iterator.iter.Menu;

/**
 * Created by zhaoyu on 2016/11/9.
 */

public class Waitress {
	Menu listMenu;
	Menu arrayMenu;

	public Waitress(Menu listMenu, Menu arrayMenu) {
		this.listMenu = listMenu;
		this.arrayMenu = arrayMenu;
	}

	public void printMenu() {
		final Iterator<MenuItem> iterator1 = listMenu.createIterator();
		final Iterator<MenuItem> iterator2 = arrayMenu.createIterator();

		System.out.println("MENU------- 午饭菜单 ------- ");
		printMenu(iterator1);
		System.out.println("MENU------- 晚饭菜单 ------- ");
		printMenu(iterator2);
	}

	private void printMenu(Iterator<MenuItem> iterator) {
		while (iterator.haxNext()) {
			MenuItem menuItem = iterator.next();
			System.out.println(menuItem.getName() + ", " + menuItem.getPrice() + " ---- " +
					menuItem.getDesc());
		}
	}
}
