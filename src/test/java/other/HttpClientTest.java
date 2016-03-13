package other;

import org.testng.annotations.Test;

public class HttpClientTest {
    @Test
    public void testMakeCall() {
        HttpClient httpClient = new HttpClient();
        httpClient.makeCall();
    }
}
