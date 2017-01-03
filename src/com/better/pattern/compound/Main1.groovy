package com.better.pattern.compound

import com.better.pattern.compound.duck.DuckCall
import com.better.pattern.compound.duck.MallardDuck
import com.better.pattern.compound.abs.Quackable
import com.better.pattern.compound.duck.RedHeadDuck
import com.better.pattern.compound.duck.RubberDuck

/**
 * Created by zhaoyu on 2017/1/2.
 */

test()

def test() {

    Quackable mallardDuck = new MallardDuck()
    Quackable redHeadDuck = new RedHeadDuck()
    Quackable duckCall = new DuckCall()
    Quackable rubberDuck = new RubberDuck()

    println "Duck Simulator \n"

    simulate(mallardDuck)
    simulate(redHeadDuck)
    simulate(duckCall)
    simulate(rubberDuck)

}


def simulate(a) {
    println a.quack()
}