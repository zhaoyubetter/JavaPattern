package com.better.io.nio;

import com.better.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 客户端
 */
public class NIoClient {
    public static void main(String[] args) throws Exception {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);

        // 打开selector，并注册
        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_CONNECT); // 发起连接事件
        channel.connect(new InetSocketAddress("127.0.0.1", 8899));

        while (true) {
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            selectedKeys.forEach(selectionKey -> {
                if (selectionKey.isConnectable()) {  // 连接已建立
                    SocketChannel client = (SocketChannel) selectionKey.channel();
                    if (client.isConnectionPending()) {
                        try {
                            client.finishConnect();     // 完成连接

                            // 往服务端发送数据
                            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                            writeBuffer.put((LocalDateTime.now() + " 连接成功").getBytes());
                            writeBuffer.flip();
                            client.write(writeBuffer);

                            // 通过线程来获取标准输入的数据
                            ExecutorService executorService = Executors.newSingleThreadExecutor();
                            executorService.submit(() -> {
                                writeBuffer.clear();
                                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                                try {
                                    String sendMsg = br.readLine();
                                    writeBuffer.put(sendMsg.getBytes());
                                    writeBuffer.flip();
                                    client.write(writeBuffer);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });

                        } catch (IOException e) {
                        }
                    }
                    // 连接完毕后，注册读取事件
                    try {
                        client.register(selector, SelectionKey.OP_READ);
                    } catch (ClosedChannelException e) {
                        e.printStackTrace();
                    }
                } else if (selectionKey.isReadable()) {   // 读取数据
                    SocketChannel client = (SocketChannel) selectionKey.channel();
                    ByteBuffer readBuffer = ByteBuffer.allocate(512);
                    try {
                        int read = client.read(readBuffer);
                        if (read > 0) {
                            String receiveMsg = new String(readBuffer.array(), 0, read);
                            Utils.println(receiveMsg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            selectedKeys.clear();
        }

    }
}