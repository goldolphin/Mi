package mi.regex;

import mi.stream.ICharStream;

/**
 * User: goldolphin
 * Time: 2013-04-04 17:23
 */
@Deprecated
public class ClosureRegex extends AbstractRegex {
    private AbstractRegex clause;
    private int lower;
    private int upper;

    public ClosureRegex(AbstractRegex clause, int lower, int upper) {
        this.clause = clause;
        this.lower = lower;
        this.upper = upper;
        clause.setNext(this);
    }

    @Override
    void print(int indent) {
        describe(indent, String.format("%d, %d", lower, upper));
        printChildren(indent, clause);
        next.print(indent);
    }

    private boolean match(ICharStream stream, Match match, int n) {
        if (n > upper) {
            return false;
        }
        int len = match.length();
        if (clause.match(stream, match) && match(stream, match, n+1)) {
            return true;
        }
        if (n < lower) {
            return false;
        }
        rollback(stream, match, len);
        return next.match(stream, match);
    }

    @Override
    public boolean match(ICharStream stream, Match match) {
        return match(stream, match, 0);
    }
}
