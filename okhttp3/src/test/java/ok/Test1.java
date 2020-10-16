
package ok;

import org.junit.Test;

import java.io.IOException;

public class Test1 {

    @Test
    public void test1() throws IOException {
        OkClient client = new OkClient();
        Request request = new Request.Builder()
                .url("www.baidu.com")
                .build();
        final Response response = client.newCall(request).execute();
        System.out.println(response);
    }
}
