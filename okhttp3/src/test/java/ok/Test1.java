
package ok;

import org.junit.Test;

import java.io.IOException;

public class Test1 {

    @Test
    public void test1Sync() throws IOException {
        OkClient client = new OkClient();
        Request request = new Request.Builder()
                .url("www.baidu.com")
                .build();
        final Response response = client.newCall(request).execute();
        System.out.println(response.message);
        System.out.println(response.request.url);
    }

    @Test
    public void test2Async() throws InterruptedException {
        OkClient client = new OkClient();
        Request request = new Request.Builder()
                .url("www.baidu.com")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Async call failed.");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("thread name: " + Thread.currentThread().getName());
                System.out.println("Async call onResponse: " + response.message);
            }
        });

        // 休息一会
        Thread.sleep(2000);
    }
}
