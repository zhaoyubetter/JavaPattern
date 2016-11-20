package com.better.pattern.composite;

import com.better.pattern.composite.abs.MenuComponent;
import com.better.pattern.composite.common.Menu;
import com.better.pattern.composite.common.MenuItem;
import com.better.pattern.composite.iterator.CompositeIterator;

/**
 * 组合迭代器
 * Created by zhaoyu on 2016/11/12.
 */

public class ClientMain2 {
	public static void main(String[] args) {
		// 创建3个菜谱
		MenuComponent houseMenu = new Menu("HOUSE MENU 家庭菜谱", "BREAKFAST 早餐");
		MenuComponent dinnerMenu = new Menu("DINNER MENU 晚餐菜谱", "DINNER 晚餐");
		MenuComponent cafeMenu = new Menu("Cafe 菜谱", "Cafe");

		// 总菜谱
		MenuComponent allMenus = new Menu("All Menus 所有菜谱", "总菜谱");

		// 2大菜谱
		allMenus.add(houseMenu);
		allMenus.add(dinnerMenu);

		// 菜谱分别加入菜单项
		houseMenu.add(new MenuItem("红烧肉", "毛式红烧肉", false, 38));
		houseMenu.add(new MenuItem("红烧鱼", "红烧大草<。)#)))≦", false, 55));
		houseMenu.add(new MenuItem("清炒空心菜", "可选择是否放大蒜", true, 12));
		houseMenu.add(new MenuItem("酸辣土豆丝", "有酸有辣", true, 10));

		cafeMenu.add(new MenuItem("摩卡 coffee", "摩卡", false, 10));
		cafeMenu.add(new MenuItem("豆浆 甜品", "纯豆浆哦", false, 15));
		cafeMenu.add(new MenuItem("小蛋糕", "蛋糕", false, 11));

		// 3菜谱
		houseMenu.add(cafeMenu);    // 添加cafe甜品菜谱

		dinnerMenu.add(new MenuItem("炒鸡肉", "土鸡呢", false, 50));
		dinnerMenu.add(new MenuItem("跳跳蛙", "肚子有点饿了", false, 48));
		dinnerMenu.add(new MenuItem("小葱豆腐", "小时候的味道", true, 12));
		dinnerMenu.add(new MenuItem("炒白菜", "正宗大火炒", true, 15));

		// 创建组合迭代器对象
		CompositeIterator iterator = new CompositeIterator(allMenus.createIterator());
		while (iterator.hasNext()) {
			MenuComponent component = iterator.next();
			component.print();
			//System.out.println(component);
		}
	}
}
