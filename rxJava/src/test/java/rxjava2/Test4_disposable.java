package rxjava2;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;
import util.Utils;

public class Test4_disposable {
    @Test
    public void test() {
        Observable.just(1, 2, 3, 4, 5)
                .subscribe(new Observer<Integer>() {
                    Disposable mD;
                    @Override
                    public void onSubscribe(Disposable d) {
                        this.mD = d;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Utils.println("onNext: " + integer);
                        if(3 == integer) {
                            mD.dispose();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Utils.println("onError:" + e);
                    }

                    @Override
                    public void onComplete() {
                        Utils.println("onComplete");
                    }
                });
    }
}
