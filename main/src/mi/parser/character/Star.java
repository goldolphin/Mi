package mi.parser.character;

/**
 * User: goldolphin
 * Time: 2013-07-07 23:52
 */
class Star<T> implements IPattern<T> {
    private final IPattern<T> prefix;
    private final IPattern<T> pattern;
    private final ICombiner<T> combiner;

    /**
     *
     * @param prefix null for an empty prefix
     * @param pattern
     * @param combiner
     */
    public Star(IPattern<T> prefix, IPattern<T> pattern, ICombiner<T> combiner) {
        this.prefix = prefix;
        this.pattern = pattern;
        this.combiner = combiner;
    }

    @Override
    public T match(IParseStream stream) {
        T result = null;
        if (prefix != null) {
            result = prefix.match(stream);
            if (result == null) {
                return null;
            }
        }
        while (true) {
            T t = pattern.match(stream);
            if (t == null) {
                return result;
            }
            result = combiner.combine(result, t);
        }
    }
}
