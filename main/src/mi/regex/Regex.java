package mi.regex;

import java.text.ParseException;
import java.util.HashSet;

/**
 * User: goldolphin
 * Time: 2013-04-07 21:35
 */
public class Regex {
    private static EmptyRegex Empty = new EmptyRegex();
    private static HashSet<Character> ControlCharSet = new HashSet<>();

    static {
        String str = "^$*+?()[]{}|\\";
        for(int i = 0; i < str.length(); i ++) {
            ControlCharSet.add(str.charAt(i));
        }
    }

    private int groupCount;
    private String pattern;
    private int n;

    char peek() {
        return pattern.charAt(n);
    }

    char poll() {
        return pattern.charAt(n++);
    }

    void unpoll() {
        n--;
    }

    boolean end() {
        return n >= pattern.length();
    }

    AbstractRegex parseOr(AbstractRegex base) {
        return null;
    }

    AbstractRegex parseTerm() {
        char c = peek();
        switch (c) {
            case '(':
               // return parseGroup();

        }
        return null;
    }

    AtomRegex parseAtom() {
        char c = poll();
        switch (c) {
            case '\\':
                char next = poll();
                if(Character.isDigit(next)) return new RefRegex(Integer.parseInt(Character.toString(next)));
                return new CharRegex(next);
        }
        return null;
    }
}
