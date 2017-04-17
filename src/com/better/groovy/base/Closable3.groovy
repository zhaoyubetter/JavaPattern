package com.better.groovy.base

/**
 * 闭包作为另一个闭包的参数
 * Created by zhaoyu on 2017/4/13.
 */

def takeWhile = { operator, list ->
    def result = []
    for (e in list) {
        if (operator(e)) {
            result << e
        } else {
            return result       // 只找部分
        }
    }
    return result
}

def isEven = { x -> return (x % 2 == 0) }
def isOdd = { x -> return !isEven(x) }

def table1 = [12, 14, 15, 18]
def table2 = [11, 13, 15, 16, 18, 19]

def evens = takeWhile.call(isEven, table1)
println("events: ${evens}")
def odds = takeWhile.call(isOdd, table2)
println("odds: ${odds}")

println('-----------------------------')

// 接受一个整数，并返回一个闭包，目的是：计算2个数的乘积
def multiply(x) {
    return { y -> return x * y }
}

def twice = multiply(2)  // 闭包：返回参数的2倍值
println("twice(4): ${twice(4)}")

// 接受整数，返回闭包
def multiplication = { x -> return { y -> return x * y } }
def quadruple = multiplication(4)       // 4X
println("quadrupe(3): ${quadruple(3)}")


println('==============================')

println('============= 嵌套闭包 =================')
// 选择排序
def selectionSort = { list ->
    // 交换闭包
    def swap = { sList, p, q ->
        def tmp = sList[p]
        sList[p] = sList[q]
        sList[q] = tmp
    }

    // 找最小的
    def minimumPosition = { pList, from ->
        def mPos = from
        def mNext = 1 + from
        for (j in mNext..<pList.size()) {
            if (pList[j] < pList[mPos]) {
                mPos = j
            }
        }
        return mPos
    }

    def size = list.size - 1
    for (k in 0..<size) {
        def minPos = minimumPosition(list, k)
        swap(list, minPos, k)
    }

    return list
}
tables = [13, 14, 12, 11, 19]
def sorted = selectionSort(tables)
println("sorted: ${sorted}")


println('==============================')
def lSubstract = { x -> return { y -> return x - y } }
def rSubstract = { y -> return { x -> return x - y } }

def p = lSubstract(100)
def q = rSubstract(1)

println("p(25): ${p(25)}")
println("q(9): ${q(9)}")

println('============= 不懂意思 =================')
def comp = { f, g -> return { x -> return f(g(x)) } }
def r = comp(p, q)
def s = comp(q, p)
println("r(10): ${r(10)}")
println("s(10): ${s(10)}")