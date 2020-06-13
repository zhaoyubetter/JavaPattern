package com.better.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * 一个 selector 可以注册多个 Channel 对象
 */
public class NioServer {

    private static Map<String, SocketChannel> clientMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        // 1.创建服务端对象
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        // get socket
        ServerSocket socket = serverSocketChannel.socket();
        // 绑定端口
        socket.bind(new InetSocketAddress(8899));

        // 2. 创建select对象
        Selector selector = Selector.open();
        // 将 步骤1的 channel 注册到 selector 上 (并关注 ACCEPT 连接事件)
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 3.事件处理，使用死循环
        while (true) {
            // 返回关注事件的数量
            int  num = selector.select();
            if(num == 0) {
                continue;
            }
            // 获取关注SelectionKey的集合对象（之前注册好的）
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            selectedKeys.forEach(selectionKey -> {
                final SocketChannel client; // 客户端
                if (selectionKey.isAcceptable()) { // 判断selectionKey的类型
                    // 4. 通过 selectionKey 获取 关联的 channel，并注册到 selector 上
                    ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                    try {
                        client = server.accept(); // 拿到客户端
                        client.configureBlocking(false);
                        // socketChannel 关注的是读事件
                        client.register(selector, SelectionKey.OP_READ); // 客户端只关注 READ

                        // 5。记录客户端
                        String key = "[" + UUID.randomUUID().toString() + "]";
                        clientMap.put(key, client);
                    } catch (IOException e) {
                    }
                } else if (selectionKey.isReadable()) { // 6.判断是否有数据可读
                    // 读取客户端发送过来的数据
                    try {
                        client = (SocketChannel) selectionKey.channel(); // 上面if注册的client，是socketChannel
                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                        int count = client.read(readBuffer);
                        if (count > 0) {
                            readBuffer.flip();
                            Charset charset = Charset.forName("utf-8");
                            String receiveMsg = String.valueOf(charset.decode(readBuffer).array());
                            System.out.println(client + ": " + receiveMsg);

                            String sendKey = null;
                            for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                if (entry.getValue() == client) {
                                    sendKey = entry.getKey();
                                    break;
                                }
                            }

                            // 写出到每个客户端（除了自己）
                            for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                // if(entry.getKey() != sendKey) {
                                    ByteBuffer writeBuffer = ByteBuffer.allocate(512);
                                    writeBuffer.put((sendKey + ": " + receiveMsg).getBytes());
                                    writeBuffer.flip();
                                    entry.getValue().write(writeBuffer);
                                // }
                            }
                        }
                    } catch (Exception e) {

                    }
                }
            });
            selectedKeys.clear(); // 一定要注意
        }
    }
}