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
import com.squareup.moshi.JsonAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import retrofit2.Call;

import java.util.Arrays;
import java.util.List;

public class ParametersTest {


    @Test
    void testFileNames() {
        String[] filenames = new String[10];
        for (int i = 0; i < filenames.length; i++) {
            filenames[i] = "file" + (i + 1);
        }

        EpisodeId param = EpisodeId.file(filenames);
        final ParamHolder holder = new ParamHolder();
        ApiImpl api = new ApiImpl(new FileNameAdapter(holder), null);

        api.episode(param);
        Assertions.assertEquals("file1", holder.param);
        holder.param = null;
        api.checkin(new Request.CheckIn(param));
        Assertions.assertEquals("file1,file2,file3,file4,file5,file6,file7,file8,file9,file10", holder.param);
        holder.param = null;
        api.isChecked(param);
        Assertions.assertEquals("file1", holder.param);
        holder.param = null;
        api.checkOut(param);
        Assertions.assertEquals("file1,file2,file3,file4,file5,file6,file7,file8,file9,file10", holder.param);
        holder.param = null;
        api.setProgress(param, 100);
        Assertions.assertEquals("file1", holder.param);
        holder.param = null;
        api.getProgress(param);
        Assertions.assertEquals("file1", holder.param);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            String[] fn = Arrays.copyOf(filenames, 11);
            fn[10] = "file11";
            EpisodeId.file(fn);
        });

    }

    @Test
    void testShowList() {
        JsonAdapter<List<EpisodeRef>> adapter = JsonAdapters.episodeRefAdapter();

        List<EpisodeRef> param = Arrays.asList(new EpisodeRef(1L, 1, 1),
                new EpisodeRef(1L, 1, 2),
                new EpisodeRef(1L, 1, 3));

        String expected = adapter.toJson(param);

        final ParamHolder holder = new ParamHolder();
        ApiImpl api = new ApiImpl(new FileNameAdapter(holder), adapter);
        api.setWatched(param);
        Assertions.assertEquals(expected, holder.param);
        holder.param = null;
        api.setUnwatched(param);
        Assertions.assertEquals(expected, holder.param);
    }


    private static class ParamHolder {
        String param;
    }

    private static class FileNameAdapter implements ApiRetrofit {
        ParamHolder holder;

        public FileNameAdapter(ParamHolder holder) {
            this.holder = holder;
        }

        @Override
        public Call<Episode> episode(Long showId,
                                     Integer season,
                                     Integer episodeNo,
                                     String imdbId,
                                     Long episode,
                                     String filename) {
            holder.param = filename;
            return null;
        }

        @Override
        public Call<Void> checkin(Long showId,
                                  Integer season,
                                  Integer episodeNo,
                                  String imdbId,
                                  Long episode,
                                  String filename,
                                  Boolean ticker,
                                  Boolean twitter,
                                  Boolean autoFollow) {
            holder.param = filename;
            return null;
        }

        @Override
        public Call<Boolean> isChecked(Long showId,
                                       Integer season,
                                       Integer episodeNo,
                                       String imdbId,
                                       Long episode,
                                       String filename) {
            holder.param = filename;
            return null;
        }

        @Override
        public Call<Void> checkOut(Long showId,
                                   Integer season,
                                   Integer episodeNo,
                                   String imdbId,
                                   Long episode,
                                   String filename) {
            holder.param = filename;
            return null;
        }

        @Override
        public Call<Integer> setProgress(Long showId,
                                         Integer season,
                                         Integer episodeNo,
                                         String imdbId,
                                         Long episode,
                                         String filename,
                                         Integer progress) {
            holder.param = filename;
            return null;
        }

        @Override
        public Call<Integer> progress(Long showId,
                                      Integer season,
                                      Integer episodeNo,
                                      String imdbId,
                                      Long episode,
                                      String filename) {
            holder.param = filename;
            return null;
        }

        @Override
        public Call<Void> progress(String shows) {
            holder.param = shows;
            return null;
        }

        @Override
        public Call<Void> deleteProgress(String shows) {
            holder.param = shows;
            return null;
        }

        @Override
        public Call<User> user() {
            return null;
        }

        @Override
        public Call<List<Episode>> toWatch(Integer page, Integer limit, Request.Language lang) {
            return null;
        }

        @Override
        public Call<List<Episode>> agenda(Integer page, Integer limit, Boolean includeWatched) {
            return null;
        }

        @Override
        public Call<List<Episode>> library(Integer page, Integer limit) {
            return null;
        }

        @Override
        public Call<List<Show>> explore(Integer page, Integer limit) {
            return null;
        }

        @Override
        public Call<Show> show(long id, String name, Boolean includeEpisodes, Boolean exactMatch) {
            return null;
        }

        @Override
        public Call<Void> follow(long showId) {
            return null;
        }

        @Override
        public Call<Boolean> isFollowed(long showId) {
            return null;
        }

        @Override
        public Call<Void> unFollow(long showId) {
            return null;
        }

        @Override
        public Call<Void> archive(long showId) {
            return null;
        }

        @Override
        public Call<Boolean> isArchived(long showId) {
            return null;
        }

        @Override
        public Call<Void> unArchive(long showId) {
            return null;
        }

        @Override
        public Call<Void> progress(long showId, Integer season, Integer episode) {
            return null;
        }

        @Override
        public Call<Void> deleteProgress(long showId, Integer season, Integer episode) {
            return null;
        }

        @Override
        public Call<Void> setEmotion(long id, Emotion emotion) {
            return null;
        }

        @Override
        public Call<Void> deleteEmotion(long episode) {
            return null;
        }
    }
}
