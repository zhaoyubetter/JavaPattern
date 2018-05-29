package com.better.concurrency.part_2_base;

import com.better.Utils;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 模拟游戏准备
 */
public class Test7_CountDownLatch_2 {

    public static void main(String... aa) throws InterruptedException {
        Game game = new Game();
        game.startGame(new Player("Better"), new Player("小虎叔叔"), new Player("liyuzero")
        , new Player("古月峰"));
    }

    /**
     * 主游戏
     */
    static class Game {
        void startGame(final Player... players) throws InterruptedException {
            final CountDownLatch startGate = new CountDownLatch(1);
            final CountDownLatch endGate = new CountDownLatch(players.length);

            for (Player player : players) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            startGate.await();
                            player.run();
                        } catch (InterruptedException e) {
                        } finally {
                            endGate.countDown();
                        }
                    }
                }.start();
            }

            Utils.println("current Thread: " + Thread.currentThread().getName() + ", 王者游戏开始了，请准备");
            startGate.countDown();
            if(endGate.await(10, TimeUnit.SECONDS)) {
                Utils.println("current Thread: " + Thread.currentThread().getName() + ", 匹配成功，enjoy!");
            } else {
                Utils.println("current Thread: " + Thread.currentThread().getName() + ", 有玩家未准备，游戏启动失败");
                System.exit(0);
            }
        }
    }

    /**
     * 游戏玩家
     */
    static class Player implements Runnable {

        private String name;

        public Player(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            final Random random = new Random();
            int prepparaTime = random.nextInt(30);
            try {
                Utils.println("current Thread: " + Thread.currentThread().getName() + ", 玩家: 【" + name + "】, 准备中...");
                Thread.sleep(prepparaTime * 1000);
                Utils.println("current Thread: " + Thread.currentThread().getName() + ", 玩家: 【" + name + "】, 准备完成，花费: " + prepparaTime + "s");
            } catch (InterruptedException e) {
            } finally {
            }
        }
    }
}
