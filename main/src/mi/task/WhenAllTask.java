package mi.task;

/**
 * @author goldolphin
 *         2014-09-06 18:27
 */
public class WhenAllTask extends Task<WhenAllTask> {
    private final ITask<?>[] tasks;

    public WhenAllTask(ITask<?> ... tasks) {
        this.tasks = tasks;
    }

    public ITask<?>[] getTasks() {
        return tasks;
    }

    @Override
    protected WhenAllTask evaluate() {
        return this;
    }

    @Override
    public void execute(IContinuation cont, IScheduler scheduler) {
        Continuation newCont = new Continuation(cont, this);
        for (ITask<?> task: tasks) {
            task.execute(newCont, scheduler);
        }
    }

    public static class Continuation implements IContinuation {
        private final IContinuation next;
        private final WhenAllTask task;
        private int complete = 0;

        public Continuation(IContinuation next, WhenAllTask task) {
            this.next = next;
            this.task = task;
        }

        @Override
        public void apply(IScheduler scheduler) {
            complete += 1;
            int total = task.getTasks().length;
            if (complete > total) {
                throw new IllegalStateException("Invalid complete value: " + complete + " exceeds " + total);
            } else if (complete == total) {
                scheduler.schedule(task, next);
            }
        }
    }
}
