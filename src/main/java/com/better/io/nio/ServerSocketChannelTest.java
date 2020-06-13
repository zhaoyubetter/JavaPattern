package com.better.io.nio;

import com.better.Utils;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelTest {
    @Test
    public void test1() throws IOException, InterruptedException {
        // server
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(8899));

        ByteBuffer buffer = ByteBuffer.wrap("Hello".getBytes());

        while (true) {
            Utils.println("waiting for client connecting...");
            SocketChannel client = serverSocketChannel.accept();
            if (client == null) {
                Thread.sleep(2000);
            } else {
                System.out.println("Incoming connection from: " + client.socket().getRemoteSocketAddress());
                buffer.rewind();
                client.write(buffer);   // 向客户端打招呼
                client.close(); // 关闭客户端
            }
        }
    }
}
