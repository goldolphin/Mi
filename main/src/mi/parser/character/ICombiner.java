package mi.parser.character;

/**
 * User: goldolphin
 * Time: 2013-07-05 15:20
 */
public interface ICombiner<T> {
    T combine(T t1, T t2);
}
