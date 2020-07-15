/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.srgsf.tvtime;

import com.squareup.moshi.JsonReader;
import okhttp3.*;
import okio.BufferedSource;
import retrofit2.Invocation;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Internal interceptor that helps with api specific handling.
 *
 * @author srgsf
 * @since 1.0
 */
public class ApiInterceptor implements Interceptor {

    private static final String RESULT_FIELD = "result";
    private static final String OK = "OK";
    private static final String AUTH_HEADER = "TVST_ACCESS_TOKEN";
    private static final String APPLICATION_JSON = "application/json";
    private final Supplier<String> accessTokenSupplier;

    /**
     * Create new instance.
     *
     * @param accessTokenSupplier OAuth 2.0 token supplier.
     */
    public ApiInterceptor(Supplier<String> accessTokenSupplier) {
        this.accessTokenSupplier = accessTokenSupplier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        ApiMethod api = Optional.ofNullable(request.tag(Invocation.class)).map(Invocation::method)
                .map(method -> method.getAnnotation(ApiMethod.class)).orElse(null);

        if (api == null) {
            return chain.proceed(request);
        }

        if (api.secured()) {
            request = request.newBuilder().header(AUTH_HEADER, accessTokenSupplier.get()).build();
        }

        Response response = chain.proceed(request);

        if (!response.isSuccessful()) {
            return response;
        }

        Optional<ResponseBody> body = Optional.ofNullable(response.body());

        if (!body.map(ResponseBody::contentType).map(MediaType::toString)
                .filter(APPLICATION_JSON::equals).isPresent()) {
            return response;
        }

        BufferedSource source = body.map(ResponseBody::source).get();
        source.request(Long.MAX_VALUE);
        JsonReader reader = JsonReader.of(source.getBuffer().clone());
        if (!(reader.hasNext() || reader.peek() == JsonReader.Token.BEGIN_OBJECT)) {
            return response;
        }
        reader.beginObject();
        if (reader.peek() != JsonReader.Token.NAME) {
            return response;
        }
        int level = 0;
        while (reader.hasNext()) {
            JsonReader.Token token = reader.peek();
            switch (token) {
                case BEGIN_OBJECT:
                    reader.beginObject();
                    ++level;
                    break;
                case END_OBJECT:
                    reader.endObject();
                    --level;
                    break;
                case NAME:
                    String name = reader.nextName();
                    if (level == 0 && RESULT_FIELD.equals(name)) {
                        String res = reader.nextString();
                        if (OK.equals(res)) {
                            return response;
                        }
                        return response.newBuilder().code(HttpURLConnection.HTTP_BAD_REQUEST).build();
                    }
                    break;
                default:
                    reader.skipValue();
            }
        }
        return response;
    }
}
