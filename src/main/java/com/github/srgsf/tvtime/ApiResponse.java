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

import com.github.srgsf.tvtime.model.Emotion;
import com.github.srgsf.tvtime.model.Episode;
import com.github.srgsf.tvtime.model.Show;
import com.github.srgsf.tvtime.model.User;
import com.squareup.moshi.Json;

import java.util.ArrayList;
import java.util.List;

class ApiResponse extends ResponseBase {

    @Json(name = "user")
    User user;

    @Json(name = "episodes")
    List<Episode> episodes = new ArrayList<>();

    @Json(name = "shows")
    List<Show> shows = new ArrayList<>();

    @Json(name = "show")
    Show show;

    @Json(name = "episode")
    Episode episode;

    @Json(name = "progress")
    Integer progress;

    @Json(name = "emotion_id")
    Emotion emotion;


}
