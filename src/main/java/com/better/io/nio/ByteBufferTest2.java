package com.better.io.nio;

import com.better.Utils;
import http.two.BufferedChannelReader;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ByteBufferTest2 {

    /**
     * readLine 测试
     *
     * @throws EOFException
     */
    @Test
    public void testReadLine() throws IOException {
        URL resource = com.better.io.okio.Test.class.getClassLoader().getResource("files/nio.txt");
        File file = new File(resource.getPath());
        FileChannel channel = new RandomAccessFile(file, "r").getChannel();
//        BufferedChannelReaderCZ reader = new BufferedChannelReaderCZ(channel, 50);
//        BufferedChannelReader reader = new BufferedChannelReader(channel, 50);
//        String line = null;
//        while (null != (line = reader.readLine())) {
//            Utils.print(line);
//        }
        channel.close();
    }

    @Test
    public void test2() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);

        byteBuffer.put("abc".getBytes());
        if (byteBuffer.hasRemaining()) {
            byteBuffer.flip();
            byte[] bytes = new byte[byteBuffer.limit()];
            byteBuffer.get(bytes);
            Utils.println(new String(bytes));
        }

        new String(byteBuffer.array());
    }

    /**
     * readLine 测试
     *
     * @throws EOFException
     */
    @Test
    public void testReadBuffer() throws IOException {
        URL resource = com.better.io.okio.Test.class.getClassLoader().getResource("files/nio.txt");
        File file = new File(resource.getPath());

        FileChannel channel = new RandomAccessFile(file, "r").getChannel();
//        BufferedChannelReader reader = new BufferedChannelReader(channel, 20);
//        int size = 0;
//        while ((size = reader.readBytes()) > 0) {
//            byte[] arr = new byte[size];
//            reader.getBuffer().get(arr);
//            System.out.print(new String(arr));
//        }
//        channel.close();
    }
}
