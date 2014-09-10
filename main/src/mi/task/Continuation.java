package mi.task;

/**
 * Basic Continuation.
 * @author caofuxiang
 *         2014-09-10 20:03
 */
public class Continuation implements IContinuation {
    protected final IContinuation next;
    protected final ITask<?> task;

    public Continuation(IContinuation next, ITask<?> task) {
        this.next = next;
        this.task = task;
    }

    @Override
    public void apply(Object state, ITask<?> previous, IScheduler scheduler) {
        scheduler.schedule(task, state, next, previous);
    }
}
