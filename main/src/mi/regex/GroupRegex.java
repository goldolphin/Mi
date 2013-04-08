package mi.regex;

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
    }

    @Override
    public boolean match(Match match, String text, int start, int end) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private static class GroupEnd extends AbstractRegex {
        private int n;

        private GroupEnd(int n) {
            this.n = n;
        }

        @Override
        void print(int indent) {
            describe(indent, String.valueOf(n));
        }

        @Override
        public boolean match(Match match, String text, int start, int end) {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
