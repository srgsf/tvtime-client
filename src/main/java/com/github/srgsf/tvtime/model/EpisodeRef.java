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

public class EpisodeRef {
    @Json(name = "show_id")
    public final Long showId;

    @Json(name = "season")
    public final Integer season;

    @Json(name = "episode")
    public final Integer episode;

    public EpisodeRef(Long showId, Integer season, Integer episode) {
        this.showId = showId;
        this.season = season;
        this.episode = episode;
    }
}
