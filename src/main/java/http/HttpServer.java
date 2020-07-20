package http;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.spi.FileTypeDetector;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The simplest http server. Only support the method: GET.
 * Cooperate with the class {@link HttpClient} which is the client use {@link java.net.HttpURLConnection} fetch something from this server.
 */
public class HttpServer {
    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";
    private static final MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = new HttpServer();
        httpServer.startServer();
    }

    public void startServer() {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            InetSocketAddress socketAddress = new InetSocketAddress("localhost", 8090);
            serverSocketChannel.bind(socketAddress);
            serverSocketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Start the server!");
            while (true) {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()) {
                        acceptChanel(selector, selectionKey);
                    } else if (selectionKey.isReadable()) {
                        //Read something from channel.
                        response(selectionKey);
                    }

                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void acceptChanel(Selector selector, SelectionKey selectionKey) throws IOException {
        ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel socketChannel = channel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private void response(SelectionKey selectionKey) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        int read = channel.read(byteBuffer);
        byteBuffer.flip();
        if (0 < read) {
            String result = new String(byteBuffer.array(), 0, read);
            String lineSeparator = "\r\n";
            String[] requestsLines = result.split(lineSeparator);
            String[] requestLine = requestsLines[0].split(" ");
            String method = requestLine[0];
            String path = requestLine[1];
            String version = requestLine[2];
            String host = requestsLines[1].split(" ")[1];

            List<String> headers = new ArrayList<>();
            for (int h = 2; h < requestsLines.length; h++) {
                String header = requestsLines[h];
                if (0 < header.trim().length()) {
                    headers.add(header);
                }
            }
            if (METHOD_GET.equals(method)) {
                Path filePath = getFilePath(path);
                if (Files.exists(filePath)) {
                    String contentType = fileTypeMap.getContentType(filePath.toFile());
                    sendResponse(channel, "200 OK", contentType, Files.readAllBytes(filePath));
                } else {
                    // 404
                    byte[] notFoundContent = "<h1>Not found :(</h1>".getBytes();
                    sendResponse(channel, "404 Not Found", "text/html", notFoundContent);
                }
            }
        }
    }

    private Path getFilePath(String path) {
        if ("/".equals(path)) {
            path = "/index.html";
        }
        File file = new File("resources/report");
        return Paths.get(file.getAbsolutePath(), path);
    }

    private void sendResponse(SocketChannel channel, String status, String contentType, byte[] content) throws IOException {
        String lineSeparator = "\r\n";
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        byteBuffer.put(("HTTP/1.1 " + status + lineSeparator).getBytes());
        byteBuffer.put(("ContentType: " + contentType + lineSeparator).getBytes());
        byteBuffer.put(lineSeparator.getBytes());
        byteBuffer.put(content);
        byteBuffer.put((lineSeparator + lineSeparator).getBytes());
        byteBuffer.flip();
        channel.write(byteBuffer);
        byteBuffer.clear();
        channel.close();
    }
}
