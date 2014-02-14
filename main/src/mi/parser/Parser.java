package mi.parser;

/**
 * @author caofuxiang
 *         2014-02-13 20:30
 */
public class Parser {
    private final State startState;
    private final Nonterm[] nontermTable;

    public Parser(State startState, Nonterm[] nontermTable) {
        this.startState = startState;
        this.nontermTable = nontermTable;
    }


}
