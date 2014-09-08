package mi.task;

/**
 * @author goldolphin
 *         2014-09-05 22:46
 */
public interface IScheduler {
    /**
     * Schedule and execute a task itself, i.e. invoke {@link ITask#onExecute}.
     * @param task
     * @param cont
     * @param previous
     */
    public void schedule(ITask<?> task, IContinuation cont, ITask<?> previous);
}