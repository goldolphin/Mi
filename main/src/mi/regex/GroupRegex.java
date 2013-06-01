package mi.regex;

import mi.parser.stream.ICharStream;

/**
 * User: goldolphin
 * Time: 2013-04-08 22:31
 */
public class GroupRegex extends AbstractRegex {
    private final int n;
    private final GroupEnd end;

    public GroupRegex(int n) {
        this.n = n;
        end = new GroupEnd(n);
    }

    public AbstractRegex groupEnd() {
        return end;
    }

    public int groupNum() {
        return n;
    }

    @Override
    void print(int indent) {
        describe(indent, String.valueOf(n));
        printChildren(indent, next);
    }

    @Override
    public boolean match(ICharStream stream, Match match) {
        match.setGroupStart(n, match.length());
        return next.match(stream, match);
    }

    private static class GroupEnd extends AbstractRegex {
        private int n;

        private GroupEnd(int n) {
            this.n = n;
        }

        @Override
        void print(int indent) {
            describe(indent, String.valueOf(n));
            next.print(indent);
        }

        @Override
        public boolean match(ICharStream stream, Match match) {
            match.setGroupEnd(n, match.length());
            if (n > 0) {
                rollback(stream, match, match.groupStart(n));
            }
            return next.match(stream, match);
        }
    }
}
