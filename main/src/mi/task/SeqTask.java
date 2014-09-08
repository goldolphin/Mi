package mi.task;

/**
 * @author goldolphin
 *         2014-09-06 15:38
 */
public abstract class SeqTask<AResult, TResult> extends Task<TResult> {
    protected final ITask<AResult> antecedent;
    private final boolean flatten;

    public SeqTask(ITask<AResult> antecedent, boolean flatten) {
        this.antecedent = antecedent;
        this.flatten = flatten;
    }

    @Override
    public void execute(IContinuation cont, IScheduler scheduler) {
        antecedent.execute(
                flatten ? new FlattenContinuation(cont, this) : new Continuation(cont, this),
                scheduler);
    }

    @Override
    public void onExecute(IContinuation cont, ITask<?> previous, IScheduler scheduler) {
        setResult(evaluate(previous.getResult()));
        System.out.println("Evaluate complete: " + getResult());

        cont.apply(this, scheduler);
    }

    /**
     * Evaluate the task, and the returned value will be set as the result.
     * @return
     */
    protected abstract TResult evaluate(Object value);

    public static class Continuation implements IContinuation {
        protected final IContinuation next;
        protected final ITask<?> task;

        public Continuation(IContinuation next, ITask<?> task) {
            this.next = next;
            this.task = task;
        }

        @Override
        public void apply(ITask<?> previous, IScheduler scheduler) {
            scheduler.schedule(task, next, previous);
        }
    }

    public static class FlattenContinuation extends Continuation {
        public FlattenContinuation(IContinuation next, ITask<?> task) {
            super(next, task);
        }

        @Override
        public void apply(ITask<?> previous, IScheduler scheduler) {
            Object result = previous.getResult();
            if (result instanceof ITask<?>) {
                ((ITask<?>) result).execute(this, scheduler);
            } else {
                scheduler.schedule(task, next, previous);
            }
        }
    }
}
