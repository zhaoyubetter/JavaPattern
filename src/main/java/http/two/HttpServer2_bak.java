package http.two;

import javax.activation.MimetypesFileTypeMap;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Simple http server
 */
public class HttpServer2_bak {
    public static void main(String[] args) {
        new SimpleServer().start();
    }

    static class SimpleServer {

        private final String LINE_SEP = "\r\n";
        private final String METHOD_GET = "GET";
        private final String METHOD_POST = "POST";
        private final ExecutorService executors = Executors.newFixedThreadPool(10);

        private static final MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();

        public void start() {
            try {
                Selector selector = Selector.open();

                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.bind(new InetSocketAddress("localhost", 8899));
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                System.out.println("Start Server.");

                while (true) {
                    selector.select();          // blocking
                    final Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        final SelectionKey selectionKey = iterator.next();
                        if (selectionKey.isAcceptable()) {
                            acceptChannel(selector, selectionKey);
                        } else if (selectionKey.isReadable()) {
                            response2(selectionKey);
                        }
                        iterator.remove();  // 必要的移除
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 关注读
        private void acceptChannel(Selector selector, SelectionKey selectionKey) throws Exception {
            final ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ); // 客户端关注读
            System.out.println("one client is connected: " + socketChannel.getRemoteAddress());
        }

        private void response2(final SelectionKey selectionKey) throws Exception {
            String method = "GET";
            String path = "";
            String version = "";
            Map<String, String> headers;
            Map<String, String> pathParam;
            Map<String, String> bodyParam;
            try {
                SocketChannel client = (SocketChannel) selectionKey.channel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(128);       // 故意使用 128
                StringBuilder headerSb = new StringBuilder();

                int lastChar = -1;
                while (client.read(byteBuffer) > 0) {
                    byteBuffer.flip();
                    while (byteBuffer.hasRemaining()) {
                        char c = (char) byteBuffer.get();
                        headerSb.append(c);
                        // c is return, last is nextLine
                        if (c == '\r' && lastChar == '\n') {  // header area is end
                            headerSb.deleteCharAt(headerSb.length() - 1);
                            headers = getHeaderMap(headerSb.toString());
                            final byte[] remain = new byte[byteBuffer.limit() - byteBuffer.position()];
                            byteBuffer.get(remain, 0, remain.length);
                            body(client, headers, remain);
                            byteBuffer.clear();
                            break;
                        }
                        lastChar = c;
                    }
                    byteBuffer.clear();
                }

                sendResponse(client, "404 Not Found", "text/html", "ok".getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private Map<String, String> getHeaderMap(String headerSb) {
            final String[] lines = headerSb.split(LINE_SEP);
            final String[] statuses = lines[0].split(" ");
            Map<String, String> headers = new HashMap<>();
            for (int i = 1; i < lines.length; i++) {
                if (lines[i] != null && !lines[i].isEmpty()) {
                    final String[] h = lines[i].split(":");
                    headers.put(h[0].toLowerCase().trim(), h[1].trim());
                }
            }
            return headers;
        }

        private void body(final SocketChannel client, final Map<String, String> headers, final byte[] remainBytes)
                throws IOException {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            StringBuilder sb = new StringBuilder();
            sb.append(new String(remainBytes, "utf-8"));
            long contentLength = 0;
            if (headers.containsKey("content-length")) {
                contentLength = Integer.parseInt(headers.get("content-length"));
            }

            if (headers.containsKey("content-type")) {
                String headerValue = headers.get("content-type");
                if (!headers.isEmpty()) {
                    if (headerValue.contains("multipart/form-data")) {
                        String boundary = headerValue.substring(headerValue.indexOf("boundary") + 9);
                        byteBuffer.put(remainBytes);    // remains
                        // analyze form data
                        while (client.read(byteBuffer) > 0) {
                            final Reader reader = Channels.newReader(Channels.newChannel(new ByteArrayInputStream(byteBuffer.array())), "utf-8");
                            final BufferedReader bufferedReader = new BufferedReader(reader);
                            String line = null;
                            while ((line = bufferedReader.readLine()) != null) {
                                /*
                                 ----------------------------088394715640614921034561
                                 Content-Disposition: form-data; name="abb"
                                 Content-Type: text/plain

                                 bbb
                                 */
                                if(line.startsWith("--") && line.indexOf(boundary) > 2) {

                                }

                            }
                            if (null == line || line.isEmpty()) {
                                break;
//                            } else if (line.indexOf("Content-Length") != -1) {
//                                this.contentLength = Integer.parseInt(line.substring(line.indexOf("Content-Length") + 16));
//                                System.out.println("contentLength: " + this.contentLength);
//                            } else if (line.indexOf("boundary") != -1) {
//                                this.boundary = line.substring(line.indexOf("boundary") + 9);
//                                System.out.println("********" + boundary);
//                            }

                            }
                            byteBuffer.clear();
//                            final Map<String, String> mapParam = getMapParam(sb.toString().trim());
                            return;
                        }
                    } else {
                        while (client.read(byteBuffer) > 0) {
                            byteBuffer.flip();
                            sb.append(new String(byteBuffer.array(), "utf-8"));
                            byteBuffer.clear();
                        }
                        final Map<String, String> mapParam = getMapParam(sb.toString().trim());
                    }
                }
            } else {
                // all is text/plain
            }
        }

        private void handleMethodGet(SocketChannel client, String path) throws Exception {
            Path filePath = getFilePath(path);
            if (Files.exists(filePath)) {
                String contentType = fileTypeMap.getContentType(filePath.toFile());
                sendResponse(client, "200 OK", contentType, Files.readAllBytes(filePath));
            } else {
                // 404
                byte[] notFoundContent = "<h1>Not found :(</h1>".getBytes();
                sendResponse(client, "404 Not Found", "text/html", notFoundContent);
            }
        }

        private void handleMethodPost(SocketChannel client, String path) throws Exception {
            // 1.part 1: analyze path, such as ?a=b&b=c
            final Map<String, String> pathParam = getPathParam(path);

            // 2.part 2: analyze body
            ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
            while (client.read(byteBuffer) > 0) {
                byteBuffer.flip();
                final String result = new String(byteBuffer.array());
                System.out.println(result);
                byteBuffer.clear();
            }

            sendResponse(client, "200 OK", "text/html", "ok".getBytes());
        }

        private Map<String, String> getPathParam(String path) {
            if (path.isEmpty()) {
                return null;
            }
            String[] urlParts = path.split("\\?");
            if (urlParts.length == 1) {
                return null;
            }
            return getMapParam(urlParts[1]);
        }

        private Map<String, String> getMapParam(String data) {
            final HashMap<String, String> paramsMap = new HashMap<>();
            String[] params = data.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                paramsMap.put(URLDecoder.decode(keyValue[0]), URLDecoder.decode(keyValue[1]));
            }
            return paramsMap;
        }


        private static Path getFilePath(String path) {
            if ("/".equals(path)) {
                path = "/index.html";
            }
            return Paths.get("/tmp/www", path);
        }

        private void sendResponse(SocketChannel channel, String status, String contentType, byte[] content)
                throws Exception {
            String lineSeparator = System.lineSeparator();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.put(("HTTP/1.1 " + status + " " + lineSeparator).getBytes());
            byteBuffer.put(("ContentType: " + contentType + lineSeparator).getBytes());
            byteBuffer.put(("Date: " + new Date() + lineSeparator).getBytes());
            byteBuffer.put(lineSeparator.getBytes());
            byteBuffer.put(content);

            byteBuffer.flip();
            channel.write(byteBuffer);
            byteBuffer.clear();
            channel.close();        // response 马上关闭
        }
    }
}
