package com.better.concurrency.part_1_safe;

import com.better.Utils;

/**
 * 逸出，不该发布的内部对象
 */
public class Test7_unsafeStates {
    private String[] states = {"AK", "unkown"};

    // states变量全部对外发布了
    public String[] getStates() {
        return this.states;
    }


    interface EventListener {
        void doEvent();
    }

    static class EventSource {
        public void registerListener(EventListener listener) {
            listener.doEvent();
        }
    }

    // 隐士this引用 逸出，构造中 this 引用逸出；
    static class ThisEscape {
        public ThisEscape(EventSource listener) {
            // EventListener 为内部类，含有对外部类的引用
            listener.registerListener(new EventListener() {
                @Override
                public void doEvent() {
                    doSome();
                }
            });
        }

        public void doSome() {
            Utils.println("doSome");
        }
    }

    // 使用工厂方法防止this引用在构造中逸出
    static class SafeListener {

        private final EventListener listener;

        private SafeListener() {
            listener = new EventListener() {
                @Override
                public void doEvent() {

                }
            };
        }


        public static SafeListener newInstance(EventSource my) {
            SafeListener safe = new SafeListener();
            my.registerListener(safe.listener);
            return safe;
        }
    }
}

