package mi.parser;

import mi.parser.State.NontermRule;

/**
 * @author caofuxiang
 *         2014-02-13 20:21
 */
public class Nonterm {
    public final int id;
    public final String name;
    private NontermRule leftMostRule = null;

    public Nonterm(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public State addLeftMostRule(int headNontermId, State.StateGenerator generator) {
        if (leftMostRule == null) {
            leftMostRule = new NontermRule(id, generator.generate());
        }
        leftMostRule.addHeadNonterm(headNontermId);
        return leftMostRule.target;
    }
}
