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

import com.github.srgsf.tvtime.ApiMethod;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

class ResponseConverterFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        for (Annotation a : annotations) {
            if (a instanceof ApiMethod) {
                return new ResponseBodyConverter<>(
                        retrofit.nextResponseBodyConverter(this, AuthResponse.class, annotations));
            }
        }

        return super.responseBodyConverter(type, annotations, retrofit);
    }

    static class ResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        final Converter<ResponseBody, AuthResponse> delegate;

        ResponseBodyConverter(Converter<ResponseBody, AuthResponse> delegate) {
            this.delegate = delegate;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T convert(ResponseBody value) throws IOException {
            AuthResponse r = delegate.convert(value);
            if (r == null) {
                return null;
            }

            if (r.accessToken != null) {
                return (T) r.accessToken;
            }

            if (r.deviceCode != null) {
                return (T) new DeviceCode(r.deviceCode, r.userCode, r.verificationUrl, r.expiresIn, r.interval);
            }
            return null;
        }
    }
}
