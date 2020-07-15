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
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;

import static com.github.srgsf.tvtime.Utils.callWithTimeout;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApiEpisodesIT {

    static final long SHOW_ID = 73255;
    static TvTime client;
    static TvTimeEpisode api;

    @BeforeAll
    static void setup() {

        client = new TvTime.Builder()
                .tokenProvider(Utils.tokenProvider())
                .build();
        api = client.episodes();
    }

    @Test
    void episodeRef() throws IOException, InterruptedException {
        Response<Episode> resp = callWithTimeout(api.episode(
                EpisodeId.episode(SHOW_ID, 1, 1)));
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
    }

    @Test
    void episodeFile() throws IOException, InterruptedException {
        Response<Episode> resp = callWithTimeout(api.episode(
                EpisodeId.file("House.s01e03.mkv")));
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
    }

    @Test
    void episodeImdb() throws IOException, InterruptedException {
        Response<Episode> resp = callWithTimeout(api.episode(
                EpisodeId.imdb("tt0606030")));
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
    }

    @Order(1)
    @Test
    void checkIn() throws IOException, InterruptedException {
        Response<Void> resp = callWithTimeout(api.checkin(new Request.CheckIn(EpisodeId.episode(110995L)).autoFollow(false)));
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNull(resp.body());
    }

    @Order(2)
    @Test
    void checkInRef() throws IOException, InterruptedException {
        Response<Void> resp = callWithTimeout(api.checkin(new Request.CheckIn(EpisodeId.episode(SHOW_ID, 1, 3))));
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNull(resp.body());
    }

    @Order(3)
    @Test
    void checkInImdb() throws IOException, InterruptedException {
        Response<Void> resp = callWithTimeout(api.checkin(new Request.CheckIn(EpisodeId.imdb("tt0606030"))));
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNull(resp.body());
    }

    @Order(4)
    @Test
    void checkInFile() throws IOException, InterruptedException {
        Response<Void> resp = callWithTimeout(api.checkin(new Request.CheckIn(EpisodeId.file("House.s01e04.mkv", "House.s01e05.mkv"))));
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNull(resp.body());
    }

    @Order(5)
    @Test
    void isChecked() throws IOException, InterruptedException {
        Response<Boolean> resp = callWithTimeout(api.isChecked(EpisodeId.file("House.s01e02.mkv")));
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
        Assertions.assertTrue(resp.body());
    }

    @Order(6)
    @Test
    void isCheckedId() throws IOException, InterruptedException {
        Response<Boolean> resp = callWithTimeout(api.isChecked(EpisodeId.episode(110995L)));
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
        Assertions.assertTrue(resp.body());
    }

    @Disabled("api error")
    @Order(7)
    @Test
    void isCheckedRef() throws IOException, InterruptedException {
        Response<Boolean> resp = callWithTimeout(api.isChecked(EpisodeId.episode(SHOW_ID, 1, 2)));
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
        Assertions.assertTrue(resp.body());
    }

    @Order(8)
    @Test
    void isCheckedImdb() throws IOException, InterruptedException {
        Response<Boolean> resp = callWithTimeout(api.isChecked(EpisodeId.imdb("tt0606034")));
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
        Assertions.assertTrue(resp.body());
    }

    @Order(9)
    @Test
    void checkOutImdb() throws IOException, InterruptedException {
        Response<Void> resp = callWithTimeout(api.checkOut(EpisodeId.imdb("tt0606034")));
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNull(resp.body());
    }

    @Order(10)
    @Test
    void checkOutRef() throws IOException, InterruptedException {
        Response<Void> resp = callWithTimeout(api.checkOut(EpisodeId.episode(SHOW_ID, 1, 2)));
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNull(resp.body());
    }

    @Order(11)
    @Test
    void checkOutId() throws IOException, InterruptedException {
        Response<Void> resp = callWithTimeout(api.checkOut(EpisodeId.episode(110995L)));
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNull(resp.body());
    }

    @Order(12)
    @Test
    void checkOutFile() throws IOException, InterruptedException {
        Response<Void> resp = callWithTimeout(api.checkOut(EpisodeId.file("House.s01e04.mkv", "House.s01e05.mkv")));
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNull(resp.body());
    }

    @Order(13)
    @Test
    void progressImdb() throws IOException, InterruptedException {
        Response<Integer> resp = callWithTimeout(api.setProgress(EpisodeId.imdb("tt0606034"), 100));
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
    }

    @Disabled("api error")
    @Order(14)
    @Test
    void progressRef() throws IOException, InterruptedException {
        Response<Integer> resp = callWithTimeout(api.setProgress(EpisodeId.episode(SHOW_ID, 1, 2), 200));
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
    }

    @Order(15)
    @Test
    void progressId() throws IOException, InterruptedException {
        Response<Integer> resp = callWithTimeout(api.setProgress(EpisodeId.episode(110995L), 300));
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
    }

    @Order(16)
    @Test
    void progressFile() throws IOException, InterruptedException {
        Response<Integer> resp = callWithTimeout(api.setProgress(EpisodeId.file("House.s01e04.mkv", "House.s01e05.mkv"), 50));
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
    }

    @Order(17)
    @Test
    void getProgressImdb() throws IOException, InterruptedException {
        Response<Integer> resp = callWithTimeout(api.getProgress(EpisodeId.imdb("tt0606034")));
        Assertions.assertTrue(resp.isSuccessful());

    }

    @Disabled("api error")
    @Order(18)
    @Test
    void getProgressRef() throws IOException, InterruptedException {
        Response<Integer> resp = callWithTimeout(api.getProgress(EpisodeId.episode(SHOW_ID, 1, 2)));
        Assertions.assertTrue(resp.isSuccessful());

    }

    @Order(19)
    @Test
    void getProgressId() throws IOException, InterruptedException {
        Response<Integer> resp = callWithTimeout(api.getProgress(EpisodeId.episode(110995L)));
        Assertions.assertTrue(resp.isSuccessful());
    }

    @Order(20)
    @Test
    void getProgressFile() throws IOException, InterruptedException {
        Response<Integer> resp = callWithTimeout(api.getProgress(
                EpisodeId.file("House.s01e04.mkv", "House.s01e05.mkv")));
        Assertions.assertTrue(resp.isSuccessful());
    }

    @Order(21)
    @Test
    void setEmotion() throws IOException, InterruptedException {
        Response<Void> resp = callWithTimeout(api.setEmotion(110995L, Emotion.Good));
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNull(resp.body());
    }

    @Order(22)
    @Test
    void episodeId() throws IOException, InterruptedException {
        Response<Episode> resp = callWithTimeout(api.episode(
                EpisodeId.episode(110995L)));
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
    }

    @Order(23)
    @Test
    void deleteEmotion() throws IOException, InterruptedException {
        Response<Void> resp = callWithTimeout(api.deleteEmotion(110995));
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNull(resp.body());
    }
}
