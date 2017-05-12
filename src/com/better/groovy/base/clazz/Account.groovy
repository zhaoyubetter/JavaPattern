package com.better.groovy.base.clazz

/**
 * Created by zhaoyu on 2017/4/17.
 */
class Account {
    def number
    def balance

    public static void main(String[] args) {
        def acc = new Account(number: '123', balance: 12000)
        println("Account ${acc.number}, balance ${acc.balance}")
        println("Account ${acc.getNumber()}, balance ${acc.getBalance()}")
    }
}

