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

public class EpisodeId {

    public final String[] filenames;

    public final Long episodeId;

    public final String imdbId;

    public final EpisodeRef episodeRef;

    /**
     * API limit up to 10 elements.
     *
     * @param filename file names.
     * @return new instance of {@link EpisodeId}
     */
    public static EpisodeId file(String... filename) {
        if (!(filename == null || filename.length <= 10)) {
            throw new IllegalArgumentException("api accepts up to 10 elements.");
        }
        return new EpisodeId(filename, null, null, null);
    }

    public static EpisodeId imdb(String imdbId) {
        return new EpisodeId(null, null, imdbId, null);

    }

    public static EpisodeId episode(Long episode) {
        return new EpisodeId(null, episode, null, null);

    }

    public static EpisodeId episode(Long showId, Integer season, Integer episode) {
        return new EpisodeId(null, null, null, new EpisodeRef(showId, season, episode));
    }

    EpisodeId(String[] filenames, Long episodeId, String imdbId, EpisodeRef episodeRef) {
        if (filenames == null) {
            filenames = new String[0];
        }

        if (episodeRef == null) {
            episodeRef = new EpisodeRef(null, null, null);
        }

        this.filenames = filenames;
        this.episodeId = episodeId;
        this.imdbId = imdbId;
        this.episodeRef = episodeRef;

    }

}
