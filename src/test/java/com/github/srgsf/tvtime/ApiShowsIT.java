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
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.github.srgsf.tvtime.Utils.callWithTimeout;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApiShowsIT {

    static final long SHOW_ID = 73255;
    static TvTime client;
    static TvTimeShow api;

    @BeforeAll
    static void setup() {
        client = new TvTime.Builder()
                .tokenProvider(Utils.tokenProvider())
                .build();
        api = client.shows();
    }


    @Test
    void show() throws IOException, InterruptedException {
        Response<Show> resp = callWithTimeout(api.show(new Request.Show(SHOW_ID, "House").includeEpisodes(true)));
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
    }

    @Order(1)
    @Test
    void follow() throws IOException, InterruptedException {
        Response<Void> resp = callWithTimeout(api.follow(SHOW_ID));
        Assertions.assertTrue(resp.isSuccessful());
    }

    @Order(2)
    @Test
    void isFollowed() throws IOException, InterruptedException {
        Response<Boolean> resp = callWithTimeout(api.isFollowed(SHOW_ID));
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
        Assertions.assertTrue(resp.body());
    }

    @Order(10)
    @Test
    void unFollow() throws IOException, InterruptedException {
        Response<Void> resp = callWithTimeout(api.unFollow(SHOW_ID));
        Assertions.assertTrue(resp.isSuccessful());
    }

    @Order(3)
    @Test
    void archive() throws IOException, InterruptedException {
        Response<Void> resp = callWithTimeout(api.archive(SHOW_ID));
        Assertions.assertTrue(resp.isSuccessful());
    }

    @Order(4)
    @Test
    void isArchived() throws IOException, InterruptedException {
        Response<Boolean> resp = callWithTimeout(api.isArchived(SHOW_ID));
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
        Assertions.assertTrue(resp.body());
    }

    @Order(5)
    @Test
    void unArchive() throws IOException, InterruptedException {
        Response<Void> resp = callWithTimeout(api.unArchive(SHOW_ID));
        Assertions.assertTrue(resp.isSuccessful());
    }

    @Order(6)
    @Test
    void showProgress() throws IOException, InterruptedException {
        Response<Void> resp = callWithTimeout(api.setWatched(new EpisodeRef(SHOW_ID, 1, 1)));
        Assertions.assertTrue(resp.isSuccessful());
    }

    @Order(7)
    @Test
    void showProgressBulk() throws IOException, InterruptedException {
        List<EpisodeRef> bulk = Arrays.asList(
                new EpisodeRef(SHOW_ID, 1, 2),
                new EpisodeRef(SHOW_ID, 1, 3),
                new EpisodeRef(SHOW_ID, 1, 4),
                new EpisodeRef(SHOW_ID, 1, 5),
                new EpisodeRef(SHOW_ID, 1, 6),
                new EpisodeRef(SHOW_ID, 1, 7)

        );
        Response<Void> resp = callWithTimeout(api.setWatched(bulk));
        Assertions.assertTrue(resp.isSuccessful());
    }

    @Order(8)
    @Test
    void deleteShowProgress() throws IOException, InterruptedException {
        Response<Void> resp = callWithTimeout(api.setUnwatched(new EpisodeRef(SHOW_ID, 1, 1)));
        Assertions.assertTrue(resp.isSuccessful());
    }

    @Order(9)
    @Test
    void deleteShowProgressBulk() throws IOException, InterruptedException {
        List<EpisodeRef> bulk = Arrays.asList(
                new EpisodeRef(SHOW_ID, 1, 2),
                new EpisodeRef(SHOW_ID, 1, 3)

        );
        Response<Void> resp = callWithTimeout(api.setUnwatched(bulk));
        Assertions.assertTrue(resp.isSuccessful());
    }
}
