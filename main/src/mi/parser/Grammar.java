package mi.parser;

import java.util.ArrayList;

/**
 * @author goldolphin
 *         2014-02-13 19:24
 */
public abstract class Grammar {
    private ArrayList<Production> productions = new ArrayList<>();

    public Grammar() {
        define();
    }

    protected Nonterm N(String name) {
        return new Nonterm(name);
    }

    protected Term T(String name) {
        return new Term(name);
    }

    protected void addProduction(Nonterm head, ISymbol ... symbols) {
        productions.add(new Production(head, symbols));
    }

    public ArrayList<Production> getProductions() {
        return productions;
    }

    protected abstract void define();

    public static class Production {
        public final Nonterm head;
        public final ISymbol[] body;

        public Production(Nonterm head, ISymbol[] body) {
            this.head = head;
            this.body = body;
        }
    }

    public static interface ISymbol {
        public String name();
    }

    public static class Nonterm implements ISymbol {
        private final String name;

        public Nonterm(String name) {
            this.name = name;
        }

        @Override
        public String name() {
            return name;
        }
    }

    public static class Term implements ISymbol {
        private final String name;

        public Term(String name) {
            this.name = name;
        }

        @Override
        public String name() {
            return name;
        }
    }
}
