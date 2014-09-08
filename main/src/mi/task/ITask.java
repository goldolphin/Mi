package mi.task;

/**
 * A task.
 * @author goldolphin
 *         2014-09-05 22:46
 */
public interface ITask<TResult> {
    /**
     * Get result of the task.
     * @return
     */
    public TResult getResult();

    /**
     * Execute the task with specified continuation.
     * @param cont
     * @param scheduler
     */
    public void execute(IContinuation cont, IScheduler scheduler);

    /**
     * Action when the task is executed. Continuation should be applied usually.
     * @param cont
     * @param previous
     * @param scheduler
     */
    public void onExecute(IContinuation cont, ITask<?> previous, IScheduler scheduler);
}