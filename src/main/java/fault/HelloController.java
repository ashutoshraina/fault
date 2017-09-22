package fault;

import io.opentracing.Tracer;
import io.opentracing.contrib.okhttp3.RequestBuilderInjectAdapter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class HelloController {

    @Autowired
    private OkHttpClient client;

    @Qualifier("zipkinTracer")
    @Autowired
    private Tracer tracer;

    @RequestMapping("/hello")
    public String hello(HttpServletRequest httpServletRequest) {
        return "Hello";
    }

    @RequestMapping("/chaining")
    public String chaining() throws IOException {

        String url = "http:localhost:8090/hello";

        Request.Builder builder = new Request.Builder()
                .url(url);
        RequestBuilderInjectAdapter injectAdapter = new RequestBuilderInjectAdapter(builder);
        injectAdapter.put("X-B3-FaultType", "delay");
        injectAdapter.put("X-B3-Service", "tault");

        Response response = client.newCall(builder.build()).execute();
        return response.body().string();

    }
}