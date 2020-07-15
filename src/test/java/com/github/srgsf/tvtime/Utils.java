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

import okhttp3.mockwebserver.RecordedRequest;
import okio.Buffer;
import org.junit.jupiter.api.Assertions;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.time.Instant;
import java.util.Objects;
import java.util.function.Supplier;

class Utils {

    static Buffer readFromFile(String path) {
        Buffer rv = new Buffer();
        try (InputStream is = Utils.class.getClassLoader().getResourceAsStream(path)) {
            rv.readFrom(Objects.requireNonNull(is));
        } catch (IOException ex) {
            Assertions.fail("Unable to read " + path);
        }
        return rv;
    }

    static boolean checkAuthorization(RecordedRequest request) {
        String retVal = request.getHeader("TVST_ACCESS_TOKEN");
        if (retVal == null) {
            return false;
        }
        Assertions.assertEquals("TOKEN", retVal);
        return true;
    }

    static Supplier<String> tokenProvider(){
        final String token = System.getProperty("token");
        return () -> token;
    }

    static <T> Response<T> callWithTimeout(Call<T> call) throws IOException, InterruptedException {
        Response<T> response = call.execute();
        if (response.isSuccessful()) {
            return response;
        }
        if (response.code() == HttpURLConnection.HTTP_FORBIDDEN) {
            String wnd = response.headers().get("X-RateLimit-Reset");
            if (wnd == null) {
                return response;
            }
            long timeout = Long.parseLong(wnd) - Instant.now().getEpochSecond();
            Thread.sleep(timeout * 1000);
            return call.clone().execute();
        }
        return response;
    }
}
