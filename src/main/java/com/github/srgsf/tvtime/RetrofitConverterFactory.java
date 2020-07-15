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
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;


class RetrofitConverterFactory extends Converter.Factory {


    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        for (Annotation a : annotations) {
            if (a instanceof AsInt) {
                if (type.equals(Boolean.class)) {
                    return (Converter<Boolean, String>) value -> Boolean.TRUE.equals(value) ? "1" : "0";
                }
                if (type.equals(Emotion.class)) {
                    return new Converter<Emotion, String>() {
                        private final Emotion.Adapter adapter = new Emotion.Adapter();

                        @Override
                        public String convert(Emotion value) {
                            Integer r = adapter.toJson(value);
                            if (r == null) {
                                return null;
                            }
                            return r.toString();
                        }
                    };
                }
            }
        }
        return null;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        for (Annotation a : annotations) {
            if (a instanceof ApiMethod) {
                return new ResponseBodyConverter(
                        retrofit.nextResponseBodyConverter(this, ApiResponse.class, annotations));
            }
        }
        return super.responseBodyConverter(type, annotations, retrofit);
    }

    private static class ResponseBodyConverter implements Converter<ResponseBody, Object> {
        final Converter<ResponseBody, ApiResponse> delegate;


        public ResponseBodyConverter(Converter<ResponseBody, ApiResponse> delegate) {
            this.delegate = delegate;
        }


        @Override
        public Object convert(ResponseBody value) throws IOException {
            ApiResponse response = delegate.convert(value);
            if (response == null || response.result != ResponseBase.State.OK) {
                return null;
            }

            if (response.code != null) {
                return response.code == 1;
            }

            if (response.user != null) {
                return response.user;
            }

            if (response.show != null) {
                return response.show;
            }

            if (response.episode != null) {
                return response.episode;
            }

            if (response.progress != null) {
                return response.progress;
            }

            if (!response.episodes.isEmpty()) {
                return response.episodes;
            }

            if (!response.shows.isEmpty()) {
                return response.shows;
            }
            return null;
        }
    }
}
