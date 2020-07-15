/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.srgsf.tvtime.model;

import com.squareup.moshi.Json;

import java.time.LocalDate;
import java.time.LocalTime;

public class Episode extends Identity {

    @Json(name = "air_date")
    public final LocalDate aired;

    @Json(name = "air_time")
    public final LocalTime airTime;

    @Json(name = "number")
    public final Integer number;

    @Json(name = "season_number")
    public final Integer season;

    @Json(name = "network")
    public final String network;

    @Json(name = "name")
    public final String name;

    @Json(name = "overview")
    public final String overview;

    @Json(name = "images")
    public final Images images;

    @Json(name = "show")
    public final Show show;

    @Json(name = "nb_comments")
    public final Integer comments;

    @Json(name = "is_new")
    public final Boolean isNew;

    @Json(name = "seen")
    public final Boolean seen;

    @Json(name = "mean_rate")
    public final Double rate;

    @Json(name = "emotion")
    @AsObject
    public final Emotion emotion;

    @Json(name = "previous_episode")
    public final EpisodeListItem prev;

    @Json(name = "next_episode")
    public final EpisodeListItem next;

    @SuppressWarnings("unused")
    Episode(Long id, LocalDate aired, LocalTime airTime, Integer number, Integer season, String network,
            String name, String overview, Images images, Show show, Integer comments, Boolean isNew,
            Boolean seen, Double rate, Emotion emotion, EpisodeListItem prev, EpisodeListItem next) {
        super(id);
        this.aired = aired;
        this.airTime = airTime;
        this.number = number;
        this.season = season;
        this.network = network;
        this.name = name;
        this.overview = overview;
        this.images = images;
        this.show = show;
        this.comments = comments;
        this.isNew = isNew;
        this.seen = seen;
        this.rate = rate;
        this.emotion = emotion;
        this.prev = prev;
        this.next = next;
    }

    public static class EpisodeListItem extends Identity {

        @Json(name = "season_number")
        public final Integer season;

        @Json(name = "number")
        public final Integer number;

        @Json(name = "show")
        public final Identity show;

        @Json(name = "all_images")
        public final Images images;

        @SuppressWarnings("unused")
        EpisodeListItem(Long id, Integer season, Integer number, Identity show, Images images) {
            super(id);
            this.season = season;
            this.number = number;
            this.show = show;
            this.images = images;
        }
    }
}
