/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.srgsf.tvtime.service;

import com.github.srgsf.tvtime.model.Episode;
import com.github.srgsf.tvtime.model.Request;
import com.github.srgsf.tvtime.model.Show;
import com.github.srgsf.tvtime.model.User;
import retrofit2.Call;

import java.util.List;

/**
 * Api methods related to user.
 *
 * @author srgsf
 * @since 1.0
 */
public interface TvTimeInfo {
    /**
     * Returns user info.
     *
     * @return retrofit {@link Call}
     */
    Call<User> user();

    /**
     * Get the user to-watch list.
     *
     * @param param details.
     * @return retrofit {@link Call}
     */
    Call<List<Episode>> toWatch(Request.ToWatch param);

    /**
     * Get the user agenda.
     *
     * @param param details.
     * @return retrofit {@link Call}
     */
    Call<List<Episode>> agenda(Request.Agenda param);

    /**
     * Get the user library.
     *
     * @param param details.
     * @return retrofit {@link Call}
     */
    Call<List<Episode>> library(Request.Library param);

    /**
     * Discover trending shows.
     *
     * @param param details.
     * @return retrofit {@link Call}
     */
    Call<List<Show>> explore(Request.Explore param);

}
