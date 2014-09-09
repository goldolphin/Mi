package mi.task;

/**
 * Scheduler that execute tasks in local thread.
 * @author goldolphin
 *         2014-09-06 15:10
 */
public class SynchronizedScheduler implements IScheduler {
    @Override
    public void schedule(ITask<?> task, Object state, IContinuation cont, ITask<?> previous) {
        System.out.println("Run: " + task);
        task.onExecute(state, cont, previous, this);
    }
}
