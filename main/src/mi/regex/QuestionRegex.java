package mi.regex;

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
    public boolean match(Match match, String text, int start, int end) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
