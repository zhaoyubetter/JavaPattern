package com.better.suanfa;

import com.better.Utils;

/**
 * 插入排序，类似 扑克牌，摸牌后，插入的过程
 * <p>
 * 见于：新的数据，插入到已排序好的数组中的
 * <p>
 * <p>
 * Created by zhaoyu on 16/1/11.
 */
public class InsertionSort {
	public static void main(String[] args) {
		int[] array = {4, 1, 5, 3, 2, 9, 20, 100, -1};

//		sort(a);
//		sort2(a);

		sort3(array);
		for (int m = 0; m < array.length; m++) {
			Utils.println(array[m]);
		}
	}

	public static void sort(int[] a) {
		int i = 1;
		while (i < a.length) {
			int key = a[i];        // 插入key
			int j = i - 1;        // 已拍好序，元素的个数

			// 从后到前循环，将大于key的数向后移动一位
			for (; j >= 0 && key < a[j]; j--) {
				a[j + 1] = a[j];
			}
			a[j + 1] = key;    // 上面减1了

			i++;
		}


		for (int m = 0; m < a.length; m++) {
			Utils.println(a[m]);
		}

	}

	public static void sort2(int[] a) {
		int i = 1;
		while (i < a.length) {
			int key = a[i];
			int j = i - 1;
			for (; j >= 0 && a[j] > key; j--) {
				a[j + 1] = a[j];
			}
			a[j + 1] = key;

			i++;
		}
	}

	/**
	 * 插入排序
	 */
	public static void sort3(int[] a) {
		// 类似于手中的扑克牌，假设是排序好的，每摸一张，我们得找个位置插入进去

		// 1.从下标1开始，遍历整个数组（桌上的牌），下标为0的为已排序好的 （手中的牌）
		// 2.不断获取数组中的数（类似于摸牌）用变量 key 记录其值，表示待插入的数据（摸到的牌）
		// 3.从后往前，遍历已排序好的数组（遍历手中的牌），找到要插入的位置，并插入
		// 4.重复上面的步骤 下标++

		int length = a.length;        // 桌上牌的个人
		int i = 1;                    // 初始化手中排序好的牌的个数
		for (; i < length; i++) {      // 不断摸牌
			int key = a[i];              // 摸到的牌

			int j = i - 1;            // 手中的牌，遍历的下标

			// 从后往前遍历手中的牌,将手中的牌与 摸到的牌，对比
			while (j >= 0 && a[j] > key) {
				a[j + 1] = a[j];    // 元素向后移动1格
				j--;
			}

			// 插入
			a[j + 1] = key;
		}
	}


}
