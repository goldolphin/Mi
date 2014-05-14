package mi.stack;

/**
 * @author goldolphin
 *         2014-05-12 23:34
 */
public class ForkableStack<T> implements IForkableStack<T> {
    private final ArrayStack<Entry<T>> buffer;
    private int top;

    private ForkableStack(ArrayStack<Entry<T>> buffer, int top) {
        this.buffer = buffer;
        this.top = top;
    }

    public ForkableStack(int capacity) {
        this(new ArrayStack<Entry<T>>(capacity), -1);
    }

    @Override
    public T pop() {
        T data = topEntry().data;
        top = topEntry().parent;
        return data;
    }

    @Override
    public void push(T data) {
        buffer.push(new Entry<T>(data, top));
        top = buffer.size()-1;
    }

    public ForkableStack<T> fork() {
        return new ForkableStack<>(buffer, top);
    }

    public boolean isEmpty() {
        return top < 0;
    }

    private Entry<T> topEntry() {
        return buffer.get(top);
    }

    private static class Entry<T> {
        final T data;
        final int parent;

        private Entry(T data, int parent) {
            this.data = data;
            this.parent = parent;
        }
    }
}
