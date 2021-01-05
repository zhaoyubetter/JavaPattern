package rxjava2;

import org.junit.Test;

/**
 * https://blog.csdn.net/yankebin/article/details/80844891
 * 理解 onSubscribeOn 只有第一次设置管用的示例代码
 */
public class Test3_onSubscribe_once {

    interface IPaper {
        void show(String content);
    }

    interface IPrinter {
        void subscribe(IPaper pager);

        void preparedPrinter(IPaper pager);

        void print(IPaper pager);
    }

    class Paper implements IPaper {
        IPaper actual;
        String printerName;

        public Paper(IPaper actual, String printerName) {
            this.actual = actual;
            this.printerName = printerName;
        }

        @Override
        public void show(String content) {
            System.out.println(printerName + " show ==> print on paper , and work on " + Thread.currentThread().getName());
            actual.show(content);
        }
    } // end Paper

    class Printer implements IPrinter {

        IPrinter source;
        String name;

        public Printer(IPrinter source, String name) {
            this.source = source;
            this.name = name;
        }

        @Override
        public void subscribe(IPaper pager) {
            // 这个地方非常关键，包装上一层
            IPaper parent = new Paper(pager, name);
            // 子线程执行
            preparedPrinter(parent);
        }

        @Override
        public void preparedPrinter(IPaper paper) {
            System.out.println(name + " preparePrint on " + Thread.currentThread().getName());
            if (null == source) {
                print(paper);
                return;
            }
            // 切换线程
            new Thread(() -> {
                if (null != source) {
                    source.subscribe(paper);
                }
            }).start();
        }

        @Override
        public void print(IPaper paper) {
            System.out.println(name + " start print work on " + Thread.currentThread().getName());
            paper.show("哈哈哈哈哈");
        }
    }

    @Test
    public void test() {
        final IPrinter printer1 = new Printer(null, "printer1");
        final IPrinter printer2 = new Printer(printer1, "printer2");
        final IPrinter printer3 = new Printer(printer2, "printer3");
        final IPrinter printer4 = new Printer(printer3, "printer4");
        printer4.subscribe(
                // 这里为最原始的 Observe
                new IPaper() {
                    @Override
                    public void show(String content) {
                        System.out.println(content + " show on " + Thread.currentThread().getName());
                    }
                });
    }
}
