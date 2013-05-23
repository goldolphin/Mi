package mi.lexer;

import java.util.HashMap;

/**
 * User: goldolphin
 * Time: 2013-05-22 23:32
 */
public enum TokenType {
    Package("package"),
    Import("import"),

    Let("let"),
    Template("template"),
    Type("type"),

    Fun("fun"),
    Struct("struct"),

    Assignment("="),
    Colon(":"),
    Semicolon(";"),
    Comma(","),
    LParen("("),
    RParen(")"),
    RArrow("=>"),
    Lt("<"),
    Gt(">"),

    Id(null),
    Operator(null),
    Integer(null),
    Double(null),
    String(null),

    Comment(null),
    ;
    private static HashMap<String, TokenType> map = new HashMap<>();
    static {
        for (TokenType type: values()) {
            if (type.isPredefined()) {
                map.put(type.value, type);
            }
        }
    }

    public static TokenType of(String value) {
        return map.get(value);
    }

    public final String value;

    private TokenType(String value) {
        this.value = value;
    }

    public boolean isPredefined() {
        return value != null;
    }
}
