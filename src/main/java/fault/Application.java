package fault;


import brave.Tracing;
import brave.opentracing.BraveTracer;
import brave.sampler.Sampler;
import io.opentracing.Tracer;
import io.opentracing.contrib.okhttp3.TracingInterceptor;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import zipkin.Span;
import zipkin.reporter.AsyncReporter;
import zipkin.reporter.Encoding;
import zipkin.reporter.okhttp3.OkHttpSender;

import java.util.Arrays;


@SpringBootApplication
public class Application {

    @Bean
    public Tracer zipkinTracer() {
        OkHttpSender okHttpSender = OkHttpSender.builder()
                .encoding(Encoding.JSON)
                .endpoint("http://localhost:9411/api/v1/spans")
                .build();
        AsyncReporter<Span> reporter = AsyncReporter.builder(okHttpSender).build();
        Tracing braveTracer = Tracing.newBuilder()
                .localServiceName("fault")
                .reporter(reporter)
                .traceId128Bit(true)
                .sampler(Sampler.ALWAYS_SAMPLE)
                .build();
//        return new CowardTracer(BraveTracer.create(braveTracer));
        return BraveTracer.create(braveTracer);
    }

    @Bean
    public OkHttpClient httpClient(@Qualifier("zipkinTracer") Tracer tracer) {
        TracingInterceptor tracingInterceptor = new TracingInterceptor(tracer, Arrays.asList());
//        FaultInterceptor faultInterceptor = new FaultInterceptor(new CowardTracer(tracer));

        return new OkHttpClient.Builder()
                .addInterceptor(tracingInterceptor)
//                .addInterceptor(faultInterceptor)
                .addNetworkInterceptor(tracingInterceptor)
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}