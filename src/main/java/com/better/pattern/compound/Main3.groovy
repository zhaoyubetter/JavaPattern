package com.better.pattern.compound

import com.better.pattern.compound.counter_decorator.QuackCounter
import com.better.pattern.compound.duck.DuckCall
import com.better.pattern.compound.duck.MallardDuck
import com.better.pattern.compound.abs.Quackable
import com.better.pattern.compound.duck.RedHeadDuck
import com.better.pattern.compound.duck.RubberDuck
import com.better.pattern.compound.goose_adapter.Goose
import com.better.pattern.compound.goose_adapter.GooseAdapter

/**
 * Created by zhaoyu on 2017/1/2.
 * 统计鸭叫声次数
 */

test()

def test() {

    Quackable mallardDuck = new QuackCounter(new MallardDuck())
    Quackable redHeadDuck = new QuackCounter(new RedHeadDuck())
    Quackable duckCall = new QuackCounter(new DuckCall())
    Quackable rubberDuck = new QuackCounter(new RubberDuck())

    // 鹅鹅鹅
    Quackable goose = new GooseAdapter(new Goose())


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