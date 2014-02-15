package mi.parser;

import java.util.HashSet;

/**
* User: goldolphin
* Time: 2014-02-14 22:28
*/
public abstract class Transition {
    public final State target;
    private HashSet<Integer> headNontermIds = new HashSet<>();

    Transition(State target) {
        this.target = target;
    }

    public void addHeadNonterm(int nontermId) {
        headNontermIds.add(nontermId);
    }

    public boolean containsHeadNonterm(int nontermId) {
        return nontermId == Nonterm.ANY || headNontermIds.contains(nontermId);
    }

    public HashSet<Integer> getHeadNontermIds() {
        return headNontermIds;
    }

    @Override
    public String toString() {
        return String.format("(%d, %s)", target.id, headNontermIds.toString());
    }

    public static class NontermTransition extends Transition {
        public final int nontermId;

        public NontermTransition(int nontermId, State target) {
            super(target);
            this.nontermId = nontermId;
        }

        @Override
        public String toString() {
            return String.format("(N: %d, %s)", nontermId, super.toString());
        }
    }

    public static class TermTransition extends Transition {
        public final char term;

        public TermTransition(char term, State target) {
            super(target);
            this.term = term;
        }

        @Override
        public String toString() {
            return String.format("(T: %c, %s)", term, super.toString());
        }
    }
}
