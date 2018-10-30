package com.better.javapoet;

import com.better.Utils;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.io.IOException;

/**
 * java poet
 * https://github.com/square/javapoet
 * https://www.ctolib.com/topics-114923.html
 */
public class Test1_base {
    public final static void main(String... aaa) throws IOException {
        test1();
        Utils.println("=========================================");
        test2();
        Utils.println("=========================================");
        test3();
        Utils.println("=========================================");
        test4_statment();
    }

    private static void test1() throws IOException {
        MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();

        JavaFile javaFile = JavaFile.builder("com.example.helloworld", helloWorld)
                .build();

        javaFile.writeTo(System.out);
    }

    /**
     * code use define
     *
     * @throws IOException
     */
    private static void test2() throws IOException {
        MethodSpec main = MethodSpec.methodBuilder("testMethod")
                .addCode(""
                        + "int total = 0;\n"
                        + "for (int i = 0; i < 10; i++) {\n"
                        + "  total += i;\n"
                        + "}\n")
                .build();

        TypeSpec myType = TypeSpec.classBuilder("BetterClass")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();

        JavaFile javaFile = JavaFile.builder("com.example.better", myType)
                .build();
        javaFile.writeTo(System.out);
    }

    private static void test3() throws IOException {
        MethodSpec main = MethodSpec.methodBuilder("main")
                .addStatement("int total = 0")
                .beginControlFlow("for (int i = 0; i < 10; i++)")
                .addStatement("total += i")
                .endControlFlow()
                .build();

        TypeSpec myType = TypeSpec.classBuilder("BetterClass")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();

        JavaFile javaFile = JavaFile.builder("com.example.better", myType)
                .build();
        javaFile.writeTo(System.out);
    }

    private static void test4_statment() throws IOException {
        int i = 0, len = 100;
        MethodSpec main = MethodSpec.methodBuilder("testStatement")
                .addModifiers(Modifier.FINAL, Modifier.PUBLIC)
                .returns(int.class)
                .addStatement("int total = 0")
                .beginControlFlow("for (int i = " + i + "; i < " + len + "; i++)")
                .addStatement("total += i")
                .endControlFlow()
                .addStatement("return total;")
                .build();

        TypeSpec myType = TypeSpec.classBuilder("BetterClass")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();

        JavaFile javaFile = JavaFile.builder("com.example.better", myType)
                .build();
        javaFile.writeTo(System.out);
    }




}
