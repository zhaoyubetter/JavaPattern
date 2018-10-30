package com.better.javapoet;

import com.better.Utils;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.lang.reflect.WildcardType;
import java.util.Map;

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

        ClassName activityClass = ClassName.get("android.app", "Activity"); // 创建activity ClassName
        ParameterizedTypeName paramType1 = ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(String.class), TypeVariableName.get("T"));
        ParameterizedTypeName paramType2 = ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(String.class), WildcardTypeName.subtypeOf(activityClass));
        // 泛型参数  Map<String, Class<? extends Activity>> map
        ParameterizedTypeName paramType3 = ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(String.class),
                ParameterizedTypeName.get(ClassName.get(Class.class), WildcardTypeName.subtypeOf(activityClass)));

        MethodSpec welcomeOverlords = MethodSpec.methodBuilder("welcomeOverlords")
                .addParameter(android)
                .addParameter(String.class, "robot", Modifier.FINAL)
                .addParameter(hoverboard, "board", Modifier.FINAL)
                .addParameter(paramType1, "map1")
                .addParameter(paramType2, "map2", Modifier.FINAL)
                .addParameter(paramType3, "map3", Modifier.FINAL)
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
