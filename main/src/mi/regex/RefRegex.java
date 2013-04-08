package mi.regex;

/**
 * User: goldolphin
 * Time: 2013-04-07 21:41
 */
public class RefRegex extends AtomRegex {
    private int n;

    public RefRegex(int n) {
        this.n = n;
    }

    @Override
    void print(int indent) {
        describe(indent, String.valueOf(n));
    }

    @Override
    public boolean match(Match match, String text, int start, int end) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
