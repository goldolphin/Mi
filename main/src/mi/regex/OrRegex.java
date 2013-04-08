package mi.regex;

/**
 * User: goldolphin
 * Time: 2013-04-04 01:14
 */
public class OrRegex extends AbstractRegex {
    private AbstractRegex left;
    private AbstractRegex right;

    public OrRegex(AbstractRegex left, AbstractRegex right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean match(Match match, String text, int start, int end) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
