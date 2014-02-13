package mi.parser;

import mi.common.CharHashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * User: goldolphin
 * Time: 2014-02-09 20:25
 */
public class State {
    public static final int ANY = -1;
    private CharHashMap<TermRule> termRules = new CharHashMap<>();
    private HashMap<Integer, NontermRule> nontermRules = new HashMap<>();
    private int acceptedNontermId = ANY;

    public void setAcceptedNonterm(int nontermId) {
        if (hasAcceptedNonterm()) {
            throw new ParseException("The state already accepts nonterm: " + acceptedNontermId);
        }
        acceptedNontermId = nontermId;
    }
    
    public boolean hasAcceptedNonterm() {
        return acceptedNontermId != ANY;
    }
    
    public State getTermRule(char c, int headNontermId) {
        TermRule termRule = termRules.get(c);
        if (termRule != null && termRule.containsHeadNonterm(headNontermId)) {
            return termRule.target;
        }
        return null;
    }

    public State addTermRule(char t, int headNontermId, boolean toAccept) {
        TermRule rule = termRules.get(t);
        if (rule == null) {
            rule = new TermRule(t, new State());
            termRules.put(t, rule);
        }
        rule.addHeadNonterm(headNontermId);
        if (toAccept) {
            rule.target.setAcceptedNonterm(headNontermId);
        }
        return rule.target;
    }

    public State addTermRule(String t, int headNontermId, boolean toAccept) {
        int len = t.length();
        State current = this;
        for (int i = 0; i < len; i ++) {
            toAccept = toAccept && i == len-1;
            current = current.addTermRule(t.charAt(i), headNontermId, toAccept);
        }
        return current;
    }

    public State addNontermRule(int nontermId, int headNontermId, boolean toAccept) {
        NontermRule rule = nontermRules.get(nontermId);
        if (rule == null) {
            rule = new NontermRule(nontermId, new State());
            nontermRules.put(nontermId, rule);
        }
        rule.addHeadNonterm(headNontermId);
        if (toAccept) {
            rule.target.setAcceptedNonterm(headNontermId);
        }
        return rule.target;
    }

    public static abstract class AbstractRule {
        public final State target;
        private HashSet<Integer> headNontermIds = new HashSet<>();

        protected AbstractRule(State target) {
            this.target = target;
        }

        public void addHeadNonterm(int nontermId) {
            headNontermIds.add(nontermId);
        }

        public boolean containsHeadNonterm(int nontermId) {
            return nontermId == ANY || headNontermIds.contains(nontermId);
        }
    }

    public static class NontermRule extends AbstractRule {
        public final int nontermId;

        public NontermRule(int nontermId, State target) {
            super(target);
            this.nontermId = nontermId;
        }
    }

    public static class TermRule extends AbstractRule {
        public final char term;

        public TermRule(char term, State target) {
            super(target);
            this.term = term;
        }
    }
}
