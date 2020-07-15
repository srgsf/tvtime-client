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
import com.github.srgsf.tvtime.service.TvTimeInfo;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

class ClientTest {
    private MockWebServer webServer;

    @BeforeEach
    void setup() throws IOException {
        webServer = new MockWebServer();
        webServer.start();
    }

    @AfterEach
    void tearDown() throws IOException {
        webServer.shutdown();
    }

    @Test
    void testHeaders() throws IOException, InterruptedException {
        webServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(Utils.readFromFile("json/agenda.json")));

        TvTime client = new TvTime.Builder()
                .baseUrl(webServer.url("/").toString())
                .tokenProvider(() -> "TOKEN")
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new HttpLoggingInterceptor()
                                .setLevel(HttpLoggingInterceptor.Level.BODY)).build())
                .build();

        TvTimeInfo api = client.info();
        Response<List<Episode>> resp = api.agenda(new Request.Agenda().limit(20)).execute();
        RecordedRequest req = webServer.takeRequest();
        Assertions.assertTrue(Utils.checkAuthorization(req));
    }

}
