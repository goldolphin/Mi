package mi.parser.character;

/**
 * User: goldolphin
 * Time: 2013-07-05 15:00
 */
class Seq<T> implements IPattern<T> {
    private final IPattern<T> pattern1;
    private final IPattern<T> pattern2;
    private final ICombiner<T> combiner;

    public Seq(IPattern<T> pattern1, IPattern<T> pattern2, ICombiner<T> combiner) {
        this.pattern1 = pattern1;
        this.pattern2 = pattern2;
        this.combiner = combiner;
    }

    @Override
    public T match(IParseStream stream) {
        int pos = stream.tell();
        T t1 = pattern1.match(stream);
        if (t1 != null) {
            T t2 = pattern2.match(stream);
            if (t2 != null) {
                return combiner.combine(t1, t2);
            }
            stream.retractTo(pos);
        }
        return null;
    }
}
