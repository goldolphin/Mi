package mi.stack;

/**
 * @author goldolphin
 *         2014-05-13 00:11
 */
public class CombinedStack<T> implements IStack<T>, Forkable<CombinedStack<T>> {
    private final ForkableStack<T> buffer;
    private final IRandomAccessStack<T> underlying;
    private int underlyingTop;

    private CombinedStack(ForkableStack<T> buffer, IRandomAccessStack<T> underlying, int underlyingTop) {
        this.buffer = buffer;
        this.underlying = underlying;
        this.underlyingTop = underlyingTop;
    }

    public CombinedStack(int capacity, IRandomAccessStack<T> underlying) {
        this(new ForkableStack<T>(capacity), underlying, underlying.size()-1);
    }

    @Override
    public CombinedStack<T> fork() {
        return new CombinedStack<>(buffer.fork(), underlying, underlyingTop);
    }

    @Override
    public T pop() {
        if (buffer.isEmpty()) {
            T data = underlying.get(underlyingTop);
            underlyingTop -= 1;
            return data;
        }
        return buffer.pop();
    }

    @Override
    public void push(T data) {
        buffer.push(data);
    }
}
