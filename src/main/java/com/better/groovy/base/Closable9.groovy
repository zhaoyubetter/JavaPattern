package com.better.groovy.base

/**
 * 闭包打包
 * Created by zhaoyu on 2017/4/28.
 */
abstract class Functor {
    // 加法
    public static Closure bAdd = { x, y -> return x + y }
    public static Closure rAdd = { y, x -> return x + y }
    public static Closure lAdd = { x, y -> return x + y }

    // 减法
    public static Closure bSubtract = { x, y -> return x - y }
    public static Closure lSubtract = { x, y -> return x - y }
    public static Closure rSubStract = { y, x -> return x - y }

    public static Closure bMultiply = { x, y -> return x * y }
    public static Closure rMultiply = { y, x -> return x * y }
    public static Closure lMultiply = { x, y -> return x * y }

    public static Closure bDivide = { x, y -> return x / y }
    public static Closure rDivide = { y, x -> return x / y }
    public static Closure lDivide = { x, y -> return x / y }

    public static Closure bMod = { x, y -> return x % y }
    public static Closure rMod = { y, x -> return x % y }
    public static Closure lMod = { x, y -> return x % y }

    public static Closure bMin = { x, y -> return (x < y) ? x : y }
    public static Closure bMax = { x, y -> return (x < y) ? y : x }

    // 组合
    public static Closure composition = { f, g, x -> return f(g(x)) }
    public static Closure bComposition = { h, f, g, x -> return h(f(x), g(x)) }

    public static Closure map = { action, list -> return list.collect(action) }

    public static Closure filter = { predicate, list -> return list.findAll(predicate) }
}

println(Functor.bAdd(2, 3))
def size = { text -> return text.length() }
def list = Functor.map(size, ['aaa', 'bbbbb', 'ccccc'])
println(list)

def isSize3 = { text -> return text.length() > 3 }
def words = ["better", 'hello', '1']
println(Functor.filter(isSize3, words))


