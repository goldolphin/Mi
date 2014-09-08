package mi.task;

/**
 * @author goldolphin
 *         2014-09-06 18:05
 */
public class Waiter<TResult> extends SeqTask<TResult, TResult> {
    private final Object lock = new Object();
    private volatile boolean isReady = false;

    public Waiter(ITask<TResult> antecedent) {
        super(antecedent);
    }

    public boolean isReady() {
        return isReady;
    }

    @Override
    public TResult getResult() {
        synchronized (lock) {
            while (!isReady) {
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
    protected TResult evaluate() {
        return antecedent.getResult();
    }

    @Override
    public void execute(IContinuation cont, IScheduler scheduler)  {
        synchronized (lock) {
            super.execute(cont, scheduler);
            isReady = true;
            lock.notifyAll();
        }
    }
}
