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

import com.squareup.moshi.FromJson;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.ToJson;

public enum Emotion {
    Good(1),
    Fun(2),
    Wow(3),
    Sad(4),
    Soso(6),
    Bad(7);

    final int id;

    Emotion(int id) {
        this.id = id;
    }

    public static class Adapter {
        @ToJson
        public Integer toJson(Emotion e) {
            if (e == null) {
                return null;
            }
            return e.id;
        }

        @ToJson
        public EmotionObject toJsonObject(@AsObject Emotion e) {
            if (e == null) {
                return null;
            }
            return new EmotionObject(e.id, e.name());
        }

        @FromJson
        public Emotion fromJson(Integer r) {
            if (r == null) {
                return null;
            }
            for (Emotion e : Emotion.values()) {
                if (r == e.id) {
                    return e;
                }
            }
            throw new JsonDataException("unknown emotion: " + r);
        }

        @FromJson
        @AsObject
        public Emotion fromJson(EmotionObject o) {
            return fromJson(o.id);
        }
    }

    @SuppressWarnings("unused")
    private static class EmotionObject {
        @Json(name = "id")
        int id;
        @Json(name = "name")
        String name;

        EmotionObject(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
