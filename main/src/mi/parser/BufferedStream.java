package mi.parser;

/**
 * User: goldolphin
 * Time: 2013-05-28 21:20
 */
public class BufferedStream<T> implements IStream<T> {
    private final IStream<T> stream;
    private final Object[] buffer;
    private final int size;
    private int len;

    protected BufferedStream(IStream<T> stream, int bufferSize) {
        this.stream = stream;
        size = bufferSize;
        buffer = new Object[size];
        len = 0;
    }

    @Override
    public T poll() {
        if (len > 0) {
            return (T) buffer[len--];
        }
        return stream.poll();
    }

    @Override
    public void retract(T item) {
        if (len == size) {
            for (int i = 0; i < len; i++) {
                stream.retract((T) buffer[i]);
            }
            len = 0;
        }
        buffer[len++] = item;
    }
}
