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

import com.github.srgsf.tvtime.model.EpisodeRef;
import com.github.srgsf.tvtime.model.Request;
import com.github.srgsf.tvtime.model.Show;
import retrofit2.Call;

import java.util.List;

/**
 * Api methods related to shows query and manipulation.
 *
 * @author srgsf
 * @since 1.0
 */
public interface TvTimeShow {

    /**
     * Get show data.
     *
     * @param param details
     * @return retrofit {@link Call}
     */
    Call<Show> show(Request.Show param);

    /**
     * Follow a show.
     *
     * @param showId tvdb show id
     * @return retrofit {@link Call}
     */
    Call<Void> follow(long showId);

    /**
     * Check if a show is followed.
     *
     * @param showId tvdb show id
     * @return retrofit {@link Call}
     */
    Call<Boolean> isFollowed(long showId);

    /**
     * Unfollow a show.
     *
     * @param showId tvdb show id
     * @return retrofit {@link Call}
     */
    Call<Void> unFollow(long showId);

    /**
     * Archive a show.
     *
     * @param showId tvdb show id
     * @return retrofit {@link Call}
     */
    Call<Void> archive(long showId);

    /**
     * Check if a show is archived.
     *
     * @param showId tvdb show id
     * @return retrofit {@link Call}
     */
    Call<Boolean> isArchived(long showId);

    /**
     * Unarchive a show.
     *
     * @param showId tvdb show id
     * @return retrofit {@link Call}
     */
    Call<Void> unArchive(long showId);

    /**
     * Mark show as watched.
     *
     * @param showId tvdb show id
     * @return retrofit {@link Call}
     */
    Call<Void> setWatched(long showId);

    /**
     * Mark show season as watched.
     *
     * @param showId tvdb show id
     * @param season season no.
     * @return retrofit {@link Call}
     */
    Call<Void> setWatched(long showId, int season);

    /**
     * Mark all episodes seen until season and episode number (included).
     *
     * @param param episode reference.
     * @return retrofit {@link Call}
     */
    Call<Void> setWatched(EpisodeRef param);

    /**
     * Mark all episodes seen.
     *
     * @param param episode reference list.
     * @return retrofit {@link Call}
     */
    Call<Void> setWatched(List<EpisodeRef> param);

    /**
     * Mark show as unwatched.
     *
     * @param showId tvdb show id
     * @return retrofit {@link Call}
     */
    Call<Void> setUnwatched(long showId);

    /**
     * Mark show season as unwatched.
     *
     * @param showId tvdb show id
     * @param season season no.
     * @return retrofit {@link Call}
     */
    Call<Void> setUnwatched(long showId, int season);

    /**
     * Mark episode seen
     *
     * @param param episode reference.
     * @return retrofit {@link Call}
     */
    Call<Void> setUnwatched(EpisodeRef param);

    /**
     * Mark episodes seen
     *
     * @param param episode reference list.
     * @return retrofit {@link Call}
     */
    Call<Void> setUnwatched(List<EpisodeRef> param);
}
