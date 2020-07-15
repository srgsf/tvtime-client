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

import com.github.srgsf.tvtime.model.Emotion;
import com.github.srgsf.tvtime.model.Episode;
import com.github.srgsf.tvtime.model.EpisodeId;
import com.github.srgsf.tvtime.model.Request;
import retrofit2.Call;

/**
 * Api methods related to episode query and manipulation.
 *
 * @author srgsf
 * @since 1.0
 */
public interface TvTimeEpisode {

    /**
     * Gets episode info.
     *
     * @param param episode reference.
     * @return retrofit {@link Call}
     */
    Call<Episode> episode(EpisodeId param);

    /**
     * Marks as watched.
     *
     * @param param details.
     * @return retrofit {@link Call}
     */
    Call<Void> checkin(Request.CheckIn param);

    /**
     * Checks if watched.
     *
     * @param param episode reference.
     * @return retrofit {@link Call}
     */
    Call<Boolean> isChecked(EpisodeId param);

    /**
     * Marks as unwatched.
     *
     * @param param episode reference.
     * @return retrofit {@link Call}
     */
    Call<Void> checkOut(EpisodeId param);

    /**
     * Sets progress.
     *
     * @param param    episode reference.
     * @param progress in seconds.
     * @return retrofit {@link Call}
     */
    Call<Integer> setProgress(EpisodeId param, int progress);

    /**
     * Returns progress.
     *
     * @param param episode reference.
     * @return retrofit {@link Call}
     */
    Call<Integer> getProgress(EpisodeId param);

    /**
     * Sets emotion.
     *
     * @param episodeId tvdb episode id.
     * @param emotion   emotion.
     * @return retrofit {@link Call}
     */
    Call<Void> setEmotion(long episodeId, Emotion emotion);

    /**
     * Removes emotion.
     *
     * @param episodeId tvdb episode id.
     * @return retrofit {@link Call}
     */
    Call<Void> deleteEmotion(long episodeId);
}
