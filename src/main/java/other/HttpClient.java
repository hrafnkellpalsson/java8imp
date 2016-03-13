package other;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class HttpClient {
    public void makeCall() {
        // Non-fluent style so we see intermediary types
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://nytimes.com");
        Invocation.Builder builder = target.request(MediaType.TEXT_HTML);
        builder.header("some-header", "true");
        Response response1 = builder.get();
        System.out.println("Status: " + response1.getStatus());
        String content = response1.readEntity(String.class);
        System.out.println("Trimmed content: " + content.substring(0, 100));

        // Now in fluent style
        Response response2 = ClientBuilder.newClient()
                .target("http://nytimes.com")
                .request(MediaType.TEXT_HTML)
                .header("some-header", "true")
                .get();
        System.out.println("Status: " + response2.getStatus());
    }
}
