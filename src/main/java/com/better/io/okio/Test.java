package com.better.io.okio;

import com.better.Utils;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

public class Test {

    @org.junit.Test
    public void test1() throws IOException {
        URL resource = Test.class.getClassLoader().getResource("files/file1.txt");
        File file = new File(resource.getPath());

        Source source = Okio.source(file);
        BufferedSource bufferedSource = Okio.buffer(source);
        Utils.println(bufferedSource.readString(Charset.forName("utf-8")));
    }
}
