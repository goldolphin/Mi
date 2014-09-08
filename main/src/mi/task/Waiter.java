package mi.task;

/**
 * @author goldolphin
 *         2014-09-06 18:05
 */
public class Waiter<TResult> extends SeqTask<TResult, TResult> {
    private final Object lock = new Object();
    private volatile boolean isComplete = false;

    public Waiter(ITask<TResult> antecedent) {
        super(antecedent, false);
    }

    /**
     * Whether the task is complete.
     * @return
     */
    public boolean isComplete() {
        return isComplete;
    }

    /**
     * Get the result in a blocking way.
     * @return
     */
    @Override
    public TResult getResult() {
        synchronized (lock) {
            while (!isComplete) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return super.getResult();
        }
    }

    @Override
    protected TResult evaluate(Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onExecute(IContinuation cont, ITask<?> previous, IScheduler scheduler)  {
        synchronized (lock) {
            setResult((TResult) previous.getResult());
            isComplete = true;
            lock.notifyAll();
        }
        cont.apply(this, scheduler);
    }
}
