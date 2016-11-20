package com.better.pattern.adapter.array;

import java.util.ArrayList;
import java.util.List;

/**
 * 让 list 执行 Enumeration接口
 * Created by zhaoyu on 2016/11/5.
 */

public class ClientTest2 {
	public static void main(String[] args) {

		List<String> lists = new ArrayList<>();
		lists.add("Java");
		lists.add("Android");
		lists.add("JavaScript");
		lists.add("Groovy");

		EnumIteratorAdapter enumIteratorAdapter = new EnumIteratorAdapter(lists.iterator());

		while (enumIteratorAdapter.hasMoreElements()) {
			System.out.println(enumIteratorAdapter.nextElement());
		}
	}
}
