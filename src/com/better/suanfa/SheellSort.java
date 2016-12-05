package com.better.suanfa;

/**
 * 希尔排序
 * 对插入排序的补充,分组进行插入排序
 * 不稳定的排序算法，
 * 最后走的还是 原始的插入排序，不过此时的序列，差不多已排序的差不多了。所以，比较，交换次数就少。
 * 组 采用 插入排序 与 希尔排序，来说，用希尔速度
 * 随着待排序数组长度的增大，而更快；
 *
 * @see InsertionSort
 * Created by zhaoyu on 16/9/14.
 */
public class SheellSort {

	public static void main(String[] args) {
		int[] array = {
				10, 5, 3, -1, 88, 0, 2, 100, 22, 89,-9,78,34
		};
		sheellSort(array);

	}

	public static void sheellSort(int[] list) {
		// 相比插入排序，希尔排序引入了步长与分组
		// 相邻步长长度的数，形成分组组，并分组进行排序
		// 当步长等于1时，排序整个数组

		System.out.print("排序前\t");
		printAll(list);

		int length = list.length;
		int gap = length / 2;        // 初始化步长为数组长度的一半
		while (gap >= 1) {
			for (int i = gap; i < length; i++) {    // 步长为gap的编为1组，进行插入排序（分组并进行插入排序）
				int key = list[i];        // 摸牌
				int j = i - gap;          // 手中的牌，遍历的下标

				for (; j >= 0 && list[j] > key; j = j - gap) {    // 从后往前遍历，步长为gap，排序手中的牌
					list[j + gap] = list[j];
				}
				list[j + gap] = key;    // 插入摸到的牌
			}

			System.out.format("gap=%d\t", gap);
			printAll(list);
			gap = gap / 2;             // gap继续缩小，也就是进行下一次分组
		}
		System.out.print("排序后\t");
		printAll(list);
	}

	public static void printAll(int[] list) {
		for (int value : list) {
			System.out.print(value + "\t");
		}
		System.out.println();
	}


}
