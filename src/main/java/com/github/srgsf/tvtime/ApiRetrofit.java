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
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;


interface ApiRetrofit {

    @ApiMethod
    @GET("user")
    Call<User> user();

    @ApiMethod
    @GET("to_watch")
    Call<List<Episode>> toWatch(@Query(value = "page") Integer page,
                                @Query(value = "limit") Integer limit,
                                @Query(value = "lang") Request.Language lang);

    @ApiMethod
    @GET("agenda")
    Call<List<Episode>> agenda(@Query(value = "page") Integer page,
                               @Query(value = "limit") Integer limit,
                               @AsInt @Query(value = "include_watched") Boolean includeWatched);

    @ApiMethod
    @GET("agenda")
    Call<List<Episode>> library(@Query(value = "page") Integer page,
                                @Query(value = "limit") Integer limit);

    @ApiMethod
    @GET("explore")
    Call<List<Show>> explore(@Query(value = "page") Integer page,
                             @Query(value = "limit") Integer limit);

    @ApiMethod
    @GET("show")
    Call<Show> show(@Query(value = "show_id") long id,
                    @Query(value = "show_name") String name,
                    @AsInt @Query(value = "include_episodes") Boolean includeEpisodes,
                    @AsInt @Query(value = "exact") Boolean exactMatch);

    @FormUrlEncoded
    @ApiMethod
    @POST("follow")
    Call<Void> follow(@Field("show_id") long showId);

    @ApiMethod
    @GET("follow")
    Call<Boolean> isFollowed(@Query("show_id") long showId);

    @FormUrlEncoded
    @ApiMethod
    @HTTP(method = "DELETE", path = "follow", hasBody = true)
    Call<Void> unFollow(@Field("show_id") long showId);

    @FormUrlEncoded
    @ApiMethod
    @POST("archive")
    Call<Void> archive(@Field("show_id") long showId);

    @ApiMethod
    @GET("archive")
    Call<Boolean> isArchived(@Query("show_id") long showId);

    @FormUrlEncoded
    @ApiMethod
    @HTTP(method = "DELETE", path = "archive", hasBody = true)
    Call<Void> unArchive(@Field("show_id") long showId);

    @FormUrlEncoded
    @ApiMethod
    @POST("show_progress")
    Call<Void> progress(@Field("show_id") long showId,
                        @Field("season") Integer season,
                        @Field("episode") Integer episode);

    @FormUrlEncoded
    @ApiMethod
    @POST("show_progress")
    Call<Void> progress(@Field("shows") String shows);

    @FormUrlEncoded
    @ApiMethod
    @HTTP(method = "DELETE", path = "show_progress", hasBody = true)
    Call<Void> deleteProgress(@Field("show_id") long showId,
                              @Field("season") Integer season,
                              @Field("episode") Integer episode);

    @FormUrlEncoded
    @ApiMethod
    @HTTP(method = "DELETE", path = "show_progress", hasBody = true)
    Call<Void> deleteProgress(@Field("shows") String shows);

    @ApiMethod
    @GET("episode")
    Call<Episode> episode(@Query("show_id") Long showId,
                          @Query("season_number") Integer season,
                          @Query("number") Integer episodeNo,
                          @Query("imdb_id") String imdbId,
                          @Query("episode_id") Long episode,
                          @Query("filename") String filename);


    @FormUrlEncoded
    @ApiMethod
    @POST("checkin")
    Call<Void> checkin(@Field("show_id") Long showId,
                       @Field("season_number") Integer season,
                       @Field("number") Integer episodeNo,
                       @Field("imdb_id") String imdbId,
                       @Field("episode_id") Long episode,
                       @Field("filename") String filename,
                       @Field("publish_on_ticker") Boolean ticker,
                       @Field("publish_on_twitter") Boolean twitter,
                       @Field("auto_follow") Boolean autoFollow);

    @ApiMethod
    @GET("checkin")
    Call<Boolean> isChecked(@Query("show_id") Long showId,
                            @Query("season_number") Integer season,
                            @Query("number") Integer episodeNo,
                            @Query("imdb_id") String imdbId,
                            @Query("episode_id") Long episode,
                            @Query("filename") String filename);

    @FormUrlEncoded
    @ApiMethod
    @HTTP(method = "DELETE", path = "checkin", hasBody = true)
    Call<Void> checkOut(@Field("show_id") Long showId,
                        @Field("season_number") Integer season,
                        @Field("number") Integer episodeNo,
                        @Field("imdb_id") String imdbId,
                        @Field("episode_id") Long episode,
                        @Field("filename") String filename);

    @FormUrlEncoded
    @ApiMethod
    @POST("progress")
    Call<Integer> setProgress(@Field("show_id") Long showId,
                              @Field("season_number") Integer season,
                              @Field("number") Integer episodeNo,
                              @Field("imdb_id") String imdbId,
                              @Field("episode_id") Long episode,
                              @Field("filename") String filename,
                              @Field("progress") Integer progress);

    @ApiMethod
    @GET("progress")
    Call<Integer> progress(@Query("show_id") Long showId,
                           @Query("season_number") Integer season,
                           @Query("number") Integer episodeNo,
                           @Query("imdb_id") String imdbId,
                           @Query("episode_id") Long episode,
                           @Query("filename") String filename);

    @FormUrlEncoded
    @ApiMethod
    @POST("emotion")
    Call<Void> setEmotion(@Field("episode_id") long id, @AsInt @Field("emotion_id") Emotion emotion);

    @FormUrlEncoded
    @ApiMethod
    @HTTP(method = "DELETE", path = "emotion", hasBody = true)
    Call<Void> deleteEmotion(@Field("episode_id") long episode);
}
