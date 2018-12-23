package vip.sonar.math

import java.util.*

fun main(args: Array<String>) {
    merge_sort(arrayOf(3, 1, 6, 8, 9, 10, 5, -1, 7,0)).forEach { print("$it,") }
}

// 归并
private fun merge_sort(array: Array<Int>): Array<Int> {

    if (array.size == 1) {
        return array
    }

    val mid = array.size / 2

    var left = Arrays.copyOfRange(array, 0, mid)
    var right = Arrays.copyOfRange(array, mid, array.size)

    left = merge_sort(left)
    right = merge_sort(right)

    return merge(left, right)
}

private fun merge(left: Array<Int>, right: Array<Int>): Array<Int> {
    var result = Array(left.size + right.size) { 0 }

    var index = 0
    var l = 0
    var r = 0

    while (l < left.size && r < right.size) {
        if (left[l] < right[r]) {
            result[index] = left[l]
            l++
        } else {
            result[index] = right[r]
            r++
        }
        index++
    }

    while (l < left.size) {
        result[index++] = left[l++]
    }

    while (r < right.size) {
        result[index++] = right[r++]
    }

    return result
}