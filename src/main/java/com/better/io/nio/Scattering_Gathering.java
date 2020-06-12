package com.better.io.nio;

import com.better.Utils;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

// nc localhost 8899
public class Scattering_Gathering {
    @Test
    public void test1() throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(8899);
        serverSocketChannel.socket().bind(address);

        int messageLength = 2 + 3 + 4;

        ByteBuffer[] buffers = new ByteBuffer[3];
        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);

        // 客户端
        SocketChannel socketChannel = serverSocketChannel.accept();

        while (true) {
            int byteRead = 0;

            while (byteRead < messageLength) {
                long r = socketChannel.read(buffers);     // 全部填充
                byteRead += r;

                Utils.println("bytesRead: " + byteRead);

                Arrays.asList(buffers).stream().map(buffer ->
                        "pos: " + buffer.position() + ", limit: " + buffer.limit()).
                        forEach(System.out::println);
            }

            Arrays.asList(buffers).forEach(it -> it.flip());

            long byteWritten = 0;
            while (byteWritten < messageLength) {
                long r = socketChannel.write(buffers);
                byteWritten += r;
            }

            Arrays.asList(buffers).forEach(it -> it.clear());

            Utils.println("bytesRead: " + byteRead + ", bytesWritten: " + byteWritten + ", messageLength: " + messageLength);
        }
    }
}
