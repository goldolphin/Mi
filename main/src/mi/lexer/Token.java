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

    public static final Token Package = new Token(Symbols.Package);
    public static final Token Import = new Token(Symbols.Import);

    public static final Token Let = new Token(Symbols.Let);
    public static final Token Template = new Token(Symbols.Template);

    public static final Token Type = new Token(Symbols.Type);
    public static final Token Fun = new Token(Symbols.Fun);
    public static final Token Struct = new Token(Symbols.Struct);

    public static final Token Assignment = new Token(Symbols.Assignment);
    public static final Token Colon = new Token(Symbols.Colon);
    public static final Token Semicolon = new Token(Symbols.Semicolon);
    public static final Token Comma = new Token(Symbols.Comma);
    public static final Token LParen = new Token(Symbols.LParen);
    public static final Token RParen = new Token(Symbols.RParen);
    public static final Token RArrow = new Token(Symbols.RArrow);
    public static final Token Lt = new Token(Symbols.Lt);
    public static final Token Gt = new Token(Symbols.Gt);
}
