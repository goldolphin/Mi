package mi.task;

/**
 * @author caofuxiang
 *         2014-09-11 11:40
 */
public class InitTask<T, TResult> extends Task<TResult> {
    private final T input;
    private final ITask<?> task;

    public InitTask(T input, ITask<?> task) {
        this.input = input;
        this.task = task;
    }

    @Override
    public void execute(Object state, IContinuation cont, IScheduler scheduler) {
        task.execute(input, cont, scheduler);
    }

    @Override
    public void onExecute(Object state, IContinuation cont, ITask<?> previous, IScheduler scheduler) {
        throw new UnsupportedOperationException();
    }
}
