package com.better.pattern.compound

import com.better.pattern.compound.duck.DuckCall
import com.better.pattern.compound.duck.MallardDuck
import com.better.pattern.compound.abs.Quackable
import com.better.pattern.compound.duck.RedHeadDuck
import com.better.pattern.compound.duck.RubberDuck
import com.better.pattern.compound.goose_adapter.Goose
import com.better.pattern.compound.goose_adapter.GooseAdapter

/**
 * Created by zhaoyu on 2017/1/2.
 * 添加了鹅
 */

test()

def test() {

    Quackable mallardDuck = new MallardDuck()
    Quackable redHeadDuck = new RedHeadDuck()
    Quackable duckCall = new DuckCall()
    Quackable rubberDuck = new RubberDuck()

    // 鹅鹅鹅
    Quackable goose = new GooseAdapter(new Goose())


    println "Duck Simulator \n"

    simulate(mallardDuck)
    simulate(redHeadDuck)
    simulate(duckCall)
    simulate(rubberDuck)
    simulate(goose)

}


def simulate(a) {
    println a.quack()
}