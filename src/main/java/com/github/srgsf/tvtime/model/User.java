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

import java.util.HashMap;
import java.util.Map;

public class User extends Identity {

    @Json(name = "name")
    public final String name;

    @Json(name = "image")
    public final String image;

    @Json(name = "all_images")
    public final Map<String, String> images = new HashMap<>();

    @SuppressWarnings("unused")
    User(Long id, String name, String image) {
        super(id);
        this.name = name;
        this.image = image;
    }
}
