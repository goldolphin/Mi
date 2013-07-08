package mi.parser.character;

import mi.stream.ICharStream;

/**
 * User: goldolphin
 * Time: 2013-07-08 00:19
 */
public class ParserBuilder<T> {
    public final ICombiner<T> SELECT_FIRST = new ICombiner<T>() {
        @Override
        public T combine(T t1, T t2) {
            return t1;
        }
    };

    public final ICombiner<T> SELECT_SECOND = new ICombiner<T>() {
        @Override
        public T combine(T t1, T t2) {
            return t2;
        }
    };

    public static IParseStream parseStream(ICharStream source) {
        return new ParseStream(source);
    }

    public IPattern<T> or(IPattern<T> pattern1, IPattern<T> pattern2) {
        return new Or<T>(pattern1, pattern2);
    }

    public IPattern<T> seq(IPattern<T> pattern1, IPattern<T> pattern2, ICombiner<T> combiner) {
        return new Seq<T>(pattern1, pattern2, combiner);
    }

    public IPattern<T> star(IPattern<T> prefix, IPattern<T> pattern, ICombiner<T> combiner) {
        return new Seq<T>(prefix, pattern, combiner);
    }

    public IPattern<T> leftRec(IPattern pattern) {
        return new LeftRec<T>(pattern);
    }
}
