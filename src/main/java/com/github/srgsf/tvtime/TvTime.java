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

import com.github.srgsf.tvtime.model.Emotion;
import com.github.srgsf.tvtime.service.TvTimeEpisode;
import com.github.srgsf.tvtime.service.TvTimeInfo;
import com.github.srgsf.tvtime.service.TvTimeShow;
import com.squareup.moshi.Moshi;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * TvTime client. Use {@link Builder} to configure and create instances.
 *
 * @author srgsf
 * @since 1.0
 */
public class TvTime {

    /**
     * Default API url.
     */
    public static final String API_URL = "https://api.tvtime.com/v1/";

    private final Retrofit retrofit;
    private final OkHttpClient httpClient;
    private final Supplier<String> accessTokenProvider;
    private final HttpUrl baseUrl;
    private Converter<ResponseBody, ApiResponse> converter;
    private ApiImpl apiImpl;

    /**
     * Builder is used to configure and create instances of {@link TvTime}.
     * To reconfigure call {@link TvTime#newBuilder()}
     *
     * @author srgsf
     *  @since 1.0
     */
    public static class Builder {
        private String url;
        private OkHttpClient client;
        private Retrofit retrofit;
        private Supplier<String> accessTokenProvider;


        public Builder() {

        }

        Builder(TvTime old) {
            url = old.baseUrl.toString();
            client = old.httpClient;
            retrofit = old.retrofit;
            accessTokenProvider = old.accessTokenProvider;
        }

        /**
         * Use custom url for Api.
         * {@link TvTime#API_URL} is used by default.
         *
         * @param url custom url
         * @return {@link Builder}
         */
        public Builder baseUrl(String url) {
            this.url = url;
            return this;
        }

        /**
         * Sets shared retrofit instance. Use this to save resources and improve performance.
         *
         * @param retrofit shared retrofit instance.
         * @return {@link Builder}
         */
        public Builder retrofit(Retrofit retrofit) {
            this.retrofit = retrofit;
            return this;
        }

        /**
         * Sets shared okHttp client instance. Use this to save resources and improve performance.
         *
         * @param client shared okHttp client instance
         * @return {@link Builder}
         */
        public Builder client(OkHttpClient client) {
            this.client = client;
            return this;
        }


        /**
         * Sets OAuth 2.0 authorization token provider.
         *
         * @param provider access token provider
         * @return {@link Builder}
         */
        public Builder tokenProvider(Supplier<String> provider) {
            this.accessTokenProvider = provider;
            return this;
        }

        /**
         * Builds new instance of {@link TvTime}.
         *
         * @return new instance of {@link TvTime}
         */
        public TvTime build() {
            if (accessTokenProvider == null) {
                throw new IllegalStateException("access token provider must be set");
            }
            final HttpUrl baseUrl = HttpUrl.get(url == null ? API_URL : url);
            OkHttpClient.Builder clientBuilder = client == null ? new OkHttpClient.Builder() : client.newBuilder();

            clientBuilder.interceptors().removeIf(i -> i instanceof ApiInterceptor);
            clientBuilder.addInterceptor(new ApiInterceptor(accessTokenProvider));

            Retrofit.Builder retrofitBuilder = retrofit == null ? new Retrofit.Builder() :
                    retrofit.newBuilder();
            retrofitBuilder.baseUrl(baseUrl);
            retrofitBuilder.client(clientBuilder.build());

            retrofitBuilder.converterFactories().removeIf(c -> c instanceof MoshiConverterFactory);
            Moshi moshi = new Moshi.Builder()
                    .add(new JsonAdapters.LocalDateAdapter())
                    .add(new JsonAdapters.LocalTimeAdapter())
                    .add(new Emotion.Adapter())
                    .build();
            retrofitBuilder.addConverterFactory(MoshiConverterFactory.create(moshi));

            if (retrofitBuilder.converterFactories().stream().noneMatch(c -> c instanceof RetrofitConverterFactory)) {
                retrofitBuilder.converterFactories().add(0, new RetrofitConverterFactory());
            }

            return new TvTime(baseUrl, client, retrofitBuilder.build(), accessTokenProvider);
        }
    }

    private TvTime(HttpUrl baseUrl,
                   OkHttpClient httpClient,
                   Retrofit retrofit,
                   Supplier<String> accessTokenProvider) {
        this.baseUrl = baseUrl;
        this.retrofit = retrofit;
        this.accessTokenProvider = accessTokenProvider;
        this.httpClient = httpClient;
    }

    /**
     * API base url that is configured for this instance.
     *
     * @return API base url.
     */
    public HttpUrl getBaseUrl() {
        return baseUrl;
    }

    /**
     * Returns copy of {@link Builder} that was used to create current instance.
     * Use this to reconfigure {@link TvTime}.
     *
     * @return {@link Builder}.
     */
    public Builder newBuilder() {
        return new Builder(this);
    }

    /**
     * Creates shows api.
     *
     * @return {@link TvTimeShow} instance.
     */
    public TvTimeShow shows() {
        return apiImpl();
    }

    /**
     * Creates episodes api.
     *
     * @return {@link TvTimeEpisode} instance.
     */
    public TvTimeEpisode episodes() {
        return apiImpl();
    }

    /**
     * Creates info api.
     *
     * @return {@link TvTimeInfo} instance.
     */
    public TvTimeInfo info() {
        return apiImpl();
    }

    private ApiImpl apiImpl() {
        synchronized (this) {
            if (apiImpl == null) {
                apiImpl = new ApiImpl(retrofit.create(ApiRetrofit.class), JsonAdapters.episodeRefAdapter());
            }
        }
        return apiImpl;
    }

    /**
     * Helper method to parse error response that contains error message.
     *
     * @param errorBody error body.
     * @return parsed error.
     * @throws IOException in case of i/o failure.
     */
    public String error(ResponseBody errorBody) throws IOException {
        if (converter == null) {
            converter = retrofit.responseBodyConverter(ApiResponse.class, new Annotation[0]);
        }
        return Optional.ofNullable(converter.convert(errorBody)).map(r -> r.message).orElse(null);
    }
}
