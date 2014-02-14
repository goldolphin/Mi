package mi.parser;

import java.util.Collection;
import java.util.HashMap;

/**
 * @author caofuxiang
 *         2014-02-13 19:15
 */
public class ParserBuilder {

    public Parser build(Grammar grammar) {
        NontermTable nontermTable = new NontermTable();

        // Construct automation.
        State.StateGenerator stateGenerator = new State.StateGenerator();
        State startState = stateGenerator.generate();
        for (Grammar.Production production: grammar.getProductions()) {
            Nonterm head = nontermTable.get(production.head.name());
            State current = startState;
            int len = production.body.length;
            for (int i = 0; i < len; i ++) {
                Grammar.ISymbol symbol = production.body[i];
                if (symbol instanceof Grammar.Nonterm) {
                    Nonterm n = nontermTable.get(symbol.name());
                    if (i == 0) {
                        // For left-most nonterm situation
                        current = n.addLeftMostRule(head.id, stateGenerator);
                    } else {
                        current = current.addNontermRule(n.id, head.id, stateGenerator);
                    }
                } else if (symbol instanceof Grammar.Term) {
                    current = current.addTermRule(symbol.name(), head.id, stateGenerator);
                } else {
                    throw new ParseException("Unknown symbol type: " + symbol.getClass());
                }
            }

            // Set accepting state
            current.setAcceptedNonterm(head.id);
        }

        // Extend rules' head set.


        // Build parser.
        Nonterm[] table = new Nonterm[nontermTable.size()];
        for (Nonterm n: nontermTable.getAll()) {
            table[n.id] = n;
        }

        return new Parser(startState, table);
    }

    private static class NontermTable {
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

        public Collection<Nonterm> getAll() {
            return map.values();
        }

        public int size() {
            return map.size();
        }
    }

}
