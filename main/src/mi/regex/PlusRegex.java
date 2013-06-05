package mi.regex;

import mi.stream.ICharStream;

/**
 * User: goldolphin
 * Time: 2013-04-04 17:55
 */
public class PlusRegex extends AsteriskRegex {

    public PlusRegex(AbstractRegex clause) {
        super(clause);
    }

    @Override
    public boolean match(ICharStream stream, Match match) {
        int len = match.length();
        clause.setNext(this);
        if (clause.match(stream, match)) {
            return true;
        }
        rollback(stream, match, len);
        clause.setNext(next);
        return clause.match(stream, match);
    }
}
