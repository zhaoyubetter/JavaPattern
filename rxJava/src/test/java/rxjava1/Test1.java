//package rxjava1;
//
//import org.junit.Before;
//import org.junit.Test;
//import rx.Observable;
//import rx.Observer;
//import rx.Subscriber;
//import rx.functions.Func1;
//
//import static org.junit.Assert.assertEquals;
//
//public class Test1 {
//    private static final Func1<Integer, Boolean> IS_EVEN = new Func1<Integer, Boolean>() {
//        @Override
//        public Boolean call(Integer value) {
//            return value % 2 == 0;
//        }
//    };
//
//    @Test
//    public void test1() {
//        Observable<Object> observable = Observable.create(new Observable.OnSubscribe<Object>() {
//            @Override
//            public void call(Subscriber<? super Object> subscriber) {
//
//            }
//        });
//
//        Observer<Object> observer = new Observer<Object>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(Object o) {
//                System.out.println(o);
//            }
//        };
//
//        String[] params = {"11", "22", "33"};
//        observable = Observable.from(params);
//        observable.subscribe(observer);
//    }
//
//    @Test
//    public void fromArray() {
//        String[] items = new String[]{"one", "two", "three"};
//        assertEquals(new Integer(3), Observable.from(items).count().toBlocking().single());
//        assertEquals("two", Observable.from(items).skip(1).take(1).toBlocking().single());
//        assertEquals("three", Observable.from(items).takeLast(1).toBlocking().single());
//    }
//
//    @Test
//    public void test2() {
//        Observable.create(new Observable.OnSubscribe<Integer>() {
//
//            @Override
//            public void call(Subscriber<? super Integer> subscriber) {
//                for (int i = 0; i < 5; i++) {
//                    subscriber.onNext(i);
//                }
//                subscriber.onCompleted();
//            }
//
//        }).subscribe(new Observer<Integer>() {
//
//            @Override
//            public void onCompleted() {
//                System.out.println("onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                System.out.println("onError");
//            }
//
//            @Override
//            public void onNext(Integer item) {
//                System.out.println("Item is " + item);
//            }
//        });
//    }
//}
