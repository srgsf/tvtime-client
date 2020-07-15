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

import com.squareup.moshi.Json;

/**
 * Common response fields holder.
 *
 * @author srgsf
 * @since 1.0
 */
public abstract class ResponseBase {
    /**
     * Signals if response successful.
     *
     * @author srgsf
     * @since 1.0
     */
    public enum State {
        /**
         * Success.
         */
        OK,
        /**
         * Fail
         */
        KO
    }

    @Json(name = "code")
    public Integer code;

    @Json(name = "result")
    public State result;

    @Json(name = "message")
    public String message;

}
