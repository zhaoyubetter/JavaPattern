package com.better.io.okio;

import okio.BufferedSink;
import okio.Okio;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Test3 {
    public static void main(String... aa) throws IOException {
        URL resource = com.better.io.okio.Test.class.getClassLoader().getResource("files/output.txt");
        File file = new File(resource.getPath());

        final BufferedSink buffer = Okio.buffer(Okio.sink(file));
        buffer.write("Hello, world 你好，Hello哈哈".getBytes());
        buffer.close();     // 必要的 close
    }
}
