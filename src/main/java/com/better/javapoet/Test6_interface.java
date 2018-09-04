package com.better.javapoet;

import com.better.Utils;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.io.IOException;

public class Test6_interface {
    public static void main(String... args) throws IOException {
        test1();
        Utils.println("=========================================");
    }

    static void test1() throws IOException {
        TypeSpec helloWorld = TypeSpec.interfaceBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC)
                .addField(FieldSpec.builder(String.class, "ONLY_THING_THAT_IS_CONSTANT")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                        .initializer("$S", "change")
                        .build())
                .addMethod(MethodSpec.methodBuilder("beep")
                        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                        .build())
                .build();

        JavaFile javaFile = JavaFile.builder("comb.better", helloWorld)
                .build();

        javaFile.writeTo(System.out);
    }
}
