package com.better.suanfa.middle_search;

/**
 * 二分法
 * Created by zhaoyu on 2016/11/13.
 */

public class TestMiddleSearch {
	public static void main(String[] args) {
		int a[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

		if (middleSearch(a, 1, 0, a.length) != -1) {
			System.out.println("找到了");
		} else {
			System.out.println("没找到");
		}

	}

	private static int middleSearch(int a[], int key) {
		int result = -1;

		// 初始化
		int bot = 0;
		int top = a.length;		// not a.length - 1;

		while (bot <= top) {
			int mid = (bot + top) >>> 1;
			System.out.format("bot:%s, mid:%s, top:%s, a[mid]:%s, key:%s\n", bot, mid, top, a[mid], key);
			if (a[mid] == key) {
				return mid;
			} else if (key < mid) {    // 省去后半部分
				top = mid - 1;
			} else {                   // 省去前半部分
				bot = mid + 1;
			}
		}
		return result;
	}

	/**
	 * 递归方式
	 * @return
	 */
	private static int middleSearch(int a[], int key, int low, int high) {
		if(low > high) {
			return -1;
		}



		int mid = (low + high) >>> 1;
		if(key > mid) {
			low = mid + 1;
		} else if(key < mid) {
			high = mid - 1;
		} else {
			return mid;
		}

		return middleSearch(a, key, low, high);
	}
}
