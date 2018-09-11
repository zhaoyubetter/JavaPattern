package com.better.javapoet;

import com.better.Utils;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 */
public class Test7_innerClass {

    public static void main(String... aaa) throws IOException {
        test1();
        Utils.println("=========================================");
        test2();
        Utils.println("=========================================");
        test3();
    }

    /**
     * 匿名
     * @throws IOException
     */
    private static void test1() throws IOException {
        TypeSpec comparator = TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(ParameterizedTypeName.get(Comparator.class, String.class))
                .addMethod(MethodSpec.methodBuilder("compare")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(String.class, "a")
                        .addParameter(String.class, "b")
                        .returns(int.class)
                        .addStatement("return $N.length() - $N.length()", "a", "b")
                        .build())
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                .addMethod(MethodSpec.methodBuilder("sortByLength")
                        .addParameter(ParameterizedTypeName.get(List.class, String.class), "strings")
                        .addStatement("$T.sort($N, $L)", Collections.class, "strings", comparator)
                        .build())
                .build();

        JavaFile.Builder builder = JavaFile.builder("com.test", helloWorld);

        builder.build().writeTo(System.out);
    }

    private static void test2() throws IOException {
        ClassName outer = ClassName.get("com.better", "Outer");
        ClassName inner = outer.nestedClass("Inner");
        ClassName callable = ClassName.get(Callable.class);
        TypeSpec typeSpec = TypeSpec.classBuilder("Outer")
                .superclass(ParameterizedTypeName.get(callable,
                        inner))
                .addType(TypeSpec.classBuilder("Inner")
                        .addModifiers(Modifier.STATIC)
                        .build())
                .build();

        JavaFile.Builder builder = JavaFile.builder("com.test", typeSpec);
        builder.build().writeTo(System.out);
    }

    private static void test3() throws IOException {
        ClassName outer = ClassName.get("java.lang", "String");
        ClassName inner = outer.nestedClass("Inner");

        MethodSpec barMethod = MethodSpec.methodBuilder("bar")
                .addStatement("new $T().new $T()", outer, inner)
                .build();

        TypeSpec fooType = TypeSpec.classBuilder("Foo")
                .addMethod(barMethod)
                .build();

        JavaFile.builder("foo.bar", fooType).build().writeTo(System.out);
    }
}
