package com.better.pattern.composite.iterator;

import com.better.pattern.composite.abs.MenuComponent;
import com.better.pattern.composite.common.Menu;

import java.util.Iterator;
import java.util.Stack;

/**
 * 组合迭代器
 * 并不好用的，难维护
 * Created by zhaoyu on 2016/11/12.
 */
public class CompositeIterator implements Iterator<MenuComponent> {

	Stack<Iterator<MenuComponent>> stack = new Stack<>();

	public CompositeIterator(Iterator<MenuComponent> iterator) {
		this.stack.push(iterator);
	}

	@Override
	public boolean hasNext() {
		if (stack.isEmpty()) {
			return false;
		}

		// 从栈顶取出迭代器，并判断该迭代器是否还有下一个元素，如果没有元素，弹出栈，并递归判断
		final Iterator<MenuComponent> iterator = stack.peek();
		if (!iterator.hasNext()) {
			stack.pop();
			return hasNext();
		} else {
			return true;
		}
	}

	@Override
	public MenuComponent next() {
		if (hasNext()) {
			final MenuComponent component = stack.peek().next();
			if(component instanceof Menu) {
				stack.push(component.createIterator());
			}
			return component;
		}

		return null;
	}

}
