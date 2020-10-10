package com.better.io.nio;

import com.better.Utils;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Arrays;

/**
 * ByteBuffer
 */
public class ByteBufferTest1 {

    /**
     * ByteBuffer
     */
    @Test
    public void test1() {
        ByteBuffer buffer = ByteBuffer.allocate(100);
        buffer.put((byte) 'H').put((byte) 'e').put((byte) 'l').put((byte) 'l').put((byte) 'o');
        buffer.flip();
        while (buffer.hasRemaining()) {
            Utils.println(buffer.get());
        }


        buffer.limit(100);  // limit 还原
        buffer.put(0, (byte) 'z');
        buffer.put((byte) 'o');

        Utils.println("----------------");
        buffer.flip();      // limit 设置为 position 位置
        while (buffer.hasRemaining()) {
            Utils.println(buffer.get());
        }

        buffer.mark();
        buffer.reset();
    }

    /**
     * CharBuffer
     */
    @Test
    public void test2() {
        CharBuffer buffer = CharBuffer.allocate(100);
        buffer.put("A random string value");    // 填充
        buffer.flip();

        // 批量移动
        char[] charArr = new char[5];
        buffer.get(charArr); // the same as buffer.get(charArr, 0. charArr.length);
        Utils.println(Arrays.toString(charArr));
    }

    @Test
    public void test3() {
        CharBuffer buffer = CharBuffer.allocate(100);
        buffer.put("A random string value");    // 填充
        buffer.flip();

        // 目标数组很大，但是源没有这么大，所以需要释放大小
        char[] charArr = new char[1024];
        int length = buffer.remaining();
        buffer.get(charArr, 0, length);
        Utils.println(buffer.hasArray());
        Utils.println(Arrays.toString(charArr));

    }

    /**
     * 创建缓冲区
     */
    @Test
    public void test4() {
        char[] charArr = new char[100];
        final CharBuffer wrap = CharBuffer.wrap(charArr);
        Utils.println(wrap.hasArray());
    }

    /**
     * 复制缓冲区1
     */
    @Test
    public void test5() {
        CharBuffer buffer = CharBuffer.allocate(10);
        buffer.put("Hello");
        CharBuffer dupeBuffer = buffer.duplicate();
        dupeBuffer.put("ooo");
        Utils.println(Arrays.toString(buffer.array())); // 操作同一份
    }


    /**
     * 复制缓冲区2
     * only
     */
    @Test
    public void test6() {
        CharBuffer buffer = CharBuffer.allocate(10);
        buffer.put("Hello");
        CharBuffer dupeBuffer = buffer.asReadOnlyBuffer();
        Utils.println(Arrays.toString(buffer.array()));

        dupeBuffer.flip();

        final char[] array = new char[dupeBuffer.remaining()];
        dupeBuffer.get(array);
        Utils.println(Arrays.toString(array));

//        while (dupeBuffer.hasRemaining()) {
//            Utils.println(dupeBuffer.get());
//        }
    }

    /**
     * 分割缓冲区 slice()
     */
    @Test
    public void test7() {
        ByteBuffer buffer = ByteBuffer.allocate(15);
        buffer.put("zhaoyubetter".getBytes());
        buffer.flip();
        int c = buffer.get();
        int b = buffer.get();
        System.out.print((char) c);
        System.out.print((char) b);
        System.out.println();

        System.out.println(buffer.position());
        System.out.println(buffer.limit());

        buffer = buffer.slice();
//        buffer.flip();
        System.out.println(buffer.position());
        System.out.println(buffer.limit());

    }

    @Test
    public void testSystemCopy() {
        ByteBuffer buffer = ByteBuffer.allocate(15);
        buffer.put("zhaoyubetter".getBytes());
        buffer.flip();
        byte[] origin = new byte[buffer.remaining()];
        buffer.get(origin);

        byte[] a = new byte[5];
        System.arraycopy(origin, 0, a, 0, a.length);
        System.out.println(buffer.position());      // position 会变；
    }
}
