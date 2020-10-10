package http;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpClient {
    public static void main(String[] args) {
        try {
            httpGet();
//            httpPost();
        } catch (IOException e) {
            System.err.println("Connection interrupted!");
        }
    }

    private static void httpGet() throws IOException {
        HttpURLConnection connection = (HttpURLConnection)new URL("http://localhost:8090/").openConnection();
        int responseCode = connection.getResponseCode();
        if (HttpURLConnection.HTTP_OK == responseCode) {
            InputStream inputStream = connection.getInputStream();
            try(BufferedReader in = new BufferedReader( new InputStreamReader(inputStream))){
                StringBuilder response = new StringBuilder();
                String currentLine;
                while ((currentLine = in.readLine()) != null) {
                    response.append(currentLine);
                }
                System.out.println("Response message:"+response.toString());
            }
        } else {
            InputStream inputStream = connection.getInputStream();
            try(BufferedReader in = new BufferedReader( new InputStreamReader(inputStream))){
                StringBuilder response = new StringBuilder();
                String currentLine;
                while ((currentLine = in.readLine()) != null) {
                    response.append(currentLine);
                }
                System.err.println("Request error:"+response.toString());
            }
        }
    }

    /**
     * with
     * @throws IOException
     */
    private static void httpPostChunked() throws IOException {

    }

    private static void httpPost() throws IOException{
        URL url = new URL("http://localhost:8090/");
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
