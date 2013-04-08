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
        String str = "^$*+?.()[]{}|\\";
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

    AbstractRegex parseOr() {
        AbstractRegex seq = parseSequence();
        char c = poll();
        switch (c) {
            case '|':
                return new OrRegex(seq, parseOr());
            default:
                unpoll();
                return seq;
        }
    }

    AbstractRegex parseSequence() {
        AbstractRegex cls;
        try {
            cls = parseClosure();
        } catch (RegexException e) {
            return Empty;
        }
        cls.setNext(parseSequence());
        return cls;
    }

    AbstractRegex parseClosure() {
        AbstractRegex term = parseTerm();
        char c = poll();
        switch (c) {
            case '*':
            case '+':
            case '?':
        }
        return term;
    }

    AbstractRegex parseTerm() {
        char c = peek();
        switch (c) {
            case '(':
                return parseGroup();
            default:
                return parseAtom();
        }
    }

    AbstractRegex parseGroup() {
        verify(poll() == '(', "need '('");
        AbstractRegex or = parseOr();
        verify(poll() == '(', "need '('");
        return or;
    }

    AtomRegex parseAtom() {
        char c = poll();
        switch (c) {
            case '.':
                return new DotRegex();
            case '\\':
                char follow = poll();
                if(Character.isDigit(follow)) {
                    int ref = Integer.parseInt(Character.toString(follow));
                    verify(ref < groupCount, "No such group");
                    return new RefRegex(ref);
                }
                return new CharRegex(follow);
            default:
                verify(!ControlCharSet.contains(c), "Control Chars");
                return new CharRegex(c);
        }
    }

    void verify(boolean cond, String msg) {
        String s = String.format("%s near '###' marker:\n %s ### %s", msg, pattern.substring(0, n), pattern.substring(n));
        if (!cond) throw new RegexException(s);
    }
}
