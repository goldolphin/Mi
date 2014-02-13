package mi.parser;

import java.util.HashMap;

/**
 * @author caofuxiang
 *         2014-02-13 19:15
 */
public class ParserBuilder {

    public void build(Grammar grammar) {
        NontermTable nontermTable = new NontermTable();

        State startState = new State();
        for (Grammar.Production production: grammar.getProductions()) {
            Nonterm head = nontermTable.get(production.head.name());
            State current = startState;
            int len = production.body.length;
            for (int i = 0; i < len; i ++) {
                Grammar.ISymbol symbol = production.body[i];
                boolean toAccept = i == len-1;
                if (symbol instanceof Grammar.Nonterm) {
                    Nonterm n = nontermTable.get(symbol.name());
                    // For left-most nonterm situation
                    if (i == 0) {
                        // Self reference
                        if (i == len-1 && n == head) {
                            throw new ParseException("Self reference: " + head.name);
                        }



                    }
                    current = current.addNontermRule(n.Id, head.Id, toAccept);
                } else if (symbol instanceof Grammar.Term) {
                    current = current.addTermRule(symbol.name(), head.Id, toAccept);
                } else {
                    throw new ParseException("Unknown symbol type: " + symbol.getClass());
                }
            }
        }
    }

    public static class NontermTable {
        private HashMap<String, Nonterm> map = new HashMap<>();
        private int currentId = 0;

        public Nonterm get(String name) {
            Nonterm n = map.get(name);
            if (n == null) {
                n = new Nonterm(currentId ++, name);
                map.put(name, n);
            }
            return n;
        }
    }
}
