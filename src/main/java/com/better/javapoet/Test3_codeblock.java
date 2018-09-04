package com.better.javapoet;

import com.better.Utils;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;


public class Test3_codeblock {

    public static void main(String... args) throws IOException {
        test1();
        Utils.println("=========================================");
        test2();
    }

    static void test1() throws IOException {
        CodeBlock block1 = CodeBlock.builder().add("String a = I ate $L $L", 3, "tacos")
                .build();

        // use position
        CodeBlock block2 = CodeBlock.builder().add("String b = I ate $2L $1L", "tacos", 3)
                .build();


        // block4 模板替换
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("food", "tacos");
        map.put("count", 3);
        CodeBlock block3 = CodeBlock.builder().addNamed("I ate $count:L $food:L", map)
                .build();

        // perfect
        CodeBlock block4 = CodeBlock.builder().add("String c = $S",
                CodeBlock.builder().add("I ate $2L $1L", "tacos", 3).build().toString())
                .build();

        MethodSpec testMethod = MethodSpec.methodBuilder("coldBlock")
                .addParameter(int.class, "i", Modifier.FINAL)
                .addModifiers(Modifier.PUBLIC)
                .addStatement(block1)
                .addStatement(block2)
                .addStatement(block3)
                .addStatement(block4)
                .build();

        TypeSpec testType = TypeSpec.classBuilder("BetterClass")
                .addMethod(testMethod)
                .build();

        JavaFile javaFile = JavaFile.builder("com.better.test", testType)
                .build();

        javaFile.writeTo(System.out);
    }

    static void test2() throws IOException {

    }
}
