/*
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.srgsf.tvtime.auth;

import com.github.srgsf.tvtime.ResponseBase;
import com.squareup.moshi.Json;

class AuthResponse extends ResponseBase {

    @Json(name = "access_token")
    String accessToken;

    @Json(name = "device_code")
    String deviceCode;

    @Json(name = "user_code")
    String userCode;

    @Json(name = "verification_url")
    String verificationUrl;

    @Json(name = "expires_in")
    Long expiresIn;

    @Json(name = "interval")
    Integer interval;
}
