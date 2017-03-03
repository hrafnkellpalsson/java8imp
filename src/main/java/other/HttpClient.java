package other;

import org.glassfish.jersey.client.ChunkedInput;
import org.glassfish.jersey.client.rx.java8.RxCompletionStage;
import org.glassfish.jersey.client.rx.rxjava.RxObservable;
import rx.Observable;

import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class HttpClient {
    public void getSync() {
        // Non-fluent style so we see intermediate types
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://nytimes.com");
        Invocation.Builder builder = target.request(MediaType.TEXT_HTML);
        builder.header("some-header", "true");
        Response response1 = builder.get();
        System.out.println("Status 1: " + response1.getStatus());
        String content = response1.readEntity(String.class);
        System.out.println("Trimmed content: " + content.substring(0, 100));

        // Now in fluent style
        Response response2 = ClientBuilder.newClient()
                .target("http://nytimes.com")
                .request(MediaType.TEXT_HTML)
                .header("some-header", "true")
                .get();
        System.out.println("Status 2: " + response2.getStatus());

        // Just to show path() and queryParam()
        Response response3 = ClientBuilder.newClient()
                .target("http://nytimes.com")
                .path("section/science")
                .queryParam("action", "click")
                .request(MediaType.TEXT_HTML)
                .header("some-header", "true")
                .get();
        System.out.println("Status 3: " + response3.getStatus());
    }

    public void getAsync() throws ExecutionException, InterruptedException {
        // Non-fluent style so we see intermediate types
        Client client = ClientBuilder.newClient();
        final AsyncInvoker asyncInvoker = client.target("http://nytimes.com")
                .request().async();
        Future<Response> future1 = asyncInvoker.get();
        // We could potentially do some more work here, before blocking by calling getSync()
        Response response1 = future1.get();
        System.out.println("Status 1: " + response1.getStatus());

        // Now in fluent style
        Future<Response> future2 = ClientBuilder.newClient()
                .target("http://nytimes.com")
                .request()
                .async()
                .get();
        Response response2 = future2.get();
        System.out.println("Status 2: " + response2.getStatus());
    }

    /**
     * Callbacks are possible!
     * ...but nested callbacks will of course be horrible :/
     * See example in chapter 'A Naive Approach' here https://jersey.java.net/documentation/latest/rx-client.html
     * Remember that when working with the Future interface in Java in general callbacks are also available through
     * the FutureTask implementation.
     */
    public void getAsyncWithCallback() throws ExecutionException, InterruptedException {
        // Fluent style with a call back
        Future<Response> future3 = ClientBuilder.newClient()
                .target("http://nytimes.com")
                .request()
                .async()
                .get(new InvocationCallback<Response>() {
                    @Override
                    public void completed(Response response) {
                        System.out.println("Status 3: " + response.getStatus());
                    }
                    @Override
                    public void failed(Throwable throwable) {
                        System.out.println("Invocation failed: " + throwable.getMessage());
                        throwable.printStackTrace();
                    }
                });
        // Block so that test won't exit before the callback has had a chance to execute.
        future3.get();
    }

    /**
     * This is not as exiting as I thought it was. The whole response is read at once in a blocking fashion from
     * the server. It's only the parsing of the answer into a type (here String) that happens in a chunked fashion.
     */
    public void getReadChunked() {
        final Response response = ClientBuilder.newClient()
                .target("http://nytimes.com")
                .request()
                .get();
        final ChunkedInput<String> chunkedInput = response.readEntity(new GenericType<ChunkedInput<String>>() {});
        String chunk;
        while ((chunk = chunkedInput.read()) != null) {
            System.out.println("Next chunk received: " + chunk);
        }
    }

    /**
     * Use the Netflix library for the Reactive Jersey Client API
     */
    public void getRxJava() {
        Observable<Response> observable = RxObservable.newClient()
                .target("http://nytimes.com")
                .request()
                .rx()
                .get();

        // observable.forEach() didn't work on its own, probably because it's async. toBlocking() did the trick.
        observable.toBlocking().forEach(System.out::println);

        // The correct way is probably to set up an Observer to getSync the value of the Observable.
        // Have a look at http://reactivex.io
    }

    /**
     * Use standard Java SE 8 for the Reactive Jersey Client API
     */
    public void getRxJava8() throws InterruptedException {
        CompletionStage<Response> future = RxCompletionStage.newClient()
                .target("http://nytimes.com")
                .request()
                .rx()
                .get();

        // The CompletableFuture method get() or stage() are not available on the interface so we do a Thread.sleep()
        // instead.
        future.thenAccept(r -> System.out.println(r.getStatus()));
        Thread.sleep(2000);
    }
}
