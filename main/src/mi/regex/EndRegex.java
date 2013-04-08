package mi.regex;

/**
 * User: goldolphin
 * Time: 2013-04-08 20:38
 */
public class EndRegex extends AbstractRegex {

    @Override
    void print(int indent) {
        describe(indent);
    }

    @Override
    public boolean match(Match match, String text, int start, int end) {
        return true;
    }
}
