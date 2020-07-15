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
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


interface Authentication {
    @FormUrlEncoded
    @ApiMethod(secured = false)
    @POST("access_token")
    Call<String> tokenViaAuthorizationCode(@Field("code") String code,
                                           @Field("client_id") String clientId,
                                           @Field("client_secret") String clientSecret,
                                           @Field("redirect_uri") String redirectUri);

    @FormUrlEncoded
    @ApiMethod(secured = false)
    @POST("access_token")
    Call<String> tokenViaDeviceCode(@Field("client_id") String clientId,
                                    @Field("client_secret") String clientSecret,
                                    @Field("code") String code);

    @FormUrlEncoded
    @ApiMethod(secured = false)
    @POST("device/code")
    Call<DeviceCode> deviceCode(@Field("client_id") String clientId);
}

