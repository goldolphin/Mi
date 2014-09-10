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
    public void execute(Object state, IContinuation cont, IScheduler scheduler) {
        antecedent.execute(state, new Continuation(cont, this), scheduler);
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
        public void apply(Object state, ITask<?> previous, IScheduler scheduler) {
            if (!flattened) {
                flattened = true;
                ((ITask<?>) state).execute(null, this, scheduler);
            } else {
                scheduler.schedule(task, state, next, previous);
            }
        }
    }
}
