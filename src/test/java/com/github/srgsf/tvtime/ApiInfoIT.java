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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

import static com.github.srgsf.tvtime.Utils.callWithTimeout;


public class ApiInfoIT {


    static TvTime client;
    static TvTimeInfo api;

    @BeforeAll
    static void setup() {
        client = new TvTime.Builder()
                .tokenProvider(Utils.tokenProvider())
                .build();
        api = client.info();
    }


    @Test
    void user() throws IOException, InterruptedException {
        Response<User> resp = callWithTimeout(api.user());
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
    }

    //show images
    @Test
    void toWatch() throws IOException, InterruptedException {
        Response<List<Episode>> resp = callWithTimeout(api.toWatch(new Request.ToWatch().limit(10)));
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
    }

    @Test
    void agenda() throws IOException, InterruptedException {
        Response<List<Episode>> resp = callWithTimeout(api.agenda(new Request.Agenda().includeWatched(true)));
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
    }

    @Test
    void library() throws IOException, InterruptedException {
        Response<List<Episode>> resp = callWithTimeout(api.library(new Request.Library()));
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
    }

    @Test
    void explore() throws IOException, InterruptedException {
        Response<List<Show>> resp = callWithTimeout(api.explore(new Request.Explore().limit(20)));
        Assertions.assertTrue(resp.isSuccessful());
        Assertions.assertNotNull(resp.body());
    }
}
