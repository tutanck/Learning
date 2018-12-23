/*
 * Copyright (c) 2015 Algolia
 * http://www.algolia.com/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package algolia.com.demo.moviesearch.model;

import java.util.HashMap;
import java.util.Map;

/**
 * An highlighted results holds a data model object along with any number of highlights for this
 * object's attributes.
 *
 * @param <T> The data model type.
 */
public class HighlightedResult<T>
{
    private T result;
    private Map<String, Highlight> highlights = new HashMap<>();

    public HighlightedResult(T result)
    {
        this.result = result;
    }

    public T getResult()
    {
        return result;
    }

    public Highlight getHighlight(String attributeName)
    {
        return highlights.get(attributeName);
    }

    public void addHighlight(String attributeName, Highlight highlight)
    {
        highlights.put(attributeName, highlight);
    }
}
