package mi.regex;

/**
 * User: goldolphin
 * Time: 2013-04-07 21:50
 */
public class EmptyRegex extends AbstractRegex {

    @Override
    public boolean match(Match match, String text, int start, int end) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
