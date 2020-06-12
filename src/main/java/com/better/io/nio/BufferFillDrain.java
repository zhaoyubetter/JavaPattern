package com.better.io.nio;

import java.nio.CharBuffer;

public class BufferFillDrain {

    static int index = 0;
    static String[] strings = {"A random string value", "The product of an infinite number of monkeys",
            "Hey hey we're the Monkees", "Opening act for the Monkees: Jimi Hendrix", "'Scuse me while I kiss this fly",
            "Help Me! Help Me!",};

    public static void main(String... args) {
        CharBuffer buffer = CharBuffer.allocate(100);
        while (fillBuffer(buffer)) {
            buffer.flip(); // 翻转
            drainBuffer(buffer);
            buffer.clear();
        }
    }

    static boolean fillBuffer(CharBuffer charBuffer) {
        if (index >= strings.length) {
            return false;
        }

        String string = strings[index++];
        for (int i = 0; i < string.length(); i++) {
            charBuffer.put(string.charAt(i));
        }

        return true;
    }

    static void drainBuffer(CharBuffer buffer) {
        while (buffer.hasRemaining()) {
            print(buffer.get());
        }
        print("\n");
    }

    static void print(Object o) {
        System.out.print(o);
    }
}