package mi.task;

/**
 * @author goldolphin
 *         2014-09-06 21:27
 */
public class WhenAnyTask extends Task<ITask<?>> {
    private final ITask<?>[] tasks;

    public WhenAnyTask(ITask<?> ... tasks) {
        this.tasks = tasks;
    }

    public ITask<?>[] getTasks() {
        return tasks;
    }


    @Override
    public void execute(IContinuation cont, IScheduler scheduler) {
        Continuation newCont = new Continuation(cont, this);
        for (ITask<?> task: tasks) {
            task.execute(newCont, scheduler);
        }
    }

    @Override
    public void onExecute(IContinuation cont, ITask<?> previous, IScheduler scheduler) {
        System.out.println("Evaluate complete: " + getResult());

        cont.apply(this, scheduler);
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
        public void apply(ITask<?> previous, IScheduler scheduler) {
            complete += 1;
            int total = task.getTasks().length;
            if (complete > total) {
                throw new IllegalStateException("Invalid complete value: " + complete + " exceeds " + total);
            } else if (complete == 1) {
                task.onExecute(next, previous, scheduler);
            }
        }
    }
}
