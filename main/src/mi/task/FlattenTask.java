package mi.task;

/**
 * @author goldolphin
 *         2014-09-08 12:03
 */
public class FlattenTask<TResult, TTask extends ITask<TResult>> extends Task<TResult> {
    private final ITask<TTask> task;

    public FlattenTask(ITask<TTask> task) {
        this.task = task;
    }

    @Override
    public void execute(IContinuation cont, IScheduler scheduler) {
        task.execute(new Continuation(cont, this), scheduler);
    }

    @Override
    protected TResult evaluate() {
        return task.getResult().getResult();
    }

    public static class Continuation implements IContinuation {
        private final IContinuation next;
        private final FlattenTask<?, ?> task;

        public Continuation(IContinuation next, FlattenTask<?, ?> task) {
            this.next = next;
            this.task = task;
        }

        @Override
        public void apply(IScheduler scheduler) {
            task.task.getResult().execute(new SeqTask.Continuation(next, task), scheduler);
        }
    }
}
