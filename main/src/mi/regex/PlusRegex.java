package mi.regex;

/**
 * User: goldolphin
 * Time: 2013-04-04 17:55
 */
public class PlusRegex extends AbstractRegex {
    private AtomRegex clause;

    public PlusRegex(AtomRegex clause) {
        this.clause = clause;
    }

    @Override
    void print(int indent) {
        describe(indent);
        printChildren(indent, clause);
    }

    @Override
    public boolean match(Match match, String text, int start, int end) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
