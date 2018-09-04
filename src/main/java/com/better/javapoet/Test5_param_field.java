package com.better.javapoet;

import com.better.Utils;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.io.IOException;

/**
 * parameter and field
 */
public class Test5_param_field {

    public static void main(String... args) throws IOException {
        test1_param();
        Utils.println("=========================================");
        test2_field();
    }

    static void test1_param() throws IOException {
        ClassName hoverboard = ClassName.get("com.mattel", "Hoverboard");

        ParameterSpec android = ParameterSpec.builder(String.class, "android")
                .addModifiers(Modifier.FINAL)
                .build();

        MethodSpec welcomeOverlords = MethodSpec.methodBuilder("welcomeOverlords")
                .addParameter(android)
                .addParameter(String.class, "robot", Modifier.FINAL)
                .addParameter(hoverboard, "board", Modifier.FINAL)
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                .addMethod(welcomeOverlords)
                .build();

        JavaFile javaFile = JavaFile.builder("comb.better", helloWorld)
                .build();

        javaFile.writeTo(System.out);
    }

    /**
     * field
     *
     * @throws IOException
     */
    static void test2_field() throws IOException {
        FieldSpec android = FieldSpec.builder(String.class, "android")
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC)
                .addField(android)
                .addField(String.class, "robot", Modifier.PRIVATE, Modifier.FINAL)
                .build();

        JavaFile javaFile = JavaFile.builder("comb.better", helloWorld)
                .build();

        javaFile.writeTo(System.out);
    }

}
