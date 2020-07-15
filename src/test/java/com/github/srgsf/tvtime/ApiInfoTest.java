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

import com.github.srgsf.tvtime.model.Episode;
import com.github.srgsf.tvtime.model.Request;
import com.github.srgsf.tvtime.model.Show;
import com.github.srgsf.tvtime.model.User;
import com.github.srgsf.tvtime.service.TvTimeInfo;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

class ApiInfoTest {
    static TvTime client;
    static MockWebServer webServer;
    static TvTimeInfo api;

    @BeforeAll
    static void setup() throws IOException {
        webServer = new MockWebServer();
        webServer.start();
        client = new TvTime.Builder()
                .baseUrl(webServer.url("/").toString())
                .tokenProvider(() -> "TOKEN")
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new HttpLoggingInterceptor()
                                .setLevel(HttpLoggingInterceptor.Level.BODY)).build())
                .build();
        api = client.info();
    }

    @AfterAll
    static void tearDown() throws IOException {
        webServer.shutdown();
    }

    @Test
    void user() throws IOException, InterruptedException {
        webServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(Utils.readFromFile("json/user.json")));
        Response<User> resp = api.user().execute();
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
        Assertions.assertTrue(Utils.checkAuthorization(webServer.takeRequest()));
    }

    @Test
    void toWatch() throws IOException, InterruptedException {
        webServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(Utils.readFromFile("json/to_watch.json")));
        Response<List<Episode>> resp = api.toWatch(new Request.ToWatch().limit(10)).execute();
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
        Assertions.assertTrue(Utils.checkAuthorization(webServer.takeRequest()));
    }

    @Test
    void agenda() throws IOException, InterruptedException {
        webServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(Utils.readFromFile("json/agenda.json")));
        Response<List<Episode>> resp = api.agenda(new Request.Agenda()).execute();
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
        Assertions.assertTrue(Utils.checkAuthorization(webServer.takeRequest()));
    }

    @Test
    void library() throws IOException, InterruptedException {
        webServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(Utils.readFromFile("json/library.json")));
        Response<List<Episode>> resp = api.library(new Request.Library()).execute();
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
        Assertions.assertTrue(Utils.checkAuthorization(webServer.takeRequest()));
    }

    @Test
    void explore() throws IOException, InterruptedException {
        webServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(Utils.readFromFile("json/explore.json")));
        Response<List<Show>> resp = api.explore(new Request.Explore()).execute();
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
        Assertions.assertTrue(Utils.checkAuthorization(webServer.takeRequest()));
    }
}
