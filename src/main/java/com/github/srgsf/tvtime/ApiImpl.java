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

import com.github.srgsf.tvtime.model.*;
import com.github.srgsf.tvtime.service.TvTimeEpisode;
import com.github.srgsf.tvtime.service.TvTimeInfo;
import com.github.srgsf.tvtime.service.TvTimeShow;
import com.squareup.moshi.JsonAdapter;
import retrofit2.Call;

import java.util.List;

class ApiImpl implements TvTimeShow, TvTimeEpisode, TvTimeInfo {
    private final ApiRetrofit api;
    private final JsonAdapter<List<EpisodeRef>> itemAdapter;

    ApiImpl(ApiRetrofit api, JsonAdapter<List<EpisodeRef>> itemAdapter) {
        this.api = api;
        this.itemAdapter = itemAdapter;
    }

    @Override
    public Call<User> user() {
        return api.user();
    }

    @Override
    public Call<List<Episode>> toWatch(Request.ToWatch param) {
        return api.toWatch(param.page, param.limit, param.language);
    }

    @Override
    public Call<List<Episode>> agenda(Request.Agenda param) {
        return api.agenda(param.page, param.limit, param.includeWatched);
    }

    @Override
    public Call<List<Episode>> library(Request.Library param) {
        return api.library(param.page, param.limit);
    }

    @Override
    public Call<List<Show>> explore(Request.Explore param) {
        return api.explore(param.page, param.limit);
    }

    @Override
    public Call<Show> show(Request.Show param) {
        return api.show(param.id, param.name, param.includeEpisodes, param.exactMatch);
    }

    @Override
    public Call<Void> follow(long showId) {
        return api.follow(showId);
    }

    @Override
    public Call<Boolean> isFollowed(long showId) {
        return api.isFollowed(showId);
    }

    @Override
    public Call<Void> unFollow(long showId) {
        return api.unFollow(showId);
    }

    @Override
    public Call<Void> archive(long showId) {
        return api.archive(showId);
    }

    @Override
    public Call<Boolean> isArchived(long showId) {
        return api.isArchived(showId);
    }

    @Override
    public Call<Void> unArchive(long showId) {
        return api.unArchive(showId);
    }

    @Override
    public Call<Void> setWatched(long showId) {
        return api.progress(showId, null, null);
    }

    @Override
    public Call<Void> setWatched(long showId, int season) {
        return api.progress(showId, season, null);
    }

    @Override
    public Call<Void> setWatched(EpisodeRef param) {
        return api.progress(param.showId, param.season, param.episode);
    }

    @Override
    public Call<Void> setWatched(List<EpisodeRef> param) {
        return api.progress(itemAdapter.toJson(param));
    }

    @Override
    public Call<Void> setUnwatched(long showId) {
        return api.deleteProgress(showId, null, null);
    }

    @Override
    public Call<Void> setUnwatched(long showId, int season) {
        return api.deleteProgress(showId, season, null);
    }

    @Override
    public Call<Void> setUnwatched(EpisodeRef param) {
        return api.deleteProgress(param.showId, param.season, param.episode);
    }

    @Override
    public Call<Void> setUnwatched(List<EpisodeRef> param) {
        return api.deleteProgress(itemAdapter.toJson(param));
    }


    @Override
    public Call<Episode> episode(EpisodeId param) {
        return api.episode(param.episodeRef.showId,
                param.episodeRef.season,
                param.episodeRef.episode,
                param.imdbId,
                param.episodeId,
                formatFileName(param.filenames, false));
    }

    @Override
    public Call<Void> checkin(Request.CheckIn param) {
        return api.checkin(param.episode.episodeRef.showId,
                param.episode.episodeRef.season,
                param.episode.episodeRef.episode,
                param.episode.imdbId,
                param.episode.episodeId,
                formatFileName(param.episode.filenames, true),
                param.ticker,
                param.twitter,
                param.autoFollow);
    }

    @Override
    public Call<Boolean> isChecked(EpisodeId param) {
        return api.isChecked(param.episodeRef.showId,
                param.episodeRef.season,
                param.episodeRef.episode,
                param.imdbId,
                param.episodeId,
                formatFileName(param.filenames, false));
    }

    @Override
    public Call<Void> checkOut(EpisodeId param) {
        return api.checkOut(param.episodeRef.showId,
                param.episodeRef.season,
                param.episodeRef.episode,
                param.imdbId,
                param.episodeId,
                formatFileName(param.filenames, true));
    }

    @Override
    public Call<Integer> setProgress(EpisodeId param, int progress) {
        return api.setProgress(param.episodeRef.showId,
                param.episodeRef.season,
                param.episodeRef.episode,
                param.imdbId,
                param.episodeId,
                formatFileName(param.filenames, false),
                progress);
    }

    @Override
    public Call<Integer> getProgress(EpisodeId param) {
        return api.progress(param.episodeRef.showId,
                param.episodeRef.season,
                param.episodeRef.episode,
                param.imdbId,
                param.episodeId,
                formatFileName(param.filenames, false));
    }

    @Override
    public Call<Void> setEmotion(long episodeId, Emotion emotion) {
        return api.setEmotion(episodeId, emotion);
    }

    @Override
    public Call<Void> deleteEmotion(long episodeId) {
        return api.deleteEmotion(episodeId);
    }

    private String formatFileName(String[] fn, boolean list) {
        if (fn == null || fn.length == 0) {
            return null;
        }
        if (!list) {
            return fn[0];
        }
        return String.join(",", fn);
    }
}
