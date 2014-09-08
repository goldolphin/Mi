package mi.task;

/**
 * @author goldolphin
 *         2014-09-05 22:46
 */
public interface IScheduler {
    public void schedule(ITask<?> task, IContinuation cont);
}
