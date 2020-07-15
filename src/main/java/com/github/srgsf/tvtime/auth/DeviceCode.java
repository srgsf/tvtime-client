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

/**
 *
 * OAuth 2.0 limited device flow response.
 *
 * @author srgsf
 * @since 1.0
 */
public class DeviceCode {

    public final String deviceCode;

    public final String userCode;

    public final String verificationUrl;

    public final Long expiresIn;

    public final Integer interval;

    DeviceCode(String deviceCode, String userCode, String verificationUrl, Long expiresIn, Integer interval) {
        this.deviceCode = deviceCode;
        this.userCode = userCode;
        this.verificationUrl = verificationUrl;
        this.expiresIn = expiresIn;
        this.interval = interval;
    }
}
