package mi.regex;

import mi.parser.stream.ICharStream;

/**
 * User: goldolphin
 * Time: 2013-04-04 17:42
 */
public class QuestionRegex extends AbstractRegex {
    private AtomRegex clause;

    public QuestionRegex(AtomRegex clause) {
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
        if (clause.match(stream, match) && next.match(stream, match)) {
            return true;
        }
        rollback(stream, match, len);
        return next.match(stream, match);
    }
}
