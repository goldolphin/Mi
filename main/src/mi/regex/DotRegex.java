package mi.regex;

/**
 * User: goldolphin
 * Time: 2013-04-08 02:50
 */
public class DotRegex extends AtomRegex {
    @Override
    void print(int indent) {
        describe(indent);
    }

    @Override
    public boolean match(Match match, String text, int start, int end) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}