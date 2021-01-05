package rxjava2;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import org.junit.Test;
import util.Utils;


public class Test1_single {
    @Test
    public void test1() {
        Single.just("a").subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onSuccess(String s) {
                System.out.println(s);
            }

            @Override
            public void onError(Throwable e) {
            }
        });
    }

    @Test
    public void test2() {
        // 加入 map 操作符
        Single.just(115)                        // => SingleJust
                .map(it -> String.valueOf(it))  // => SingleMap
                // subscribe 先走到 SingleMap 的 subscribeActual 方法
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(String s) {
                        Utils.print(s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Test
    public void test3() {

    }
}
