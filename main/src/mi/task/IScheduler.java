package mi.task;

/**
 * @author goldolphin
 *         2014-09-05 22:46
 */
public interface IScheduler {
    /**
     * Schedule and execute a task itself, i.e. invoke {@link ITask#onExecute(IContinuation, IScheduler)}.
     * @param task
     * @param cont should be passed to {@link ITask#onExecute(IContinuation, IScheduler)} as a parameter.
     */
    public void schedule(ITask<?> task, IContinuation cont);
}
