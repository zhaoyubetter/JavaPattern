//package http.two.upload;
//
//import http.two.BufferedChannelReader;
//import http.two.MultiPart;
//import org.codehaus.groovy.runtime.wrappers.ByteWrapper;
//import org.yaml.snakeyaml.util.UriEncoder;
//
//import javax.activation.MimetypesFileTypeMap;
//import java.io.*;
//import java.net.InetSocketAddress;
//import java.net.URLDecoder;
//import java.nio.ByteBuffer;
//import java.nio.channels.*;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.*;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * Simple http server
// *
// * https://blog.csdn.net/cuidongdong1234/article/details/17083123
// *
// * https://blog.csdn.net/zuguorui/article/details/60145796
// *
// */
//public class HttpServer2 {
//    public static void main(String[] args) {
//        new SimpleServer().start();
//    }
//
//    static class SimpleServer {
//
//        private final String LINE_SEP = "\r\n";
//        private final String METHOD_GET = "GET";
//        private final String METHOD_POST = "POST";
//        // Content-Disposition: form-data; name="cccc"
//        private static final Pattern FORM_FIELD_FILE_PATTERN = Pattern.compile("([\\w-]+):\\s([\\w-]+);\\s(\\w+)=\"([^\"]+)\"(;\\s+(\\w+)=\"([^\"]+)\")?");
//        private final ExecutorService executors = Executors.newFixedThreadPool(10);
//
//        private static final MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
//
//        public void start() {
//            try {
//                Selector selector = Selector.open();
//
//                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
//                serverSocketChannel.configureBlocking(false);
//                serverSocketChannel.bind(new InetSocketAddress("localhost", 8899));
//                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
//                System.out.println("Start Server.");
//
//                while (true) {
//                    selector.select();          // blocking
//                    final Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
//                    while (iterator.hasNext()) {
//                        final SelectionKey selectionKey = iterator.next();
//                        if (selectionKey.isAcceptable()) {
//                            acceptChannel(selector, selectionKey);
//                        } else if (selectionKey.isReadable()) {
//                            response2(selectionKey);
//                        }
//                        iterator.remove();  // 必要的移除
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        // 关注读
//        private void acceptChannel(Selector selector, SelectionKey selectionKey) throws Exception {
//            final ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
//            SocketChannel socketChannel = serverSocketChannel.accept();
//            socketChannel.configureBlocking(false);
//            socketChannel.register(selector, SelectionKey.OP_READ); // 客户端关注读
//            System.out.println("one client is connected: " + socketChannel.getRemoteAddress());
//        }
//
//        private void response2(final SelectionKey selectionKey) throws Exception {
//            String method = "GET";
//            String path = "";
//            String version = "";
//            Map<String, String> headers;
//            Map<String, String> pathParam;
//            Map<String, String> bodyParam;
//            try {
//                SocketChannel client = (SocketChannel) selectionKey.channel();
//                BufferedChannelReader brReader = new BufferedChannelReader(client);
//                StringBuilder headerSb = new StringBuilder();
//                String statusLine = brReader.readLine();
//
//                // 1.status line parse
//                final String[] statuses = statusLine.split(" ");
//                method = statuses[0];
//                path = statuses[1];
//                version = statuses[2];
//                pathParam = getPathParam(path);
//
//                // 2. header area
//                String line = null;
//                while (!LINE_SEP.equals(line = brReader.readLine())) {
//                    if (null == statusLine) {
//                        statusLine = line;
//                    } else {
//                        headerSb.append(line);
//                    }
//                }
//                headers = getHeaderMap(headerSb.toString());
//
//                if (METHOD_GET.equals(method)) {
//
//                } else if (METHOD_POST.equals(method)) {
//                    // 3. parse parseBody
//                    parseBody(headers, brReader);
//                }
//                sendResponse(client, "200 ok", "text/html", "ok".getBytes());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        private void parseBody(final Map<String, String> headers, final BufferedChannelReader bufferedChannelReader)
//                throws IOException {
//            // checked if is Chunked
//            String transferEncoding = headers.get("transfer-encoding");
//            if("chunked".equalsIgnoreCase(transferEncoding)){
//                // 大文件分块上传
//                handlePostWithChunked(headers, bufferedChannelReader);
//                return;
//            }
//            long contentLength = headers.containsKey("content-length") ? Integer.parseInt(headers.get("content-length")) : 0;
//            String contentType = headers.containsKey("content-type") ? headers.get("content-type") : "";
//            ByteBuffer buffer = bufferedChannelReader.getBuffer();
//            byte[] bytes = null;
//            if (buffer.remaining() > 0) {
//                bytes = new byte[buffer.limit() - buffer.position()];
//                buffer.get(bytes, 0, bytes.length);
//            }
//
//            // form-bean
//            if (contentType.contains("multipart/form-data")) {
//                List<MultiPart> multiParts = new ArrayList<>();
//                String boundary = contentType.substring(contentType.indexOf("boundary") + 9);
//                MultipartStream multipart = new MultipartStream(bufferedChannelReader.getChannel(), boundary.getBytes("ISO-8859-1"), bytes);
//                boolean nextPart = multipart.skipPreamble();
//                while (nextPart) {
//                    MultiPart item = new MultiPart();
//                    // 1. header
//                    String header = multipart.readHeaders();
//                    parseMultiItemHeader(header, item);
//
//                    // 2. body content
//                    parseMultiItemContent(item, multipart);
//
//                    // 3. has next?
//                    nextPart = multipart.readBoundary();
//                    multiParts.add(item);
//                }
//                System.out.println(multiParts);
//            } else {
//                // all is text/plain
//                // application/x-www-form-urlencoded
//                final SocketChannel channel = bufferedChannelReader.getChannel();
//                final ByteArrayOutputStream baos = new ByteArrayOutputStream(contentLength > 0 ?
//                        ((int) contentLength) : 512);
//                if (bytes != null) {
//                    baos.write(bytes, 0, bytes.length);
//                }
//                ByteBuffer bs = ByteBuffer.allocate(512);
//                while (channel.read(bs) > 0) {
//                    bs.flip();
//                    byte[] read = new byte[bs.limit()];
//                    bs.get(read);
//                    baos.write(read);
//                    bs.clear();
//                }
//                // 在 decode 之前，应该先根据 contentType 进行 bodyContent 的内容分割
//                String bodyContent = URLDecoder.decode(new String(baos.toByteArray()), "utf-8");
//                System.out.println("contentType:" + contentType);
//                System.out.println(bodyContent);
//            }
//        }
//
//        private void handlePostWithChunked(final Map<String, String> headers, final BufferedChannelReader bufferedChannelReader) throws IOException {
//            // 编码规则
//            String contentType = headers.containsKey("content-type") ? headers.get("content-type") : "";
//            String line = "";
//            while((line = bufferedChannelReader.readLine() )!= null) {
//                System.out.println(line);
//            }
//
//            List<Chunk> list = new ArrayList<>();
////            while(bufferedChannelReader.hasNextChunk()) {
////                Chunk chunk = new Chunk();
////                int chunkSize = bufferedChannelReader.readChunkSize();
//////                bufferedChannelReader.readChunkData(chunkSize);
////            }
////            // 获取内容
//
//        }
//
//        private void parseMultiItemContent(MultiPart item, MultipartStream multipart) throws IOException {
//            if (!item.isFile) {
//                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                int size = multipart.readBodyData(outputStream);
//                item.value = new String(outputStream.toByteArray(), "utf-8");
//                item.length = size;
//            } else {
//                // upload files
//                File uploadFile = new File(item.filename);
//                FileOutputStream fos = new FileOutputStream(uploadFile);
//                item.length = multipart.readBodyData(fos);
//            }
//        }
//
//        private void parseMultiItemHeader(String header, MultiPart item) {
//            final String[] split = header.split(LINE_SEP);
//            Matcher matcher = null;
//            for (String line : split) {
//                if (line.startsWith("Content-Disposition")) {
//                    // Content-Disposition: form-data; name="abb"
//                    // Content-Disposition: form-data; name="file1"; filename="XmlReader.java"
//                    matcher = FORM_FIELD_FILE_PATTERN.matcher(line);
//                    if (matcher.find()) {
//                        item.name = matcher.group(4);
//                        String filename = matcher.group(7);
//                        if (filename != null) {
//                            item.isFile = true;
//                            item.filename = filename;
//                        }
//                    }
//                } else if (line.startsWith("Content-Type")) {
//                    //Content-Type: text/plain; charset=UTF-8
//                    Pattern pattern = Pattern.compile("([\\w-]+):\\s([^;]+);?\\s?((.+))?");
//                    matcher = pattern.matcher(line);
//                    if (matcher.find()) {
//                        item.contentType = matcher.group(2);
//                    }
//                }
//            }
//        }
//
//        private Map<String, String> getPathParam(String path) {
//            if (path.isEmpty()) {
//                return null;
//            }
//            String[] urlParts = path.split("\\?");
//            if (urlParts.length == 1) {
//                return null;
//            }
//            return getMapParam(urlParts[1]);
//        }
//
//        private Map<String, String> getMapParam(String data) {
//            final HashMap<String, String> paramsMap = new HashMap<>();
//            String[] params = data.split("&");
//            for (String param : params) {
//                String[] keyValue = param.split("=");
//                paramsMap.put(URLDecoder.decode(keyValue[0]), URLDecoder.decode(keyValue[1]));
//            }
//            return paramsMap;
//        }
//
//        private Map<String, String> getHeaderMap(String headerSb) {
//            final String[] lines = headerSb.split(LINE_SEP);
//            Map<String, String> headers = new HashMap<>();
//            for (int i = 0; i < lines.length; i++) {
//                if (lines[i] != null && !lines[i].isEmpty()) {
//                    final String[] h = lines[i].split(":");
//                    headers.put(h[0].toLowerCase().trim(), h[1].trim());
//                }
//            }
//            return headers;
//        }
//
//        private static Path getFilePath(String path) {
//            if ("/".equals(path)) {
//                path = "/index.html";
//            }
//            return Paths.get("/tmp/www", path);
//        }
//
//        private void sendResponse(SocketChannel channel, String status, String contentType, byte[] content)
//                throws Exception {
//            String lineSeparator = System.lineSeparator();
//            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
//            byteBuffer.put(("HTTP/1.1 " + status + " " + lineSeparator).getBytes());
//            byteBuffer.put(("ContentType: " + contentType + lineSeparator).getBytes());
//            byteBuffer.put(("Date: " + new Date() + lineSeparator).getBytes());
//            byteBuffer.put(lineSeparator.getBytes());
//            byteBuffer.put(content);
//
//            byteBuffer.flip();
//            channel.write(byteBuffer);
//            byteBuffer.clear();
//            channel.close();        // response 马上关闭
//        }
//    }
//}
//
//
//////////////////////
//       /*
//       以下代码作废
//       private void uploadFile(String filename, String boundary,
//                               BufferedChannelReader channelReader) throws FileNotFoundException {
//           final byte[] boundaryBytes = ("--" + boundary).getBytes();
//           int head = 0;
//           int tail = 0;
//           File uploadFile = new File(filename);
//           long fileSize = 0;
//           try (FileChannel uploadChannel = new FileOutputStream(uploadFile).getChannel()) {
//               int size = 0;
//               while ((size = channelReader.readBytes()) > 0) {
//                   byte[] arr = new byte[size];
//                   channelReader.getBuffer().get(arr);
//
//                   System.out.print(new String(arr));
//               }
//
////                char c = (char)channelReader.read();
////                while (null != (line = channelReader.readLine())) {
////                    // end the file
////                    if(line.startsWith("--" + boundary)) {
////                        break;
////                    }
////                    uploadChannel.write(ByteBuffer.wrap(line.getBytes()));
////                    System.out.print(line);
////                }
//           } catch (IOException e) {
//               e.printStackTrace();
//           }
//       }
//
//    private void handleMethodGet(SocketChannel client, String path) throws Exception {
//        Path filePath = getFilePath(path);
//        if (Files.exists(filePath)) {
//            String contentType = fileTypeMap.getContentType(filePath.toFile());
//            sendResponse(client, "200 OK", contentType, Files.readAllBytes(filePath));
//        } else {
//            // 404
//            byte[] notFoundContent = "<h1>Not found :(</h1>".getBytes();
//            sendResponse(client, "404 Not Found", "text/html", notFoundContent);
//        }
//    }
//
//    private void handleMethodPost(SocketChannel client, String path) throws Exception {
//        // 1.part 1: analyze path, such as ?a=b&b=c
//        final Map<String, String> pathParam = getPathParam(path);
//
//        // 2.part 2: analyze parseBody
//        ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
//        while (client.read(byteBuffer) > 0) {
//            byteBuffer.flip();
//            final String result = new String(byteBuffer.array());
//            System.out.println(result);
//            byteBuffer.clear();
//        }
//
//        sendResponse(client, "200 OK", "text/html", "ok".getBytes());
//    }
//
//        private void parseBody(final Map<String, String> headers, final BufferedChannelReader channelReader)
//                throws IOException {
//            long contentLength = headers.containsKey("content-length") ? Integer.parseInt(headers.get("content-length")) : 0;
//            String contentType = headers.containsKey("content-type") ? headers.get("content-type") : "";
//            // form-bean
//            if (contentType.contains("multipart/form-data")) {
//                List<MultiPart> multiParts = new ArrayList<>();
//                String boundary = contentType.substring(contentType.indexOf("boundary") + 9);
//                String line = null;
//                Matcher matcher = null;
//                MultiPart multiPart = null;
//                while (null != (line = channelReader.readLine())) {
//                    if (line.startsWith("--") && line.startsWith(boundary)) {
//                        multiPart = new MultiPart();
//                    } else if (line.startsWith("Content-Disposition")) {
//                        // Content-Disposition: form-data; name="abb"
//                        // Content-Disposition: form-data; name="file1"; filename="XmlReader.java"
//                        matcher = FORM_FIELD_FILE_PATTERN.matcher(line);
//                        if (matcher.find()) {
//                            multiPart.name = matcher.group(4);
//                            String filename = matcher.group(7);
//                            if (filename != null) {
//                                multiPart.isFile = true;
//                                multiPart.filename = filename;
//                            }
//                        }
//                    } else if (line.startsWith("Content-Type")) {
//                        //Content-Type: text/plain; charset=UTF-8
//                        Pattern pattern = Pattern.compile("([\\w-]+):\\s([^;]+);?\\s?((.+))?");
//                        matcher = pattern.matcher(line);
//                        if (matcher.find()) {
//                            multiPart.contentType = matcher.group(2);
//                        }
//                    } else if (LINE_SEP.equals(line)) {
//                        // 以下内容就是文件
//                        multiParts.add(multiPart);
//                        if (!multiPart.isFile) {
//                            line = channelReader.readLine();
//                            if (null == multiPart.value) {
//                                multiPart.value = line;
//                            } else {
//                                multiPart.value += line;
//                            }
//                        } else {
//                            uploadFile(multiPart.filename, boundary, channelReader);
//                        }
//                    }
//                }
//                System.out.println(multiParts);
//            } else {
//                // all is text/plain
//                // application/x-www-form-urlencoded
//            }
//        }
//         */