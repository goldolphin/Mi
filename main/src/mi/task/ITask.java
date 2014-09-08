package mi.task;

/**
 * A task.
 * @author goldolphin
 *         2014-09-05 22:46
 */
public interface ITask<TResult> {
    public TResult getResult();

    /**
     * Make a plan for this task.
     * @param cont
     * @param scheduler
     */
    public void plan(IContinuation cont, IScheduler scheduler);

    /**
     * Execute the task itself.
     * @param cont
     * @param scheduler
     */
    public void execute(IContinuation cont, IScheduler scheduler);
}