package com.better.suanfa;

import com.better.Utils;

/**
 * 堆排序
 * 左子树索引：2n+1
 * 游子树索引：2n+2
 * 堆的存储表示是顺序的。
 * 当想得到一个序列中第k个最小的元素之前的部分排序序列，最好采用堆排序。
 * Created by zhaoyu on 16/9/16.
 */
public class HeapSort {
	public static void main(String[] args) {
		int[] a = {9, 5, 2, 1, 7, 4, 5, 8, 3};
		System.out.print("排序前： \t\t");
		Utils.printArray(a);

		heapSort(a);
	}

	public static void heapSort(int a[]) {
		//1. 首先，获取该数组的 初始化为大根堆 （父节点，比所有子节点都大）
		//2. 将第一个元素与最后一个元素进行交换
		//3. 去除最后一个元素，并重新调整为大根堆
		//4. 重复2，3，直到最后一个元素输出

		// 从最后一个父节点(length/2)，开始，循环来创建 大根树
		int length = a.length;
		for (int i = length / 2; i >= 0; i--) {
			buildMaxHeap(a, i, length - 1);
		}

		// 打印一下
		Utils.print("初始化堆：\t");
		Utils.printArray(a);

		// 进行 n-1次循环，完成排序，（将不断省略最后一个数）
		for (int i = length - 1; i > 0; i--) {
			// 交换第一个元素与最后一个元素
			int max = a[i];
			a[i] = a[0];
			a[0] = max;

			// 调整，获取i-1个节点的堆
			buildMaxHeap(a, 0, i);

			System.out.format("第%d趟：\t", length - i);
			Utils.printArray(a);
		}
	}

	/**
	 * 为数组a，父节点parent，创建大根堆
	 *
	 * @param a      数组
	 * @param parent 父节点索引
	 * @param length 堆的大小
	 */
	public static void buildMaxHeap(int a[], int parent, int length) {
		int currentParent = parent;                // 当前父节点索引
		int parentValue = a[currentParent];        // 父节点值
		int child = currentParent * 2 + 1;            // 左树索引

		// 如果有子树，就不断遍历
		while (child < length) {
			// 如果右树值 > 左树值,child 赋值为右索引
			if (child + 1 < length && a[child + 1] > a[child]) {
				child++;
			}

			// 如果当前父节点值 >= 子树值，表示，不需要查找下去了，退出循环
			if (parentValue >= a[child]) {
				break;
			}

			// 否则，将child值赋给 currentParent
			a[currentParent] = a[child];

			// 重新赋值 currentParent 与 child
			currentParent = child;
			child = child * 2 + 1;        // 下一个左树
		}

		// 如果currentParent，被子树索引重新赋值了,将原始parentValue，赋值给currenParent
		if (currentParent != parent) {
			a[currentParent] = parentValue;
		}
	}

	/*

	public static void heapSort(int a[]) {
		// 循环建立初始化堆（大根堆)，从最后一个节点的父节点(length/2)开始建立,
		// 知道 父节点 为 0 止
		// length -1，可减少一次判断
		int length = a.length;
		for (int i = length / 2; i >= 0; i--) {
			buildMaxHeap(a, i, length - 1);
		}

		Utils.print("初始化大根堆：\t");
		Utils.printArray(a);

		// 进行 n-1次循环，完成排序
		for (int i = length - 1; i > 0; i--) {
			// 最后一个元素与第一个元素进行交换
			int temp = a[i];
			a[i] = a[0];
			a[0] = temp;

			// 筛选R[0]节点,得到 i-1个节点的 大根堆，即最大值移动到 顶
			buildMaxHeap(a, 0, i);

			System.out.format("第 %d 趟：\t", length - i);
			Utils.printArray(a);
		}
	}*/

	/**
	 * 创建 基于 parent 父节点 的大根树
	 *
	 * @param a      数组
	 * @param parent 父节点索引
	 * @param length
	 */
	/*
	public static void buildMaxHeap(int a[], int parent, int length) {
		int currentParent = parent;        // 当前程序段中的parent
		int temp = a[currentParent];       // temp 保存当前父节点值，如果对比小，将一直下沉该值
		int child = 2 * currentParent + 1; // 左子树孩子

		// 当前节点有有孩子，就不断向下遍历
		while (child < length) {
			// 判断右孩子，如果有右孩子，并且右 大于 左，则选取右孩子节点
			if (child + 1 < length && a[child] < a[child + 1]) {
				child++;
			}

			// 父节点大于孩子节点的值，退出循环
			if (temp >= a[child]) {
				break;
			}

			// 孩子节点值，赋给父节点
			a[currentParent] = a[child];

			// 父节点下移，并选取孩子节点的左孩子节点，继续向下筛选
			currentParent = child; // 改变 parent
			child = 2 * child + 1;
		}

		// 查找结束，将原始父节点的值，写入到查找到的新节点位置
		if (currentParent != parent) {    // currentParent 没有改变，就不进行写操作，这样判断，可减少写的次数
			a[currentParent] = temp;
		}
	}

	*/
}
