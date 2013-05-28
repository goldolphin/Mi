package mi.parser;

/**
 * User: goldolphin
 * Time: 2013-05-28 21:06
 */
public interface IStream<T> {
    T poll();
    void retract(T item);
}
