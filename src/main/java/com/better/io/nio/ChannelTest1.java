package com.better.io.nio;


import com.better.Utils;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.security.SecureRandom;

public class ChannelTest1 {

    /**
     * 通过 Channel 读文件
     *
     * @throws IOException
     */
    @Test
    public void test1() throws IOException {
        FileInputStream fis = new FileInputStream("files/nio.txt");
        final FileChannel channel = fis.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(256);
        channel.read(buffer);   // 通过通道，读入到 buffer 中

        buffer.flip();
        while (buffer.hasRemaining()) {
            byte b = buffer.get();
            Utils.print((char) b);
        }

        fis.close();
    }

    /**
     * 通过 Channel 写文件
     */
    @Test
    public void test2() throws IOException {
        FileOutputStream fos = new FileOutputStream("files/nio_w.txt");
        FileChannel channel = fos.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(256);
        buffer.put((byte) 'H');
        buffer.put((byte) 'e');
        buffer.flip();

        channel.write(buffer);

        fos.close();
    }

    /**
     * copy file use channel & buffer
     *
     * @throws IOException
     */
    @Test
    public void test3() throws IOException {
        FileInputStream fis = new FileInputStream("files/nio.txt");
        FileOutputStream fos = new FileOutputStream("files/nio_out.txt");
        FileChannel inChannel = fis.getChannel();
        FileChannel outChannel = fos.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(512);
        while (true) {
            buffer.clear();     // must be called clear method
            int read = inChannel.read(buffer);

            if (read == -1) {
                break;
            }

            buffer.flip();
            outChannel.write(buffer);
        }

        fis.close();
        fos.close();
    }

    @Test
    public void test4() throws IOException {
        FileInputStream fis1 = new FileInputStream("files/nio.txt");

        // 标出输出
        WritableByteChannel writableByteChannel = Channels.newChannel(System.out);

        FileChannel channel = fis1.getChannel();
        channel.transferTo(0, channel.size(), writableByteChannel);
        channel.close();
        fis1.close();
    }
}
