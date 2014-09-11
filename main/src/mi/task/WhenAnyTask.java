package mi.task;

/**
 * A task which will complete when any specified task complete.
 * @author goldolphin
 *         2014-09-06 21:27
 */
public class WhenAnyTask extends Task<WhenAnyTask.Result> {
    private final ITask<?>[] tasks;

    public WhenAnyTask(ITask<?> ... tasks) {
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
        private final WhenAnyTask task;
        private int complete = 0;

        public Continuation(IContinuation next, WhenAnyTask task) {
            this.next = next;
            this.task = task;
        }

        @Override
        public void apply(Object state, ITask<?> previous, IScheduler scheduler) {
            complete += 1;
            int total = task.getTasks().length;
            if (complete > total) {
                throw new IllegalStateException("Invalid complete value: " + complete + " exceeds " + total);
            } else if (complete == 1) {
                next.apply(new Result(previous, state), task, scheduler);
            }
        }
    }

    /**
     * Result of a WhenAnyTask
     */
    public static class Result {
        public final ITask<?> task;
        public final Object result;

        public Result(ITask<?> task, Object result) {
            this.task = task;
            this.result = result;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "task=" + task +
                    ", result=" + result +
                    '}';
        }
    }
}
