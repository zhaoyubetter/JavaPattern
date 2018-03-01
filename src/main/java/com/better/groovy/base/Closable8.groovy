package com.better.groovy.base

/**
 * 例子
 * Created by zhaoyu on 2017/4/28.
 */
class Book {
    def name
    def author
    def price
    def category
}

def bk = new Book(name: 'groovy设计', 'author': 'Json', 'price': 25, category: 'java')
def discountRate = 0.1      // 折扣
def taxRate = 0.17          // 税率

def rMultiply = { y, x -> return x * y }
def lMultiply = { x, y -> return x * y }
def composition = { f, g, x -> return f(g(x)) }

// 折扣价格
def calcDiscountedPrice = rMultiply.curry(1 - discountRate)
// 含税价
def calcTax = rMultiply.curry(1 + taxRate)
// 实际价格
def calcNetPrice = composition.curry(calcTax, calcDiscountedPrice)

def netPrice = calcNetPrice(bk.price)
println("netPrice: ${netPrice}")

println('-------------- 有上限的折扣 -----------------')

def maxDiscount = 3     // 最大折扣
def lsubtract = { x, y -> return x - y }
def rSubStract = { y, x -> return x - y }

def min = { x, y -> return (x < y) ? x : y }
def id = { x -> return x }

def bComposition = { h, f, g, x -> return h(f(x), g(x)) }
// 计算折扣
def calcDiscount = rMultiply.curry(discountRate)
// 实际折扣
def calcActualDiscount = bComposition.curry(min, calcDiscount, id)
// 计算折扣后的金额
calcDiscountedPrice = bComposition.curry(lsubtract, id, calcDiscount)
// 含税
calcTax = rMultiply.curry(1 + taxRate)



calcNetPrice = composition.curry(calcTax, calcDiscountedPrice)
println("bk.price : ${bk.price}")
netPrice = calcNetPrice(bk.price)
println("netPrice:${netPrice}")