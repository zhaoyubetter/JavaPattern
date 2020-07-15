package com.better.io.okio;

import com.better.Utils;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class Test {

    @org.junit.Test
    public void testUseIO() throws IOException {
        URL resource = Test.class.getClassLoader().getResource("files/file1.txt");
        File file = new File(resource.getPath());

        // 1. First to bytes
        byte[] bytes = new byte[1024];
        FileInputStream fis = new FileInputStream(file);
        fis.read(bytes);
        // while 省略

        // 2. bytes to String
        String content = new String(bytes);

        // 3.println

        fis.close();
    }

    @org.junit.Test
    public void testUseNIO() throws IOException {
        URL resource = Test.class.getClassLoader().getResource("files/file1.txt");
        File file = new File(resource.getPath());
        FileInputStream fis = new FileInputStream(file);

        // 1. Read
        final FileChannel channel = fis.getChannel();
        final ByteBuffer buffer = ByteBuffer.allocate(1024);
        channel.read(buffer);
        // 省略while

        // 2.Write to System.out
        buffer.flip();
        System.out.println(buffer.array().toString());

        fis.close();
    }

    /**
     * 读txt例子
     * @throws IOException
     */
    @org.junit.Test
    public void testUseOK() throws IOException {
        URL resource = Test.class.getClassLoader().getResource("files/hofuman.txt");
        File file = new File(resource.getPath());

        // 1.拿到 source 对象，这为 Okio source()方法的匿名内部类
        Source source = Okio.source(file);
        // 2.这里的buffer实际类型为：RealBufferedSource
        BufferedSource bufferedSource = Okio.buffer(source);
        /** 3.
         *  RealBufferedSource#readString
         */
        final String content = bufferedSource.readString(Charset.forName("utf-8"));
        Utils.println(content);

        // 4.重点关注 RealBufferedSource#readString，这里非常不好懂。很绕
        // 实际意思是Buffer可读可写
        /*
            // buffer 为 Buffer final 类，写入数据到buffer
            buffer.writeAll(source);
            // 从 buffer 缓存中读取数据
            return buffer.readString(charset);
         */
    }


}
