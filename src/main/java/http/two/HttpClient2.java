package http.two;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 客户端
 */
public class HttpClient2 {

    String url = "http://localhost:8899";

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
    public void doPost() {
        System.out.println((int)System.lineSeparator().charAt(0));
    }
}
