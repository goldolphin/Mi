package mi.task;

/**
 * @author goldolphin
 *         2014-09-06 15:38
 */
public abstract class SeqTask<AResult, TResult> extends Task<TResult> {
    protected final ITask<AResult> parent;
    private final boolean flatten;

    public SeqTask(ITask<AResult> parent, boolean flatten) {
        this.parent = parent;
        this.flatten = flatten;
    }

    @Override
    public void execute(Object state, IContinuation cont, ITask<?> antecedent, IScheduler scheduler) {
        this.parent.execute(state,
                flatten ? new FlattenContinuation(cont, this) : new Continuation(cont, this),
                antecedent,
                scheduler);
    }

    @Override
    public void onExecute(Object state, IContinuation cont, ITask<?> previous, IScheduler scheduler) {
        TResult result = evaluate(state);
        System.out.println("Evaluate complete: " + result);

        cont.apply(result, this, scheduler);
    }

    /**
     * Evaluate the task, and the returned value will be set as the result.
     * @return
     */
    protected abstract TResult evaluate(Object value);

    public static class FlattenContinuation extends Continuation {
        public FlattenContinuation(IContinuation next, ITask<?> task) {
            super(next, task);
        }

        @Override
        public void apply(Object state, ITask<?> previous, IScheduler scheduler) {
            if (state instanceof ITask<?>) {
                ((ITask<?>) state).execute(null, this, BEGIN_TASK, scheduler);
            } else {
                scheduler.schedule(task, state, next, previous);
            }
        }
    }
}
