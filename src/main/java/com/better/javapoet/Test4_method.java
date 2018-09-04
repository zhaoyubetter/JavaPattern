package com.better.javapoet;

import com.better.Utils;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.io.IOException;

public class Test4_method {

    public static void main(String... aa) throws IOException {
        test1();
        Utils.println("=========================================");
        test2();
        Utils.println("=========================================");
    }

    static void test1() throws IOException {
        MethodSpec flux = MethodSpec.methodBuilder("flux")
                .addModifiers(Modifier.ABSTRACT, Modifier.PROTECTED)
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addMethod(flux)
                .build();

        JavaFile javaFile = JavaFile.builder("comb.better", helloWorld)
                .build();

        javaFile.writeTo(System.out);
    }

    /**
     * 构造函数
     * @throws IOException
     */
    static void test2() throws IOException {
        MethodSpec flux = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class, "greeting")             // 字段
                .addStatement("this.$N = $N", "greeting", "greeting")     // 语句 ("this.$L = $L", "greeting", "greeting")
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC)
                .addField(String.class, "greeting", Modifier.PRIVATE, Modifier.FINAL)
                .addMethod(flux)
                .build();

        JavaFile javaFile = JavaFile.builder("comb.better", helloWorld)
                .build();

        javaFile.writeTo(System.out);
    }
}
