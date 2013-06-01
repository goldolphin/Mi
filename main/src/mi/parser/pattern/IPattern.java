package mi.parser.pattern;

import mi.parser.stream.IStream;

/**
 * User: goldolphin
 * Time: 2013-05-29 01:22
 */
public interface IPattern<T> {
    boolean match(IStream<T> stream, IHandler<T> handler);
}