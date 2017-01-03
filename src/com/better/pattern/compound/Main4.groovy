package com.better.pattern.compound

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

/**
 * Created by zhaoyu on 2017/1/2.
 * 使用了抽象工厂
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


    println "Duck Simulator \n"

    simulate(mallardDuck)
    simulate(redHeadDuck)
    simulate(duckCall)
    simulate(rubberDuck)
    simulate(goose)

    println("\n")

    println "鸭叫次数：" + QuackCounter.getQuacks()

}


def simulate(a) {
    println a.quack()
}