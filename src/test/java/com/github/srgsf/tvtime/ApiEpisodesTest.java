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
import com.github.srgsf.tvtime.model.Episode;
import com.github.srgsf.tvtime.model.EpisodeId;
import com.github.srgsf.tvtime.model.Request;
import com.github.srgsf.tvtime.service.TvTimeEpisode;
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

class ApiEpisodesTest {
    static TvTime client;
    static MockWebServer webServer;
    static TvTimeEpisode api;

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
        api = client.episodes();
    }

    @AfterAll
    static void tearDown() throws IOException {
        webServer.shutdown();
    }

    @Test
    void setEmotion() throws IOException, InterruptedException {
        webServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(Utils.readFromFile("json/empty.json")));
        Response<Void> resp = api.setEmotion(110995L, Emotion.Good).execute();
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNull(resp.body());
        Assertions.assertTrue(Utils.checkAuthorization(webServer.takeRequest()));
    }


    @Test
    void deleteEmotion() throws IOException, InterruptedException {
        webServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(Utils.readFromFile("json/empty.json")));
        Response<Void> resp = api.deleteEmotion(110995).execute();
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNull(resp.body());
        Assertions.assertTrue(Utils.checkAuthorization(webServer.takeRequest()));
    }

    @Test
    void checkIn() throws IOException, InterruptedException {
        webServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(Utils.readFromFile("json/empty.json")));
        Response<Void> resp = api.checkin(new Request.CheckIn(EpisodeId.imdb("tt0606030"))).execute();
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNull(resp.body());
        Assertions.assertTrue(Utils.checkAuthorization(webServer.takeRequest()));

    }

    @Test
    void isChecked() throws IOException, InterruptedException {
        webServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(Utils.readFromFile("json/false.json")));
        Response<Boolean> resp = api.isChecked(EpisodeId.file("House.s01e02.mkv")).execute();
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
        Assertions.assertFalse(resp.body());
        Assertions.assertTrue(Utils.checkAuthorization(webServer.takeRequest()));
    }

    @Test
    void progress() throws IOException, InterruptedException {
        webServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(Utils.readFromFile("json/progress_null.json")));
        Response<Integer> resp = api.setProgress(EpisodeId.episode(110995L), 100).execute();
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNull(resp.body());
        Assertions.assertTrue(Utils.checkAuthorization(webServer.takeRequest()));
    }

    @Test
    void getProgressZero() throws IOException, InterruptedException {
        webServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(Utils.readFromFile("json/progress_zero.json")));
        Response<Integer> resp = api.getProgress(EpisodeId.episode(110995L)).execute();
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
        Assertions.assertEquals(0, resp.body());
        Assertions.assertTrue(Utils.checkAuthorization(webServer.takeRequest()));
    }

    @Test
    void getProgressNonZero() throws IOException, InterruptedException {
        webServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(Utils.readFromFile("json/progress.json")));
        Response<Integer> resp = api.getProgress(EpisodeId.episode(110995L)).execute();
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
        Assertions.assertEquals(25, resp.body());
        Assertions.assertTrue(Utils.checkAuthorization(webServer.takeRequest()));
    }

    @Test
    void episodeId() throws IOException, InterruptedException {
        webServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(Utils.readFromFile("json/episode.json")));
        Response<Episode> resp = api.episode(
                EpisodeId.episode(110995L)).execute();
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
        Assertions.assertTrue(Utils.checkAuthorization(webServer.takeRequest()));
    }
}
