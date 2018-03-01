package com.better.android.handler;

/**
 * Created by zhaoyu on 2017/3/7.
 */
public class Main {
    public static void main(String... args) throws InterruptedException {
        new Main().start();
    }

    private void start() throws InterruptedException {
        Looper.prepare();
        onCreate();

        System.out.println("main Thread: [" + Thread.currentThread().getName() + "]");

        Looper.loop();
    }

    private void onCreate() throws InterruptedException {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                System.out.println("current Thread: [" + Thread.currentThread().getName() + "]");
                System.out.println("message: " + msg);
            }
        };

        Thread.sleep(1000);

        new Thread(new Runnable() {
            @Override
            public void run() {

                Message message = new Message();
                message.what = 1;
                message.obj = "来自子线程消息1";
                message.target = handler;
                handler.sendMessage(message);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                message = new Message();
                message.what = 2;
                message.obj = "来自子线程消息2";
                message.target = handler;
                handler.sendMessage(message);

            }
        }).start();
    }
}
