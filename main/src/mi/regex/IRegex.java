package mi.regex;

/**
 * User: goldolphin
 * Time: 2013-04-04 01:14
 */
public interface IRegex {

    public boolean match(Match match, String text, int start, int end);
}
