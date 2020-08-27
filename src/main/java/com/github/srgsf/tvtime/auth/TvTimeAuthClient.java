/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.srgsf.tvtime.auth;

import com.github.srgsf.tvtime.ApiInterceptor;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Optional;

/**
 * OAuth 2.0 authentication client. Supports web application and limited device flows.
 *
 * @author srgsf
 * @since 1.0
 */
public class TvTimeAuthClient {

    /**
     * <a href="https://tvtime.com">tvtime.com</a> OAuth 2.0 base url.
     */
    public static final String AUTH_URL = "https://api.tvtime.com/v1/oauth/";

    /**
     * Error message that is returned on token polling request.
     */
    public static final String ERR_AUTHORIZATION_PENDING = "Authorization pending";
    /**
     * Error message that is returned on token polling request.
     */
    public static final String ERR_SLOW_DOWN = "Slow down";
    private Authentication authentication;
    private final Retrofit retrofit;
    private final OkHttpClient httpClient;
    private final String clientId;
    private final String clientSecret;

    private Converter<ResponseBody, AuthResponse> converter;

    /**
     * Base url that is used with current instance.
     */
    public final HttpUrl baseUrl;

    /**
     * Builder is used to configure and create instances of {@link TvTimeAuthClient}.
     * To reconfigure call {@link TvTimeAuthClient#newBuilder()}
     *
     * @author srgsf
     *  @since 1.0
     */
    public static class Builder {
        private String url;
        private OkHttpClient client;
        private Retrofit retrofit;
        private String clientId;
        private String clientSecret;

        public Builder() {
        }

        Builder(TvTimeAuthClient client) {
            url = client.baseUrl.toString();
            this.client = client.httpClient;
            retrofit = client.retrofit;
            clientId = client.clientId;
            clientSecret = client.clientSecret;
        }

        public Builder baseUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder retrofit(Retrofit retrofit) {
            this.retrofit = retrofit;
            return this;
        }

        public Builder client(OkHttpClient client) {
            this.client = client;
            return this;
        }

        /**
         * @param clientId The client ID you received from TV Time when you registered.
         * @param secret   The client secret you received from TV Time when you registered.
         * @return {@link Builder}
         */
        public Builder clientCredentials(String clientId, String secret) {
            this.clientId = clientId;
            clientSecret = secret;
            return this;
        }

        /**
         * Creates {@link TvTimeAuthClient} instance.
         *
         * @return {@link TvTimeAuthClient}
         * @throws IllegalStateException if API client credentials are not provided.
         */
        public TvTimeAuthClient build() {
            final HttpUrl baseUrl = HttpUrl.get(url == null ? AUTH_URL : url);
            if (clientId == null || clientSecret == null) {
                throw new IllegalStateException("client credentials must be provided.");
            }
            Retrofit.Builder retrofitBuilder = retrofit == null ? new Retrofit.Builder() :
                    retrofit.newBuilder();
            retrofitBuilder.baseUrl(baseUrl);


            if (retrofitBuilder.converterFactories().stream().noneMatch(c -> c instanceof MoshiConverterFactory)) {
                retrofitBuilder.addConverterFactory(MoshiConverterFactory.create());
            }

            if (retrofitBuilder.converterFactories().stream().noneMatch(c -> c instanceof ResponseConverterFactory)) {
                retrofitBuilder.converterFactories().add(0, new ResponseConverterFactory());
            }

            OkHttpClient.Builder clientBuilder = client == null ? new OkHttpClient.Builder() : client.newBuilder();

            if (clientBuilder.interceptors().stream().noneMatch(i -> i instanceof ApiInterceptor)) {
                clientBuilder.addInterceptor(new ApiInterceptor(() -> null));
            }
            client = clientBuilder.build();
            retrofitBuilder.client(client);
            return new TvTimeAuthClient(baseUrl, clientId, clientSecret, client, retrofitBuilder.build());
        }
    }

    private TvTimeAuthClient(HttpUrl baseUrl,
                             String clientId,
                             String clientSecret,
                             OkHttpClient httpClient,
                             Retrofit retrofit) {
        this.httpClient = httpClient;
        this.retrofit = retrofit;
        this.baseUrl = baseUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    private Authentication authentication() {
        if (authentication == null) {
            authentication = retrofit.create(Authentication.class);
        }
        return authentication;
    }

    /**
     * Retrieves OAuth 2.0 access token using authorization code.
     *
     * @param authorizationCode code received as a result of authorization request.
     * @param redirectUri       uri that was used in a authorization request.
     * @return OAuth 2.0 access token.
     * @throws IOException if token request fails.
     */
    public Response<String> accessToken(String authorizationCode, String redirectUri) throws IOException {
        return accessTokenCall(authorizationCode, redirectUri).execute();
    }

    /**
     * Retrieves OAuth 2.0 access token using authorization code.
     *
     * @param authorizationCode code received as a result of authorization request.
     * @param redirectUri       uri that was used in a authorization request.
     * @return OAuth 2.0 access token.
     */
    public Call<String> accessTokenCall(String authorizationCode, String redirectUri) {
        return authentication().tokenViaAuthorizationCode(
                authorizationCode,
                clientId,
                clientSecret,
                redirectUri);
    }

    /**
     * Requests new device code.
     *
     * @return device code for polling TV Time's authorization server
     * @throws IOException if device code request fails.
     */
    public Response<DeviceCode> deviceCode() throws IOException {
        return deviceCodeCall().execute();
    }

    /**
     * Requests new device code.
     *
     * @return device code for polling TV Time's authorization server
     */
    public Call<DeviceCode> deviceCodeCall() {
        return authentication().deviceCode(clientId);
    }

    /**
     * Retrieves OAuth 2.0 access token using authorization code.     *
     *
     * @param code code received as a result of device code request.
     * @return OAuth 2.0 access token.
     * @throws IOException if token request fails.
     */
    public Response<String> accessToken(String code) throws IOException {
        return accessTokenCall(code).execute();
    }

    /**
     * Retrieves OAuth 2.0 access token using authorization code.     *
     *
     * @param code code received as a result of device code request.
     * @return OAuth 2.0 access token.
     */
    public Call<String> accessTokenCall(String code) {
        return authentication().tokenViaDeviceCode(
                clientId,
                clientSecret,
                code);
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
            converter = retrofit.responseBodyConverter(AuthResponse.class, new Annotation[0]);
        }
        return Optional.ofNullable(converter.convert(errorBody)).map(r -> r.message).orElse(null);
    }

    /**
     * Use this if you need to reconfigure auth client.
     *
     * @return copy of {@link Builder} that was used to create current auth client instance.
     */
    public Builder newBuilder() {
        return new Builder(this);
    }

    /**
     * Helper method for authorization request generation.
     *
     * @param clientId    Api client id.
     * @param redirectUri Api redirect uri.
     * @param state       An unguessable random string. It is used to protect against cross-site request forgery attacks.
     * @return state request url.
     */
    public static HttpUrl authorizationRequestUrl(String clientId, String redirectUri, String state) {
        return HttpUrl.get("https://www.tvtime.com/oauth/authorize").newBuilder()
                .addQueryParameter("client_id", clientId)
                .addQueryParameter("redirect_uri", redirectUri)
                .addQueryParameter("state", state).build();

    }
}
