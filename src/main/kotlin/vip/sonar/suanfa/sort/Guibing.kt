package vip.sonar.suanfa.sort


/**
 * @description: bubble
 * @author better
 * @date 2019-03-08 21:45
 */
fun main() {

    fun merge(a: Array<Int>, low: Int, mid: Int, high: Int) {
        val tmp = Array(high - low + 1) { 0 }
        var index = 0
        var i = low
        var j = mid + 1
        while (i <= mid && j <= high) {
            if (a[i] > a[j]) {
                tmp[index] = a[j]
                j++
            } else {
                tmp[index] = a[i]
                i++
            }
            index++
        }
        while (i <= mid) {
            tmp[index++] = a[i++]
        }
        while (j <= high) {
            tmp[index++] = a[j++]
        }

        index = 0
        for (i in (low..high)) {
            a[i] = tmp[index++]
        }
    }

    fun mergeSort(array: Array<Int>, low: Int, high: Int) {
        if (low >= high) {
            return
        }
        val mid = low + (high - low) / 2
        mergeSort(array, low, mid)
        mergeSort(array, mid + 1, high)
        merge(array, low, mid, high)
    }

    val array = arrayOf(1, 5, 4, 2, 0, 3, -9)
    mergeSort(array, 0, array.size - 1)
    println(array.joinToString(" "))

}