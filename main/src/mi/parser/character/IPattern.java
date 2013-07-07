package mi.parser.character;

import mi.parser.pattern.IContext;
import mi.stream.ICharStream;

/**
 * User: goldolphin
 * Time: 2013-07-03 23:12
 */
public interface IPattern<T> {
    T match(IParseStream stream);
}
