package com.better.newFeature.java8;

import com.better.Utils;
import org.junit.Test;


public class Test1_lambda {

    interface MathOperation {
        int operation(int a, int b);
    }

    int operate(int a, int b, MathOperation operation) {
        return operation.operation(a, b);
    }

    /**
     * Base Lambda
     */
    @Test
    public void test1() {
        // Uses type defined
        MathOperation addition = (int a, int b) -> a + b;
        // No uses type defined
        MathOperation subtraction = (a, b) -> a - b;

        // Uses block and return
        MathOperation multi = (a, b) -> {
            return a * b;
        };

        Utils.println(addition.operation(1, 2));
        Utils.println(subtraction.operation(1, 2));
        Utils.println(multi.operation(1, 2));
    }

    /**
     * Use functions
     * 避免使用匿名内部类
     */
    @Test
    public void test2() {
        int result = operate(4, 5, (a, b) -> a + b);
        Utils.println(result);

        MathOperation subtraction = (a, b) -> a - b;
        result = operate(4, 5, subtraction);
        Utils.println(result);
    }
}
