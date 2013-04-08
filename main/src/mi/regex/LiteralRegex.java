package mi.regex;

/**
 * User: goldolphin
 * Time: 2013-04-04 01:14
 */
public class LiteralRegex extends AtomRegex {

    private String literal;

    public LiteralRegex(String literal) {
        this.literal = literal;
    }

    @Override
    public boolean match(Match match, String text, int start, int end) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
