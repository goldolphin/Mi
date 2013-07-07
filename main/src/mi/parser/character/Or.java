package mi.parser.character;

/**
 * User: goldolphin
 * Time: 2013-07-05 15:28
 */
class Or<T> implements IPattern<T> {
    private final IPattern<T> pattern1;
    private final IPattern<T> pattern2;

    public Or(IPattern<T> pattern1, IPattern<T> pattern2) {
        this.pattern1 = pattern1;
        this.pattern2 = pattern2;
    }

    @Override
    public T match(IParseStream stream) {
        T t1 = pattern1.match(stream);
        if (t1 != null) {
            return t1;
        }
        T t2 = pattern2.match(stream);
        if (t2 != null) {
            return t2;
        }
        return null;
    }
}
