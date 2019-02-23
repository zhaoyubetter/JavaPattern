package com.better.javapoet;

import com.better.Utils;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

/**
 * $L (原样输出),$S("字符串"),$T(类型)
 * ClassName(非常重要)
 * $N(使用代码中定义的方法)
 */
public class Test2_ {
    public static void main(String[] aaa) throws IOException {
        test1();
        Utils.println("=========================================");
        test2();
        Utils.println("=========================================");
        test3();
        Utils.println("=========================================");
        test4();
        Utils.println("=========================================");
        test4_className_parameterized();
        Utils.println("=========================================");
        test5_import_static();
        Utils.println("=========================================");
        test6_n();
    }

    /**
     * $L for Literals like String.format("%s", 22)
     * 原样输出
     *
     * @throws IOException
     */
    static void test1() throws IOException {
        MethodSpec methodSpec = MethodSpec.methodBuilder("testMethod")
                .returns(int.class)
                .addStatement("int result = 0")
                .beginControlFlow("for (int i = $L; i < $L; i++)", 0, 100)
                .addStatement("result = result $L i", "*")
                .endControlFlow()
                .addStatement("return result")
                .build();

        TypeSpec typeSpec = TypeSpec.classBuilder("BetterClass")
                .addMethod(methodSpec)
                .addModifiers(Modifier.FINAL, Modifier.PUBLIC)
                .build();

        JavaFile file = JavaFile.builder("com.better", typeSpec).build();

        file.writeTo(System.out);
    }

    /**
     * $S for Strings
     */
    static void test2() throws IOException {
        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(whatsMyName("slimShady"))
                .addMethod(whatsMyName("eminem"))
                .addMethod(whatsMyName("marshallMathers"))
                .build();

        JavaFile javaFile = JavaFile.builder("com.example.helloworld", helloWorld)
                .build();
        javaFile.writeTo(System.out);
    }

    private static MethodSpec whatsMyName(String name) {
        return MethodSpec.methodBuilder(name)
                .returns(String.class)
                .addStatement("return $S", name)
                .build();
    }

    /**
     * $T for Types
     *
     * @throws IOException
     */
    static void test3() throws IOException {
        MethodSpec today = MethodSpec.methodBuilder("today")
                .returns(Date.class)
                .addStatement("return new $T()", Date.class)
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(today)
                .build();

        JavaFile javaFile = JavaFile.builder("com.example.helloworld", helloWorld)
                .build();

        javaFile.writeTo(System.out);
    }

    /**
     * $T,类引用不在本工程，使用
     *
     *
     * @throws IOException
     */
    static void test4() throws IOException {
        // 自己的类
        ClassName hoverboard = ClassName.get("com.mattel", "Hoverboard");

        MethodSpec today = MethodSpec.methodBuilder("tomorrow")
                .returns(hoverboard)
                .addStatement("return new $T()", hoverboard)
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(today)
                .build();

        JavaFile javaFile = JavaFile.builder("com.example.helloworld", helloWorld)
                .build();

        javaFile.writeTo(System.out);
    }

    /**
     * ClassName
     *
     * @throws IOException
     */
    static void test4_className_parameterized() throws IOException {
        ClassName hoverboard = ClassName.get("com.mattel", "Hoverboard");
        ClassName list = ClassName.get("java.util", "List");
        ClassName arrayList = ClassName.get("java.util", "ArrayList");
        TypeName listOfHoverboards = ParameterizedTypeName.get(list, hoverboard);   // 泛型支持

        MethodSpec beyond = MethodSpec.methodBuilder("beyond")
                .returns(listOfHoverboards)
                .addStatement("$T result = new $T<>()", listOfHoverboards, arrayList)     // List<Hoverboard> result = new ArrayList<>();
                .addStatement("result.insertLast(new $T())", hoverboard)
                .addStatement("result.insertLast(new $T())", hoverboard)
                .addStatement("result.insertLast(new $T())", hoverboard)
                .addStatement("return result")
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(beyond)
                .build();

        JavaFile javaFile = JavaFile.builder("com.example.helloworld", helloWorld)
                .build();

        javaFile.writeTo(System.out);
    }

    /**
     * import static
     *
     * @throws IOException
     */
    static void test5_import_static() throws IOException {
        ClassName hoverboard = ClassName.get("com.mattel", "Hoverboard");
        ClassName list = ClassName.get("java.util", "List");
        ClassName arrayList = ClassName.get("java.util", "ArrayList");
        TypeName listOfHoverboards = ParameterizedTypeName.get(list, hoverboard);   // 泛型支持
        ClassName namedBoards = ClassName.get("com.mattel", "Hoverboard", "Boards");

        MethodSpec beyond = MethodSpec.methodBuilder("beyond")
                .returns(listOfHoverboards)
                .addStatement("$T result = new $T<>()", listOfHoverboards, arrayList)
                .addStatement("result.insertLast($T.createNimbus(2000))", hoverboard)
                .addStatement("result.insertLast($T.createNimbus(\"2001\"))", hoverboard)
                .addStatement("result.insertLast($T.createNimbus($T.THUNDERBOLT))", hoverboard, namedBoards)
                .addStatement("$T.sort(result)", Collections.class)
                .addStatement("return result.isEmpty() ? $T.emptyList() : result", Collections.class)
                .build();

        TypeSpec hello = TypeSpec.classBuilder("HelloWorld")
                .addMethod(beyond)
                .build();

        // static import
        JavaFile javaFile = JavaFile.builder("com.example.helloworld", hello)
                .addStaticImport(hoverboard, "createNimbus")
                .addStaticImport(namedBoards, "*")
                .addStaticImport(Collections.class, "*")
                .build();


        javaFile.writeTo(System.out);
    }

    static void test6_n() throws IOException {
        MethodSpec hexDigit = MethodSpec.methodBuilder("hexDigit")
                .addParameter(int.class, "i")
                .returns(char.class)
                .addStatement("return (char) (i < 10 ? i + '0' : i - 10 + 'a')")
                .build();

        MethodSpec byteToHex = MethodSpec.methodBuilder("byteToHex")
                .addParameter(int.class, "b")
                .returns(String.class)
                .addStatement("char[] result = new char[2]")
                .addStatement("result[0] = $N((b >>> 4) & 0xf)", hexDigit)
                .addStatement("result[1] = $N(b & 0xf)", hexDigit)
                .addStatement("return new String(result)")
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(byteToHex)
                .addMethod(byteToHex)
                .build();

        JavaFile javaFile = JavaFile.builder("com.example.helloworld", helloWorld)
                .build();

        javaFile.writeTo(System.out);

    }


}
