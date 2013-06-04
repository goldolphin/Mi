package mi.regex;

import mi.stream.ICharStream;
import mi.stream.StringStream;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * User: goldolphin
 * Time: 2013-04-07 21:35
 */
public class Regex {
    private static final EndRegex End = new EndRegex();

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
    private ArrayList<GroupRegex> groups = new ArrayList<>();

    // Temporary variables.
    private int offset;

    public Regex(String pattern) {
        this.pattern = pattern;
        offset = 0;
        groupCount = 0;
        GroupRegex group = parseGroup();
        group.groupEnd().setNext(End);
        regex = group;
    }

    public Match startWith(String text) {
        return startWith(new StringStream(text));
    }

    public Match match(String text) {
        return match(new StringStream(text));
    }

    public Match startWith(ICharStream stream) {
        Match match = new Match(groupCount);
        match.setSucceed(regex.match(stream, match));
        return match;
    }

    public Match match(ICharStream stream) {
        Match match = new Match(groupCount);
        match.setSucceed(regex.match(stream, match) && stream.peek() == ICharStream.EOF);
        return match;
    }

    void dump() {
        System.out.println(pattern);
        regex.print(0);
    }

    char peek() {
        return pattern.charAt(offset);
    }

    char poll() {
        return pattern.charAt(offset++);
    }

    boolean end() {
        return offset >= pattern.length();
    }

    AbstractRegex parseOr(AbstractRegex end) {
        AbstractRegex seq = parseSequence(end);
        if (end()) {
            return seq;
        }
        char c = peek();
        switch (c) {
            case '|':
                poll();
                return new OrRegex(seq, parseOr(end));
            default:
                return seq;
        }
    }

    AbstractRegex parseSequence(AbstractRegex end) {
        if (end()) {
            return end;
        }
        char c = peek();
        switch (c) {
            case ')':
            case '|':
                return end;
            case '^':
                poll();
                HatRegex hat = new HatRegex();
                hat.setNext(parseSequence(end));
                return hat;
            case '$':
                poll();
                DollarRegex dollar = new DollarRegex();
                dollar.setNext(parseSequence(end));
                return dollar;
        }
        AbstractRegex term = parseTerm();
        if (term instanceof GroupRegex) {
            GroupRegex group = (GroupRegex) term;
            AbstractRegex cls = buildClosure(new RefRegex(group.groupNum()));
            cls.setNext(parseSequence(end));
            group.groupEnd().setNext(cls);
            return group;
        }
        AbstractRegex cls = buildClosure(term);
        cls.setNext(parseSequence(end));

        return cls;
    }

    AbstractRegex buildClosure(AbstractRegex term) {
        if (end()) {
            return term;
        }
        char c = peek();
        switch (c) {
            case '*':
                poll();
                return new AsteriskRegex(term);
            case '+':
                poll();
                return new PlusRegex(term);
            case '?':
                poll();
                return new QuestionRegex(term);
            default:
                return term;
        }
    }

    AbstractRegex parseTerm() {
        char c = peek();
        switch (c) {
            case '(':
                poll();
                AbstractRegex group = parseGroup();
                verify(poll() == ')', "need ')'");
                return group;
            default:
                return parseAtom();
        }
    }

    GroupRegex parseGroup() {
        int groupNum = groupCount ++;
        GroupRegex group = new GroupRegex(groupNum);
        groups.add(null);
        group.setNext(parseOr(group.groupEnd()));
        groups.set(groupNum, group);
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
                    verify(groups.get(ref) != null, "No such group");
                    return new RefRegex(ref);
                }
                return new CharRegex(follow);
            default:
                verify(!ControlCharSet.contains(c), "Control Chars");
                return new CharRegex(c);
        }
    }

    void verify(boolean cond, String msg) {
        String s = String.format("%s near '_###_' marker:\n %s_###_%s", msg, pattern.substring(0, offset), pattern.substring(offset));
        if (!cond) throw new RegexException(s);
    }
}
