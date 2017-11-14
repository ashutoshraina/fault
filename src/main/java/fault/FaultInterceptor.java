package fault;

import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.contrib.okhttp3.RequestBuilderInjectAdapter;
import io.opentracing.propagation.Format;
import io.opentracing.tag.Tags;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.logging.Logger;


public class FaultInterceptor {
//
//    private static final Logger log = Logger.getLogger(FaultInterceptor.class.getSimpleName());
//
//    private CowardTracer tracer;
//
//    public FaultInterceptor(CowardTracer tracer) {
//        this.tracer = tracer;
//    }
//
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//        Response response;
//
//        // application interceptor?
//        if (chain.connection() == null) {
//            Tracer.SpanBuilder spanBuilder = tracer.buildSpan(chain.request().method())
//                    .withTag(Tags.SPAN_KIND.getKey(), Tags.SPAN_KIND_CLIENT);
//            Request.Builder requestBuilder = chain.request().newBuilder();
//            Span span = spanBuilder.startManual();
//            tracer.injectFault(span.context(), Format.Builtin.HTTP_HEADERS, new RequestBuilderInjectAdapter(requestBuilder), "tault", FaultTypes.DELAY);
//
//            try {
//                response = chain.proceed(requestBuilder.build());
//            } catch (Throwable ex) {
//                throw ex;
//
//            } finally {
//                span.finish();
//            }
//
//        } else {
//            response = chain.proceed(chain.request());
//
//        }
//
//        return response;
//    }

}
