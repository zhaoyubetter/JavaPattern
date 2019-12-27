package vip.sonar.suanfa.sort.two

import org.junit.Test

class SortTest {

    val a = arrayOf(9, 1, 2, 4, 5, 8, 3, 0, 5, 7, 6, 10, 11)

    @Test
    fun test1() {
        fun bubbleSort(arr: Array<Int>) {
            for (i in (0 until arr.size)) {
                var flag = false
                for (j in (0 until arr.size - i - 1)) {
                    if (arr[j] > arr[j + 1]) {
                        swap(arr, j, j + 1)
                        flag = true // 有交换
                    }
                }
                if (!flag) {
                    break
                }
                println(arr.joinToString())
            }
        }
        bubbleSort(a)
        println(a.joinToString())
    }

    @Test
    fun test2() {
        fun insertSort(array: Array<Int>) {
            for (i in (1 until array.size)) {
                val value = array[i]
                var j = i - 1
                while (j >= 0 && value < array[j]) {
                    array[j + 1] = array[j] // 移动
                    j--
                }
                array[j + 1] = value  // 插入数据
                println(a.joinToString())
            }
        }
        insertSort(a)
        println(a.joinToString())
    }

    @Test
    fun test3() {
        fun selectSort(array: Array<Int>) {
            for (i in (0 until array.size)) {
                var minIndex = i
                for (j in (i until array.size)) {
                    if(a[minIndex] > array[j]) {
                        minIndex = j
                    }
                }
                swap(array, minIndex, i )
            }
        }
        selectSort(a)
        println(a.joinToString())
    }

    fun swap(array: Array<Int>, i: Int, j: Int) {
        val tmp = array[i]
        array[i] = array[j]
        array[j] = tmp
    }
}