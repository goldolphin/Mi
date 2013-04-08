package mi.regex;

/**
 * User: goldolphin
 * Time: 2013-04-04 17:04
 */
public class CharRegex extends AtomRegex {
    private char c;

    public CharRegex(char c) {
        this.c = c;
    }

    @Override
    void print(int indent) {
        describe(indent, String.valueOf(c));
    }

    @Override
    public boolean match(Match match, String text, int start, int end) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
