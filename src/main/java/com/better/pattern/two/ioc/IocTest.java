package com.better.pattern.two.ioc;

import com.better.Utils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 控制反转，到框架中
 */
public class IocTest {

    @Test
    void test1() {
        TestCase t1 = new TestCase() {
            @Override
            boolean doTest() {
                return true;
            }
        };

        TestCase t2 = new TestCase() {
            @Override
            boolean doTest() {
                return false;
            }
        };

        JunitApplication.registerCase(t1);
        JunitApplication.registerCase(t2);
    }

    static abstract class TestCase {
        void run() {
            if (doTest()) {
                Utils.println("test ok.");
            } else {
                Utils.println("test fail.");
            }
        }

        abstract boolean doTest();
    }

    static class JunitApplication {

        static List<TestCase> cases = new ArrayList<>();

        public static void registerCase(TestCase c) {
            cases.add(c);
        }

        public static void main(String... a) {
            for (TestCase c : cases) {
                c.run();
            }
        }
    }

}
