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

import com.github.srgsf.tvtime.auth.DeviceCode;
import com.github.srgsf.tvtime.auth.TvTimeAuthClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;

class AuthClientIT {

    private static TvTimeAuthClient authClient;

    @BeforeAll
    static void setup() {
        authClient = new TvTimeAuthClient.Builder()
                .clientCredentials(System.getProperty("client_id"),
                        System.getProperty("client_secret")).build();
    }

    @Test
    void deviceCode() throws IOException {
        Response<DeviceCode> response = authClient.deviceCode();
        Assertions.assertTrue(response.isSuccessful());
        Assertions.assertNotNull(response.body());
        DeviceCode code = response.body();

        Response<String> tokenResp = authClient.accessToken(code.deviceCode);
        Assertions.assertFalse(tokenResp.isSuccessful());
        Assertions.assertNotNull(tokenResp.errorBody());
        String error = authClient.error(tokenResp.errorBody());
        Assertions.assertTrue(TvTimeAuthClient.ERR_AUTHORIZATION_PENDING.equals(error) ||
                TvTimeAuthClient.ERR_SLOW_DOWN.equals(error));
    }

}
