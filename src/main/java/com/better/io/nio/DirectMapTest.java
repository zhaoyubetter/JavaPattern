package com.better.io.nio;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * 操作内存就是操作文件（通过操作系统来直接操作文件）
 */
public class DirectMapTest {

    /**
     * 内存映射修改文件，利用操作系统
     * 位于堆外内存（JNI）
     * @throws IOException
     */
    @Test
    public void test1() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("files/nio.txt", "rw");
        // 从 0 开始映射5个字节长度大小，映射到内存当中
        MappedByteBuffer map = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 5);
        map.put(0, (byte) 'Z');
        map.put(3, (byte) 'H');
        randomAccessFile.close();
    }

    @Test
    public void test2_fileLock() throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("files/nio.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        channel.lock(0, 3, true);
        ///
    }

}
