package com.better.pattern.two.use_composition;

import org.junit.Test;

/**
 * 多用组合少用继承
 */
public class Composition1 {

    /**
     * 每种鸟，实现内容都要重复一遍，重复内容太多
     * Q: 如果鸟类过多，如何避免重复代码呢
     */
    static class Sparrow implements Flyable, Tweetable, Egglayable {

        @Override
        public void layEgg() {

        }

        @Override
        public void fly() {

        }

        @Override
        public void tweet() {

        }
    }

    /**
     * 使用组合
     */
    static class Sparrow2 implements Flyable, Tweetable, Egglayable {

        // 组合
        private TweetAbility tweetAbility = new TweetAbility();
        private EgglayAbility egglayAbility = new EgglayAbility();

        @Override
        public void fly() {

        }

        @Override
        public void layEgg() {
            egglayAbility.layEgg();
        }

        @Override
        public void tweet() {
            tweetAbility.tweet();
        }
    }

    /**
     * A: 那就是每个接口做一个实现，然后进行组合
     */

    static class Flyability implements Flyable {
        @Override
        public void fly() {

        }
    }

    static class TweetAbility implements Tweetable {

        @Override
        public void tweet() {

        }
    }

    static class EgglayAbility implements Egglayable {

        @Override
        public void layEgg() {

        }
    }

    @Test
    public void test1() {

    }
}
