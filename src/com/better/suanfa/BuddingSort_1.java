package com.better.suanfa;

import com.better.Utils;

/**
 * 优化冒泡排序
 * Created by zhaoyu on 16/10/12.
 */
public class BuddingSort_1 {
	public static void main(String[] args) {
		int a[] = {1,5,9,20,0,-3,8,10,56,33,22,30};
		boolean swap = true;
		for (int i = 0; i < a.length - 1; i++) {
			swap = true;    // 每次还原成 false
			for (int j = a.length - 1; j > i; j--) {
				if(a[j] < a[j - 1]) {
					int tmp = a[j];
					a[j] = a[j-1];
					a[j-1] = tmp;
					swap = true;
				}
			}

			Utils.printArray(a);

			// 没有交换,直接结束,退出循环
			if(!swap) {
				break;
			}
		}
	}
}
