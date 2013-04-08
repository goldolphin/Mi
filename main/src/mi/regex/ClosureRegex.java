package mi.regex;

/**
 * User: goldolphin
 * Time: 2013-04-04 17:23
 */
public class ClosureRegex extends AbstractRegex {
    private AtomRegex clause;
    private int lower;
    private int upper;

    public ClosureRegex(AtomRegex clause, int lower, int upper) {
        this.clause = clause;
        this.lower = lower;
        this.upper = upper;
    }

    @Override
    void print(int indent) {
        describe(indent, String.format("%d, %d", lower, upper));
        printChildren(indent, clause);
    }

    @Override
    public boolean match(Match match, String text, int start, int end) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
