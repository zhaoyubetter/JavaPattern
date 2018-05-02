package com.better.kotlin.array

fun main(args: Array<String>) {
    val array = arrayOf(1, 4, 3, 9, 0, -1, 20, 50, 3)
    println(array.min())

    println(array.foldIndexed(0) { index, acc, i ->
        return@foldIndexed if (i < array[acc]) index else acc
    })

    println(array.minIndex())
}

fun Array<Int>.minIndex(): Int? {
    if (isEmpty()) return null
    var min = 0
    for (i in 1..lastIndex) {
        if (this[min] > this[i]) min = i
    }
    return min
}