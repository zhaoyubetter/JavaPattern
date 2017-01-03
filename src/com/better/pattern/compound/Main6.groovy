package com.better.pattern.compound

import com.better.pattern.compound.composite.Flock
import com.better.pattern.compound.counter_decorator.QuackCounter
import com.better.pattern.compound.duck.DuckCall
import com.better.pattern.compound.duck.MallardDuck
import com.better.pattern.compound.abs.Quackable
import com.better.pattern.compound.duck.RedHeadDuck
import com.better.pattern.compound.duck.RubberDuck
import com.better.pattern.compound.duck_factory.AbstractDuckFactory
import com.better.pattern.compound.duck_factory.CountingDuckFactory
import com.better.pattern.compound.goose_adapter.Goose
import com.better.pattern.compound.goose_adapter.GooseAdapter
import com.better.pattern.compound.goose_factory.AbstractGooseFactory
import com.better.pattern.compound.goose_factory.GooseFactory
import com.better.pattern.compound.observe.QuackLogList

/**
 * Created by zhaoyu on 2017/1/2.
 * 使用了抽象工厂 + 使用了组合模式 + 观察者模式
 */

test()

def test() {

    AbstractDuckFactory duckFactory = new CountingDuckFactory();
    AbstractGooseFactory gooseFactory = new GooseFactory();

    Quackable mallardDuck = duckFactory.createMallardDuck()
    Quackable redHeadDuck = duckFactory.createRedHeadDuck()
    Quackable duckCall = duckFactory.createDuckCall()
    Quackable rubberDuck = duckFactory.createRubberDuck()

    // 鹅鹅鹅
    Quackable goose = gooseFactory.createQuackGoose()


    println "Duck Simulator  \n"

    // 创建组
    Flock flockOfDucks = new Flock()
    flockOfDucks.add(redHeadDuck)
    flockOfDucks.add(duckCall)
    flockOfDucks.add(rubberDuck)
    flockOfDucks.add(goose)


    // 创建组2
    Flock flockOfMallards = new Flock()
    Quackable mallardDuck1 = duckFactory.createMallardDuck()
    Quackable mallardDuck2 = duckFactory.createMallardDuck()
    Quackable mallardDuck3 = duckFactory.createMallardDuck()
    Quackable mallardDuck4 = duckFactory.createMallardDuck()
    flockOfMallards.add(mallardDuck1)
    flockOfMallards.add(mallardDuck2)
    flockOfMallards.add(mallardDuck3)
    flockOfMallards.add(mallardDuck4)

    // 组合
    flockOfDucks.add(flockOfMallards)

    println "全部鸭子 \n"
    simulate(flockOfDucks)

    println "测试其他鸭子 \n"
    simulate(flockOfMallards)

    println("\n")

    println "鸭叫次数：" + QuackCounter.getQuacks()


    println("\nwith Observer\n")
    QuackLogList log = new QuackLogList()
    redHeadDuck.registerObserver(log)
    simulate(redHeadDuck)

    println("\n监听一群试试\n")
    flockOfMallards.registerObserver(log)
    simulate(flockOfMallards)
}


def simulate(a) {
    println a.quack()
}