package mi.lexer;

import java.util.HashSet;

/**
 * User: goldolphin
 * Time: 2013-05-20 20:29
 */
public final class Symbols {
    public static final String Package = "package";
    public static final String Import = "import";

    public static final String Let = "let";
    public static final String Template = "template";
    public static final String Type = "type";

    public static final String Fun = "fun";
    public static final String Struct = "struct";

    public static final String Assignment = "=";
    public static final String Colon = ":";
    public static final String Semicolon = ";";
    public static final String Comma = ",";
    public static final String LParen = "(";
    public static final String RParen = ")";
    public static final String RArrow = "=>";
    public static final String Lt = "<";
    public static final String Gt = ">";

    public static final String Comment = "//";

    public static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    public static boolean isUpperCase(char c) {
        return c >= 'A' && c <= 'Z';
    }

    public static boolean isLowerCase(char c) {
        return c >= 'a' && c <= 'z';
    }

    public static boolean isLetter(char c) {
        return isUpperCase(c) || isLowerCase(c);
    }

    public static boolean isIdHead(char c) {
        return isLetter(c) || c == '_';
    }

    public static boolean isIdTail(char c) {
        return isIdHead(c) || isDigit(c);
    }

    private static final HashSet<Character> OperatorChars = new HashSet<>();

    static {
        for (char c: "|&^!=<>+-*/%~".toCharArray()) {
            OperatorChars.add(c);
        }
    }

    public static boolean isOperatorPart(char c) {
        return OperatorChars.contains(c);
    }

    public static boolean isNewLineChar(char c){
        return c == '\n';
    }

    public static boolean isSpaceChar(char c) {
        return c == ' ' || c == '\t';
    }
}
