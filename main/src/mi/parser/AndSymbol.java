package mi.parser;

/**
 * User: goldolphin
 * Time: 2013-05-29 01:24
 */
public class AndSymbol implements ISymbol {
    private ISymbol left;
    private ISymbol right;

    public AndSymbol(ISymbol left, ISymbol right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean parse() {
        return left.parse() && right.parse();
    }
}
