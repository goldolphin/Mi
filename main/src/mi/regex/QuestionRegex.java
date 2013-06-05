package mi.regex;

import mi.stream.ICharStream;

/**
 * User: goldolphin
 * Time: 2013-04-04 17:42
 */
public class QuestionRegex extends AbstractRegex {
    private AbstractRegex clause;

    public QuestionRegex(AbstractRegex clause) {
        this.clause = clause;
    }

    @Override
    void print(int indent) {
        describe(indent);
        printChildren(indent, clause);
        next.print(indent);
    }

    @Override
    public boolean match(ICharStream stream, Match match) {
        int len = match.length();
        clause.setNext(next);
        if (clause.match(stream, match)) {
            return true;
        }
        rollback(stream, match, len);
        return next.match(stream, match);
    }
}
