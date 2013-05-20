package mi.lexer;

/**
 * User: goldolphin
 * Time: 2013-05-19 16:12
 */
public class Token {
    public final String value;

    public Token(String value) {
        this.value = value;
    }

    public static final Token Package = new Token("package");
    public static final Token Import = new Token("import");

    public static final Token Let = new Token("let");
    public static final Token Template = new Token("template");
    public static final Token Type = new Token("type");

    public static final Token Fun = new Token("fun");
    public static final Token Struct = new Token("struct");

    public static final Token Assignment = new Token("=");
    public static final Token Colon = new Token(":");
    public static final Token Semicolon = new Token(";");
    public static final Token Comma = new Token(",");
    public static final Token LParen = new Token("(");
    public static final Token RParen = new Token(")");
    public static final Token RArrow = new Token("=>");
    public static final Token Lt = new Token("<");
    public static final Token Gt = new Token(">");
}
