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

import com.github.srgsf.tvtime.model.EpisodeRef;
import com.github.srgsf.tvtime.model.Request;
import com.github.srgsf.tvtime.model.Show;
import com.github.srgsf.tvtime.service.TvTimeShow;
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
import java.util.Arrays;
import java.util.List;

import static com.github.srgsf.tvtime.ApiEpisodesIT.SHOW_ID;

class ApiShowsTest {
    static TvTime client;
    static MockWebServer webServer;
    static TvTimeShow api;

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
        api = client.shows();
    }

    @AfterAll
    static void tearDown() throws IOException {
        webServer.shutdown();
    }

    @Test
    void show() throws IOException, InterruptedException {
        webServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(Utils.readFromFile("json/show.json")));
        Response<Show> resp = api.show(new Request.Show(SHOW_ID, "House").includeEpisodes(true)).execute();
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
        Assertions.assertTrue(Utils.checkAuthorization(webServer.takeRequest()));
    }

    @Test
    void follow() throws IOException, InterruptedException {
        webServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(Utils.readFromFile("json/empty.json")));
        Response<Void> resp = api.follow(SHOW_ID).execute();
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNull(resp.body());
        Assertions.assertTrue(Utils.checkAuthorization(webServer.takeRequest()));
    }


    @Test
    void isFollowed() throws IOException, InterruptedException {
        webServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(Utils.readFromFile("json/true.json")));
        Response<Boolean> resp = api.isFollowed(SHOW_ID).execute();
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
        Assertions.assertTrue(resp.body());
        Assertions.assertTrue(Utils.checkAuthorization(webServer.takeRequest()));
    }


    @Test
    void unFollow() throws IOException, InterruptedException {
        webServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(Utils.readFromFile("json/empty.json")));
        Response<Void> resp = api.unFollow(SHOW_ID).execute();
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNull(resp.body());
        Assertions.assertTrue(Utils.checkAuthorization(webServer.takeRequest()));
    }


    @Test
    void archive() throws IOException, InterruptedException {
        webServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(Utils.readFromFile("json/empty.json")));
        Response<Void> resp = api.archive(SHOW_ID).execute();
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNull(resp.body());
        Assertions.assertTrue(Utils.checkAuthorization(webServer.takeRequest()));
    }


    @Test
    void isArchived() throws IOException, InterruptedException {
        webServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(Utils.readFromFile("json/true.json")));
        Response<Boolean> resp = api.isArchived(SHOW_ID).execute();
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
        Assertions.assertTrue(resp.body());
        Assertions.assertTrue(Utils.checkAuthorization(webServer.takeRequest()));
    }

    @Test
    void unArchive() throws IOException, InterruptedException {
        webServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(Utils.readFromFile("json/empty.json")));
        Response<Void> resp = api.unArchive(SHOW_ID).execute();
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNull(resp.body());
        Assertions.assertTrue(Utils.checkAuthorization(webServer.takeRequest()));
    }

    @Test
    void showProgress() throws IOException, InterruptedException {
        webServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(Utils.readFromFile("json/empty.json")));
        Response<Void> resp = api.setWatched(new EpisodeRef(SHOW_ID, 1, 1)).execute();
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNull(resp.body());
        Assertions.assertTrue(Utils.checkAuthorization(webServer.takeRequest()));
    }


    @Test
    void showProgressBulk() throws IOException, InterruptedException {
        webServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(Utils.readFromFile("json/empty.json")));
        List<EpisodeRef> bulk = Arrays.asList(
                new EpisodeRef(SHOW_ID, 1, 2),
                new EpisodeRef(SHOW_ID, 1, 3),
                new EpisodeRef(SHOW_ID, 1, 4),
                new EpisodeRef(SHOW_ID, 1, 5),
                new EpisodeRef(SHOW_ID, 1, 6),
                new EpisodeRef(SHOW_ID, 1, 7)

        );
        Response<Void> resp = api.setWatched(bulk).execute();
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNull(resp.body());
        Assertions.assertTrue(Utils.checkAuthorization(webServer.takeRequest()));
    }


    @Test
    void deleteShowProgress() throws IOException, InterruptedException {
        webServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(Utils.readFromFile("json/empty.json")));
        Response<Void> resp = api.setUnwatched(new EpisodeRef(SHOW_ID, 1, 1)).execute();
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNull(resp.body());
        Assertions.assertTrue(Utils.checkAuthorization(webServer.takeRequest()));
    }


    @Test
    void deleteShowProgressBulk() throws IOException, InterruptedException {
        webServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(Utils.readFromFile("json/empty.json")));
        List<EpisodeRef> bulk = Arrays.asList(
                new EpisodeRef(SHOW_ID, 1, 2),
                new EpisodeRef(SHOW_ID, 1, 3)

        );
        Response<Void> resp = api.setUnwatched(bulk).execute();
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNull(resp.body());
        Assertions.assertTrue(Utils.checkAuthorization(webServer.takeRequest()));
    }
}
