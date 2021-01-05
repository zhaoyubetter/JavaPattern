package rxjava2;

import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;
import util.Utils;

public class Test2_observable {

    @Test
    public void test1() {
        Observable.just(1, 2)
                .observeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Integer integer) {
                        // IO 线程
                        System.out.println(Thread.currentThread().getName() + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Test
    public void test2() throws InterruptedException {
        Observable.just(1, 2,3,4,5)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println(Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        // IO 线程
                        Utils.println("hhhh");
                        System.out.println(Thread.currentThread().getName() + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Utils.println(e);
                    }

                    @Override
                    public void onComplete() {
                        Utils.println("onComplete");
                    }
                });

        // 等待
        Thread.sleep(2000);
    }

    @Test
    public void test3() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) {
                // IO 线程
                Utils.println("subscribe() --> 所在线程为 " + Thread.currentThread().getName());
                emitter.onNext(1);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.trampoline())       // 多次调用无效
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // main
                        System.out.println(Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        // IO 线程
                        System.out.println("onNext() ==> " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Test
    public void test4() {
        final Printer printer1 = new Printer(null, "printer1");
        final Printer printer2 = new Printer(printer1, "printer2");
        final Printer printer3 = new Printer(printer2, "printer3");
        final Printer printer4 = new Printer(printer3, "printer4");
        printer4.print();

        //////////
        Utils.println("====+++++=====");
        new Thread(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // thread3 也就是第一个 new 的 Thread
                                System.out.println(Thread.currentThread().getName());
                            }
                        }, "thread3").start();
                    }
                }, "thread2").start();
            }
        }, "thread1").start();
    }

    private static class Printer {
        Printer source;
        String name;

        Printer(Printer source, String name) {
            this.name = name;
            this.source = source;

        }

        void print() {
            // 执行在最后的线程中
            if (source == null) {
                System.out.println("-->" + name + "-" + Thread.currentThread().getName());
                return;
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(name + "-" + Thread.currentThread().getName());
                    if (null != source) {
                        source.print();
                    }
                }
            }).start();
        }
    }
}
