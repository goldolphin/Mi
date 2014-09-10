package mi.task;

/**
 * A task.
 * @param <TResult> result type.
 * @author goldolphin
 *         2014-09-05 22:46
 */
public interface ITask<TResult> {
    public static final ITask<?> BEGIN_TASK = new ITask<Object>() {
        @Override
        public void execute(Object state, IContinuation cont, ITask<?> antecedent, IScheduler scheduler) {
            onExecute(state, cont, null, scheduler);
        }

        @Override
        public void onExecute(Object state, IContinuation cont, ITask<?> previous, IScheduler scheduler) {
            cont.apply(state, this, scheduler);
        }
    };

    /**
     * Execute the task with specified init state, continuation and antecedent.
     * @param state the init state of the task.
     * @param cont
     * @param antecedent
     * @param scheduler
     */
    public void execute(Object state, IContinuation cont, ITask<?> antecedent, IScheduler scheduler);

    /**
     * Action when the task is executed. Continuation should be applied usually.
     * @param state
     * @param cont
     * @param previous
     * @param scheduler
     */
    public void onExecute(Object state, IContinuation cont, ITask<?> previous, IScheduler scheduler);
}
