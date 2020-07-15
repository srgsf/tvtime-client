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

class Pagination<T extends Pagination<T>> {


    public Integer page;

    public Integer limit;

    @SuppressWarnings("unchecked")
    public T page(Integer page) {
        this.page = page;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T limit(Integer limit) {
        this.limit = limit;
        if (limit != null && page == null) {
            page = 0;
        }
        return (T) this;
    }
}
