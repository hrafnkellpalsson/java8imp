package other;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class HttpClientTest {
  @Test
  public void testGetSync() {
    HttpClient httpClient = new HttpClient();
    httpClient.getSync();
  }

  @Test
  public void testGetAsync() throws ExecutionException, InterruptedException {
    HttpClient httpClient = new HttpClient();
    httpClient.getAsync();
  }

  @Test
  public void testGetAsyncWithCallback() throws ExecutionException, InterruptedException {
    HttpClient httpClient = new HttpClient();
    httpClient.getAsyncWithCallback();
  }

  @Test
  public void getGetReadChunked() {
    HttpClient httpClient = new HttpClient();
    httpClient.getReadChunked();
  }

  @Test
  public void getGetRxJava() {
    HttpClient httpClient = new HttpClient();
    httpClient.getRxJava();
  }

  @Test
  public void getGetRxJava8() throws InterruptedException {
    HttpClient httpClient = new HttpClient();
    httpClient.getRxJava8();
  }
}
