package com.better.pattern.iterator;

import com.better.pattern.iterator.common.ArrayListMenu;
import com.better.pattern.iterator.common.ArrayMenu;

/**
 * Created by zhaoyu on 2016/11/9.
 */

public class ClientMain {
	public static void main(String[] args) {
		ArrayListMenu aMenu = new ArrayListMenu();
		ArrayMenu bMenu = new ArrayMenu();

		Waitress waitress = new Waitress(aMenu, bMenu);
		waitress.printMenu();
	}
}
