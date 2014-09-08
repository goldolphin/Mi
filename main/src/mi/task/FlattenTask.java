package mi.task;

/**
 * @author goldolphin
 *         2014-09-08 12:03
 */
public class FlattenTask<TResult, TTask extends ITask<TResult>> extends SeqTask<TTask, TResult> {

    public FlattenTask(ITask<TTask> antecedent) {
        super(antecedent, true);
    }

    @Override
    public void execute(IContinuation cont, IScheduler scheduler) {
        antecedent.execute(new Continuation(cont, this), scheduler);
    }

    @Override
    protected TResult evaluate(Object value) {
        return (TResult) value;
    }

    public static class Continuation extends SeqTask.Continuation {
        private boolean flattened = false;

        public Continuation(IContinuation next, ITask<?> task) {
            super(next, task);
        }

        @Override
        public void apply(ITask<?> previous, IScheduler scheduler) {
            Object result = previous.getResult();
            if (!flattened) {
                flattened = true;
                ((ITask<?>) result).execute(this, scheduler);
            } else {
                scheduler.schedule(task, next, previous);
            }
        }
    }
}
