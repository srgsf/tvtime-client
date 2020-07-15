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

public class Request {

    public static class Explore extends Pagination<Explore> {
    }

    public static class Library extends Pagination<Library> {
    }

    public static class Agenda extends Pagination<Agenda> {
        public Boolean includeWatched;

        public Agenda includeWatched(Boolean includeWatched) {
            this.includeWatched = includeWatched;
            return this;
        }
    }

    public static class Show {

        public final Long id;

        public final String name;

        public Boolean includeEpisodes;

        public boolean exactMatch;

        public Show(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Show includeEpisodes(Boolean includeEpisodes) {
            this.includeEpisodes = includeEpisodes;
            return this;
        }

        public Show exactMatch(boolean exactMatch) {
            this.exactMatch = exactMatch;
            return this;
        }
    }

    public enum Language {
        en, fr, es, it, pt
    }

    public static class ToWatch extends Pagination<ToWatch> {

        public Language language;

        public ToWatch language(Language language) {
            this.language = language;
            return this;
        }
    }

    public static class CheckIn {
        public final EpisodeId episode;
        public Boolean ticker;
        public Boolean twitter;
        public Boolean autoFollow;

        public CheckIn(EpisodeId episode) {
            this.episode = episode;
        }

        public CheckIn ticker(Boolean ticker) {
            this.ticker = ticker;
            return this;
        }

        public CheckIn twitter(Boolean twitter) {
            this.twitter = twitter;
            return this;
        }

        public CheckIn autoFollow(Boolean autoFollow) {
            this.autoFollow = autoFollow;
            return this;
        }
    }
}
