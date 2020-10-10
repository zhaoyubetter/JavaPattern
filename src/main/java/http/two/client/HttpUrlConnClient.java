package http.two.client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class HttpUrlConnClient {

    String url = "http://localhost:8899";
    HttpURLConnection connection;

    @Before
    public void before() throws IOException {
        connection = (HttpURLConnection) new URL(url).openConnection();
    }

    @After
    public void after() {
        if (connection != null) {
            connection.disconnect();
            connection = null;
        }
    }

    @Test
    public void testNormalPost() throws ProtocolException {
        connection.setConnectTimeout(3000);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        connection.setRequestMethod("POST");
        StringBuffer stringBuffer = new StringBuffer();
        Map<Object, Object> params = new HashMap<>();
        params.put("userName", "better照");
        params.put("userPassword", "123456");
        for (Map.Entry<Object, Object> entry : params.entrySet()) {
            stringBuffer.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        try (OutputStream outputStream = connection.getOutputStream()) {
            outputStream.write(stringBuffer.toString().getBytes());
            try (InputStream inputStream = connection.getInputStream()) {
                String result = streamToString(inputStream, "utf-8");
                System.out.println("Receiving:" + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMultiPart() throws IOException {
        connection.setConnectTimeout(3000);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");

        String boundary = "------------------" + Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
        connection.setRequestProperty("content-type", "multipart/form-data;  boundary=" + boundary);


        File textFile = new File(HttpUrlConnClient.class.getClassLoader().getResource("operator_test.txt").getFile());
        File binaryFile = new File(HttpUrlConnClient.class.getClassLoader().getResource("operator_test.xls").getFile());


        String CRLF = "\r\n"; // Line separator required by multipart/form-data.

        try (
                OutputStream output = connection.getOutputStream();
        ) {
            // Send normal param.
            output.write(("--" + boundary + CRLF).getBytes());
            output.write(("Content-Disposition: form-data; name=\"param\"" + CRLF).getBytes());
            output.write(("Content-Type: text/plain;" + CRLF).getBytes());
            output.write(CRLF.getBytes());
            output.write("param value".getBytes());
            output.write(CRLF.getBytes());
            output.flush();

            // Send text file.
            output.write(("--" + boundary + CRLF).getBytes());
            output.write(("Content-Disposition: form-data; name=\"textFile\"; filename=\"" + textFile.getName() + "\"" + CRLF).getBytes());
            output.write(("Content-Type: text/plain;"+ CRLF).getBytes()); // Text file itself must be saved in this charset!
            output.write(("Content-Length: " + textFile.length() + CRLF).getBytes()); // Text file itself must be saved in this charset!
            output.write(CRLF.getBytes());
            output.write((Files.readAllBytes(textFile.toPath())));
            output.write((CRLF).getBytes());
            output.flush(); // CRLF is important! It indicates end of boundary.

            // Send binary file.
            output.write(("--" + boundary + CRLF).getBytes());
            output.write(("Content-Disposition: form-data; name=\"binaryFile\"; filename=\"" + binaryFile.getName() + "\"" + CRLF).getBytes());
            output.write(("Content-Type: " + URLConnection.guessContentTypeFromName(binaryFile.getName()) + CRLF).getBytes());
            output.write(("Content-Transfer-Encoding: binary" + CRLF).getBytes());
            output.write(("Content-Length: " + binaryFile.length() + CRLF).getBytes()); // Text file itself must be saved in this charset!
            output.write(CRLF.getBytes());
            output.write(Files.readAllBytes(binaryFile.toPath()));
            output.write(CRLF.getBytes());
            output.flush(); // CRLF is important! It indicates end of boundary.

            // End of multipart/form-data.
            output.write(("--" + boundary + "--" + CRLF).getBytes());
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Request is lazily fired whenever you need to obtain information about response.
        int responseCode = connection.getResponseCode();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while (null != (line = reader.readLine())) {
            System.out.println(line);
        }
        connection.disconnect();
        System.out.println(responseCode); // Should be 200
    }

    @Test
    public void testUpload() {

    }

    private static String streamToString(InputStream inputStream, String encodeType) {
        String resultString = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int len;
        byte data[] = new byte[1024];
        try {
            while ((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
            byte[] allData = byteArrayOutputStream.toByteArray();
            resultString = new String(allData, encodeType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultString;
    }
}
