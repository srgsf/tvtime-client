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

import com.github.srgsf.tvtime.auth.DeviceCode;
import com.github.srgsf.tvtime.auth.TvTimeAuthClient;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;
import java.net.HttpURLConnection;

class AuthClientTest {

    private static MockWebServer webServer;
    private static final String CLIENT_ID = "API_CLIENT_USER";
    private static final String CLIENT_SECRET = "API_CLIENT_SECRET";
    private static TvTimeAuthClient client;

    @BeforeAll
    static void setup() throws IOException {
        webServer = new MockWebServer();
        webServer.start();
        client = new TvTimeAuthClient.Builder()
                .clientCredentials(CLIENT_ID, CLIENT_SECRET)
                .baseUrl(webServer.url("/").toString())
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new HttpLoggingInterceptor()
                                .setLevel(HttpLoggingInterceptor.Level.BODY)).build())
                .build();
    }

    @AfterAll
    static void tearDown() throws IOException {
        webServer.shutdown();
    }


    @Test
    void authUrl() {
        String random = "123String";
        HttpUrl url = TvTimeAuthClient.authorizationRequestUrl("clientId", "urn::urn", random);
        Assertions.assertEquals("clientId", url.queryParameter("client_id"));
        Assertions.assertEquals("urn::urn", url.queryParameter("redirect_uri"));
        Assertions.assertEquals(random, url.queryParameter("state"));
        Assertions.assertEquals("/oauth/authorize", url.encodedPath());
        Assertions.assertEquals("www.tvtime.com", url.host());
        Assertions.assertEquals("https", url.scheme());
    }

    @Test
    void testAuthCode() throws IOException, InterruptedException {
        webServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(Utils.readFromFile("json/access_token.json")));
        Response<String> response = client.accessToken("123", "urn::urn");
        RecordedRequest req = webServer.takeRequest();
        Assertions.assertFalse(Utils.checkAuthorization(req));
        Assertions.assertTrue(response.isSuccessful());
        Assertions.assertNotNull(response.body());
        Assertions.assertEquals("ACCESS_TOKEN", response.body());
    }

    @Test
    void testDeviceCodeAuth() throws IOException, InterruptedException {
        webServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(Utils.readFromFile("json/access_token.json")));
        Response<String> response = client.accessToken("234");
        RecordedRequest req = webServer.takeRequest();
        Assertions.assertFalse(Utils.checkAuthorization(req));
        Assertions.assertTrue(response.isSuccessful());
        Assertions.assertNotNull(response.body());
        Assertions.assertEquals("ACCESS_TOKEN", response.body());
    }

    @Test
    void testDeviceCode() throws IOException, InterruptedException {
        webServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(Utils.readFromFile("json/device_code.json")));
        Response<DeviceCode> response = client.deviceCode();
        RecordedRequest req = webServer.takeRequest();
        Assertions.assertFalse(Utils.checkAuthorization(req));
        Assertions.assertTrue(response.isSuccessful());
        Assertions.assertNotNull(response.body());
        DeviceCode code = response.body();

        Assertions.assertEquals("DEVICE_CODE", code.deviceCode);
        Assertions.assertEquals("USER_CODE", code.userCode);
        Assertions.assertEquals(900, code.expiresIn);
        Assertions.assertEquals(5, code.interval);
    }

    @Test
    void testError() throws IOException, InterruptedException {
        webServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(Utils.readFromFile("json/auth_pending.json")));
        Response<String> response = client.accessToken("234");
        RecordedRequest req = webServer.takeRequest();
        Assertions.assertFalse(Utils.checkAuthorization(req));
        Assertions.assertFalse(response.isSuccessful());
        Assertions.assertNotNull(response.errorBody());
        Assertions.assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.code());
        String error = client.error(response.errorBody());
        Assertions.assertEquals(TvTimeAuthClient.ERR_AUTHORIZATION_PENDING, error);
    }
}
