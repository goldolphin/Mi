package mi.regex;

/**
 * User: goldolphin
 * Time: 2013-04-07 21:15
 */
public abstract class AbstractRegex implements IRegex {
    protected AbstractRegex next;

    protected void setNext(AbstractRegex next) {
        this.next = next;
    }
}
