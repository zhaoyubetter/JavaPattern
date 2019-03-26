package com.better;

/**
 * Created by zhaoyu on 16/1/11.
 */
public final class Utils {
	public static void println
			(Object obj) {
		if (null == obj) {
			System.out.println("空指针");
			return;
		}
		System.out.println(obj.toString());
	}

	public static void print(Object obj) {
		System.out.print(obj.toString());
	}

	public static void printArray(int[] a) {
		if (a != null) {
			for (int i : a) {
				System.out.print(i + "\t");
			}

			System.out.println();
		}

	}
}
