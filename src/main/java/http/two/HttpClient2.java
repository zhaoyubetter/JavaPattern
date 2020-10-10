package http.two;

import org.junit.Test;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户端
 */
public class HttpClient2 {

    String url = "http://localhost:8899";


    /**
     * The Carriage Return ASCII character value.
     */
    private static final byte CR = 0x0D;

    /**
     * The Line Feed ASCII character value.
     */
    private static final byte LF = 0x0A;

    final String boundary = "--------------------------082027979888165320628853";
    final String twoHyphens = "--";
    final byte[] FIELD_SEPARATOR = {CR, LF};

    private static final MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();

    @Test
    public void doGet() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        int respCode = connection.getResponseCode();
        if (respCode == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = connection.getInputStream();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))) {
                StringBuilder response = new StringBuilder();
                String currentLine;
                while ((currentLine = in.readLine()) != null) {
                    response.append(currentLine);
                }
                System.out.println("Response message:" + response.toString());
            }
        } else {
            InputStream inputStream = connection.getInputStream();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))) {
                StringBuilder response = new StringBuilder();
                String currentLine;
                while ((currentLine = in.readLine()) != null) {
                    response.append(currentLine);
                }
                System.err.println("Request error:" + response.toString());
            }
        }
    }

    @Test
    public void doPost() throws Exception {
        URL url = new URL("http://localhost:8899/");
        HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(3000);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);

        httpURLConnection.setRequestMethod("POST");
        StringBuffer stringBuffer = new StringBuffer();
        Map<Object,Object> params = new HashMap<>();
        params.put("userName", "mmt");
        params.put("userPassword","123456");
        for(Map.Entry<Object,Object> entry:params.entrySet()){
            stringBuffer.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        stringBuffer.deleteCharAt(stringBuffer.length()-1);
        try(OutputStream outputStream=httpURLConnection.getOutputStream()){
            outputStream.write(stringBuffer.toString().getBytes());
            try(InputStream inputStream = httpURLConnection.getInputStream()){
                String result=streamToString(inputStream,"utf-8");
                System.out.println("Receiving:"+result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * chunked
     */
    @Test
    public void doPostWithChunked() throws Exception {
        URL url = new URL("http://localhost:8899/");
        HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(3000);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setChunkedStreamingMode(-1);

        File file = new File("/Users/better/Documents/my/english/Engilish/L10讲解.mp3");

        httpURLConnection.addRequestProperty("Transfer-Encoding", "chunked");       // chunked
        httpURLConnection.setRequestProperty("Content-Type", "" + fileTypeMap.getContentType(file));

        // 使用一个大文件

        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        int chunedsSize = 1024 * 20; // 5m


        // 这里发送请求多了一个 1b 不知道怎么回事
        /*
        POST / HTTP/1.1
        Content-Type: application/octet-stream
        User-Agent: Java/1.8.0_211
        Host: localhost:8899
        Connection: keep-alive
        Transfer-Encoding: chunked

        1b
        6
        better
         */
        // 写入流
        OutputStream request = (httpURLConnection.getOutputStream());
        ByteBuffer byteBuffer = ByteBuffer.allocate(chunedsSize);
        byteBuffer.clear();

//        request.write("6".getBytes());
//        request.write(FIELD_SEPARATOR);
//        request.write("better".getBytes());
//        request.write(FIELD_SEPARATOR);
//        request.write("6".getBytes());
//        request.write(FIELD_SEPARATOR);
//        request.write("zhaoyu".getBytes());
//        request.write(FIELD_SEPARATOR);


                final FileChannel channel = randomAccessFile.getChannel();
//
//        /*
//            115\r\n
//            data.....\r\r
//            0\r\n
//            \r\n
//         */
        while(channel.read(byteBuffer) > 0) {
            request.write(("" + chunedsSize).getBytes());
            request.write(FIELD_SEPARATOR);
            byte[] bytes = new byte[byteBuffer.position()];
            byteBuffer.flip();
            byteBuffer.get(bytes);
            request.write(bytes);
            request.flush();
            request.write(FIELD_SEPARATOR);
            byteBuffer.clear();
        }

        request.write("0".getBytes());
        request.write(FIELD_SEPARATOR);
        request.write(FIELD_SEPARATOR);
        request.flush();

        StringBuffer stringBuffer = new StringBuffer();
        try(OutputStream outputStream=httpURLConnection.getOutputStream()){
            outputStream.write(stringBuffer.toString().getBytes());
            try(InputStream inputStream = httpURLConnection.getInputStream()){
                String result=streamToString(inputStream,"utf-8");
                System.out.println("Receiving:"+result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String streamToString(InputStream inputStream,String encodeType){
        String resultString = null ;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int len;
        byte data[]=new byte[1024];
        try {
            while((len=inputStream.read(data))!=-1){
                byteArrayOutputStream.write(data,0,len);
            }
            byte[] allData = byteArrayOutputStream.toByteArray();
            resultString = new String(allData,encodeType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultString ;
    }
}
