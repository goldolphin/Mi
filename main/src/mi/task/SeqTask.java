package mi.task;

/**
 * @author goldolphin
 *         2014-09-06 15:38
 */
public abstract class SeqTask<AResult, TResult> extends Task<TResult> {
    protected final ITask<AResult> antecedent;

    public SeqTask(ITask<AResult> antecedent) {
        this.antecedent = antecedent;
    }

    @Override
    public void plan(IContinuation cont, IScheduler scheduler) {
        antecedent.plan(new Continuation(cont, this), scheduler);
    }

    public static class Continuation implements IContinuation {
        private final IContinuation next;
        private final ITask<?> task;

        public Continuation(IContinuation next, ITask<?> task) {
            this.next = next;
            this.task = task;
        }

        @Override
        public void apply(IScheduler scheduler) {
            scheduler.schedule(task, next);
        }
    }
}
