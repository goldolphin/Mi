package mi.task;

/**
 * A task which will complete when all specified tasks complete.
 * @author goldolphin
 *         2014-09-06 18:27
 */
public class WhenAllTask extends Task<Object[]> {
    private final ITask<?>[] tasks;

    public WhenAllTask(ITask<?> ... tasks) {
        this.tasks = tasks;
    }

    /**
     * Get tasks to wait.
     * @return
     */
    public ITask<?>[] getTasks() {
        return tasks;
    }

    @Override
    public void execute(Object state, IContinuation cont, IScheduler scheduler) {
        Continuation newCont = new Continuation(cont, this);
        for (ITask<?> task: tasks) {
            task.execute(state, newCont, scheduler);
        }
    }

    @Override
    public void onExecute(Object state, IContinuation cont, ITask<?> previous, IScheduler scheduler) {
        throw new UnsupportedOperationException();
    }

    public static class Continuation implements IContinuation {
        private final IContinuation next;
        private final WhenAllTask task;
        private final Object[] results;
        private int complete = 0;

        public Continuation(IContinuation next, WhenAllTask task) {
            this.next = next;
            this.task = task;
            results = new Object[task.tasks.length];
        }

        @Override
        public void apply(Object state, ITask<?> previous, IScheduler scheduler) {
            complete += 1;
            int total = task.tasks.length;
            if (complete > total) {
                throw new IllegalStateException("Invalid complete value: " + complete + " exceeds " + total);
            }
            setResult(state, previous);
            if (complete == total) {
                next.apply(results, task, scheduler);
            }
        }

        private void setResult(Object state, ITask<?> task) {
            for (int i = 0; i < results.length; i ++) {
                if (task == this.task.tasks[i]) {
                    results[i] = state;
                    break;
                }
            }
        }
    }
}
