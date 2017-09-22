package fault;

import io.opentracing.propagation.TextMap;
import okhttp3.Request;

import java.util.Iterator;
import java.util.Map;

public class FaultAdapter implements TextMap {

    private Request.Builder requestBuilder;

    public FaultAdapter(Request.Builder request) {
        this.requestBuilder = request;
    }

    @Override
    public Iterator<Map.Entry<String, String>> iterator() {
        throw new UnsupportedOperationException("Should be used only with tracer#inject()");
    }

    @Override
    public void put(String key, String value) {
        requestBuilder.addHeader(key, value);
    }
}