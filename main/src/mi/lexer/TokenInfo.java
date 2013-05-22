package mi.lexer;

/**
 * User: goldolphin
 * Time: 2013-05-22 04:30
 */
public class TokenInfo {
    public final int lineNum;
    public final int colNum;
    public final Token token;

    public TokenInfo(int lineNum, int colNum, Token token) {
        this.lineNum = lineNum;
        this.colNum = colNum;
        this.token = token;
    }
}
