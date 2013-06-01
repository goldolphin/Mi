package mi.parser.pattern;

import mi.parser.stream.ICharStream;

/**
 * User: goldolphin
 * Time: 2013-06-01 14:15
 */
public interface ICharPattern {
    boolean match(ICharStream stream, ICharHandler handler);
}