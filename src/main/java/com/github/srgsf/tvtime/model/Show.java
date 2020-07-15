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

import java.util.ArrayList;
import java.util.List;

public class Show extends Identity {

    @Json(name = "name")
    public final String name;

    @Json(name = "overview")
    public final String overview;

    @Json(name = "aired_episodes")
    public final Integer airedEpisodes;

    @Json(name = "seen_episodes")
    public final Integer seenEpisodes;

    @Json(name = "number_of_seasons")
    public final Integer seasons;

    @Json(name = "runtime")
    public final Integer runtime;

    @Json(name = "last_seen")
    public final Episode lastSeen;

    @Json(name = "last_aired")
    public final Episode lastAired;

    @Json(name = "next_aired")
    public final Episode nextAired;

    @Json(name = "nb_followers")
    public final Integer followers;

    @Json(name = "followed")
    public final Boolean followed;

    @Json(name = "archived")
    public final Boolean archived;

    @Json(name = "status")
    public final String status;

    @Json(name = "all_images")
    private final Images all_images;

    @Json(name = "images")
    private final Images images;

    @Json(name = "episodes")
    public final List<Episode> episodes = new ArrayList<>();

    @Json(name = "genre")
    public final String genre;

    @Json(name = "hashtag")
    public final String hashTag;

    public Images images() {
        if (all_images != null) {
            return all_images;
        }
        return images;
    }

    @SuppressWarnings("unused")
    Show(Long id, String name, String overview, Integer airedEpisodes, Integer seenEpisodes, Integer seasons,
         Integer runtime, Episode lastSeen, Episode lastAired, Episode nextAired, Integer followers, Boolean followed,
         Boolean archived, String status, Images all_images, Images images, String genre, String hashTag) {
        super(id);
        this.name = name;
        this.overview = overview;
        this.airedEpisodes = airedEpisodes;
        this.seenEpisodes = seenEpisodes;
        this.seasons = seasons;
        this.runtime = runtime;
        this.lastSeen = lastSeen;
        this.lastAired = lastAired;
        this.nextAired = nextAired;
        this.followers = followers;
        this.followed = followed;
        this.archived = archived;
        this.status = status;
        this.all_images = all_images;
        this.images = images;
        this.genre = genre;
        this.hashTag = hashTag;
    }
}
