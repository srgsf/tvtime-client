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

import com.github.srgsf.tvtime.model.EpisodeRef;
import com.squareup.moshi.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

class JsonAdapters {

    static class LocalDateAdapter {

        @ToJson
        String toJson(LocalDate d) {
            if (d == null) {
                return null;
            }
            return d.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }

        @FromJson
        LocalDate fromJson(String s) {
            if (s == null || s.trim().length() == 0) {
                return null;
            }
            return LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    static class LocalTimeAdapter {

        @ToJson
        String toJson(LocalTime t) {
            if (t == null) {
                return null;
            }
            return t.format(DateTimeFormatter.ISO_LOCAL_TIME);
        }

        @FromJson
        LocalTime fromJson(String s) {
            if (s == null || s.trim().length() == 0) {
                return null;
            }
            return LocalTime.parse(s, DateTimeFormatter.ISO_LOCAL_TIME);
        }
    }

    static JsonAdapter<List<EpisodeRef>> episodeRefAdapter() {
        Moshi moshi = new Moshi.Builder().build();
        return moshi.adapter(Types.newParameterizedType(List.class, EpisodeRef.class));
    }
}
