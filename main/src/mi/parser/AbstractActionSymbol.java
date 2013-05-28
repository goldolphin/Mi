package mi.parser;

/**
 * User: goldolphin
 * Time: 2013-05-25 12:51
 */
public abstract class AbstractActionSymbol implements ISymbol {
    public boolean parse() {
        boolean ret = match();
        if (ret) {
            action();
        }
        return ret;
    }

    protected abstract boolean match();

    protected abstract void action();
}
