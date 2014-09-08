package mi.task;

/**
 * @author goldolphin
 *         2014-09-06 15:10
 */
public class SynchronizedScheduler implements IScheduler {
    @Override
    public void schedule(ITask<?> task, IContinuation cont, ITask<?> previous) {
        System.out.println("Run: " + task);
        task.onExecute(cont, previous, this);
    }
}
