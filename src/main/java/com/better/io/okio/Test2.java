package com.better.io.okio;

import okio.BufferedSink;
import okio.Okio;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Test2 {
    public static void main(String ...aa ) throws IOException {
        // 注意文件路径 build/output
        URL resource = com.better.io.okio.Test.class.getClassLoader().getResource("files/output.txt");
        File file = new File(resource.getPath());

        final BufferedSink buffer = Okio.buffer(Okio.sink(file));
        buffer.writeUtf8("Hello, world 你好，Hello哈哈");
        buffer.close();     // 必要的 close
    }
}
