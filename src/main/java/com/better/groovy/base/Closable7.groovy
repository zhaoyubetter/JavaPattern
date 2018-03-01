package com.better.groovy.base

/**
 * 计算模式
 * Created by zhaoyu on 2017/4/28.
 */

// map closure
// 返回操作列表的闭包
def map = { clos, list -> return list.collect(clos) }

def multiply = { x, y -> return x * y }
def triple = multiply.curry(3)
def quadruple = multiply.curry(4)

def tripleAll = map.curry(triple)
def table = tripleAll([1, 2, 3, 4])

println("table: ${table}")

def quadrupleAll = map.curry(quadruple)
def table2 = quadrupleAll([1, 2, 3, 4])
println("table2: ${table2}")


println('=======================')
// composition closure
def composition = { f, g, x -> return f(g(x)) }
def composeMapMap = composition.curry(map.curry(triple), map.curry(quadruple))
def tableComposeMapMap = composeMapMap([1, 2, 3, 4])
println("tableComposeMapMap : ${tableComposeMapMap}")

def mapCompose = map.curry(composition.curry(triple, quadruple))
def tableMapComose = mapCompose([1, 2, 3, 4])
println("tableMapCompose: ${tableMapComose}")