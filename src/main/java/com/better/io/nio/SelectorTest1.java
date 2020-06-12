package com.better.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 单线程可连多个客户端
 */
public class SelectorTest1 {
    public static void main(String[] args) throws IOException {
        int[] ports = new int[5];

        ports[0] = 5000;
        ports[1] = 5001;
        ports[2] = 5002;
        ports[3] = 5003;
        ports[4] = 5004;

        Selector selector = Selector.open();

        // 4个端口监听
        for (int i = 0; i < ports.length; i++) {
            ServerSocketChannel socketChannel = ServerSocketChannel.open();
            socketChannel.configureBlocking(false);
            ServerSocket serverSocket = socketChannel.socket();
            InetSocketAddress address = new InetSocketAddress(ports[i]);
            serverSocket.bind(address);

            // 注册。通道与选择器
            socketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("监听端口：" + ports[i]);
        }

        // 客户端连接
        while (true) {
            int numbers = selector.select();    // select() 为阻塞方法
            System.out.println("numbers: " + numbers);

            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);

                    // 至此链接已建立好
                    socketChannel.register(selector, SelectionKey.OP_READ); // 关注读
                    iterator.remove(); // 从集合中移除

                    System.out.println("获取客户端链接：" + socketChannel);
                } else if (selectionKey.isReadable()) { // 开始读取数据
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    int bytesRead = 0;
                    while (true) {
                        ByteBuffer buffer = ByteBuffer.allocate(512);
                        buffer.clear();
                        int read = socketChannel.read(buffer);
                        if (read <= 0) {
                            break;
                        }
                        buffer.flip();
                        socketChannel.write(buffer); // 写出
                        bytesRead += read;
                    }

                    System.out.println("读取：" + bytesRead + ", 来自：" + socketChannel);
                    // 本次selectionKey事件结束，remove
                    iterator.remove(); 
                }
            }
        }
    }
}