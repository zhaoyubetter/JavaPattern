package com.better.javapoet;

import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.Map;

/**
 * 数组参数测试
 */
public class Test8_array {

    /**
     * <pre>
     * public final void handleTable(final Map<Class<? extends Activity>, String[]> map) {
     *     map.put(Activity.class, String[].class);
     *   }
     *   </pre>
     */

    public static void main(String[] args) throws IOException {

        final ClassName activityClass = ClassName.get("android.app", "Activity"); // 创建activity ClassName
        final ParameterizedTypeName paramActivityMap = ParameterizedTypeName.get(ClassName.get(Map.class),
                ParameterizedTypeName.get(ClassName.get(Class.class), WildcardTypeName.subtypeOf(activityClass)),
                ArrayTypeName.of(String.class));

        final MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("handleTable")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addParameter(paramActivityMap, "map", Modifier.FINAL);

        // insertLast code statement


//        String expected = "java.lang.String[] names = new java.lang.String[] {\"1\",\"2\"}";
        String[] testArr = new String[]{"1", "2"};
        String literal = "{\"" + String.join("\",\"", testArr) + "\"}";
//        ArrayTypeName stringArray = ArrayTypeName.of(String.class);
//        CodeBlock block = CodeBlock.builder().insertLast("$1T names = new $1T $2L", stringArray, literal).build();

        CodeBlock block = CodeBlock.builder().add("map.put($T.class, new $T $L)",
                activityClass,
                ArrayTypeName.of(String.class),
                literal
        ).build();
        methodBuilder.addStatement(block);

        TypeSpec injectClass = TypeSpec.classBuilder("TestTable")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(methodBuilder.build())
                .build();

        final JavaFile javaFile = JavaFile.builder("com.better", injectClass)
                .build();

        javaFile.writeTo(System.out);
        // Map<Class<? extends Activity, String[]> map
        // ArrayTypeName mapArray = ArrayTypeName.of(mapName); // Map<K, V>[]
    }
}
