package mi.regex;

import java.text.ParseException;
import java.util.HashSet;

/**
 * User: goldolphin
 * Time: 2013-04-07 21:35
 */
public class Regex {
    private static EndRegex End = new EndRegex();

    private static HashSet<Character> ControlCharSet = new HashSet<>();

    static {
        String str = "^$*+?.()[]{}|\\";
        for(int i = 0; i < str.length(); i ++) {
            ControlCharSet.add(str.charAt(i));
        }
    }

    private final String pattern;
    private final AbstractRegex regex;
    private int groupCount;
    private int n;

    public Regex(String pattern) {
        this.pattern = pattern;
        n = 0;
        groupCount = 0;
        regex = parseGroup();
        regex.setNext(End);
    }

    void dump() {
        regex.print(0);
    }

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

    AbstractRegex parseOr(AbstractRegex end) {
        AbstractRegex seq = parseSequence(end);
        if (end()) return seq;
        char c = poll();
        switch (c) {
            case '|':
                return new OrRegex(seq, parseOr(end));
            default:
                unpoll();
                return seq;
        }
    }

    AbstractRegex parseSequence(AbstractRegex end) {
        try {
            AbstractRegex term = parseTerm();
            if (term instanceof GroupRegex) {
                GroupRegex group = (GroupRegex) term;
                AbstractRegex cls = buildClosure(new RefRegex(group.groupNum()));
                group.groupEnd().setNext(cls);
                cls.setNext(parseSequence(end));
                return group;
            }
            AbstractRegex cls = buildClosure((AtomRegex) term);
            cls.setNext(parseSequence(end));
            return cls;
        } catch (RegexException e) {
            return end;
        }
    }

    AbstractRegex buildClosure(AtomRegex atom) {
        char c = poll();
        switch (c) {
            case '*':
                return new AsteriskRegex(atom);
            case '+':
                return new PlusRegex(atom);
            case '?':
                return new QuestionRegex(atom);
            default:
                unpoll();
                return atom;
        }
    }

    AbstractRegex parseTerm() {
        verify(!end(), "Meet end");
        char c = peek();
        switch (c) {
            case '(':
                verify(poll() == '(', "need '('");
                AbstractRegex group = parseGroup();
                verify(poll() == ')', "need ')'");
                return group;
            default:
                return parseAtom();
        }
    }

    AbstractRegex parseGroup() {
        GroupRegex group = new GroupRegex(groupCount);
        group.setNext(parseOr(group.groupEnd()));
        groupCount ++;
        return group;
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
