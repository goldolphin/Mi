package mi.task;

/**
 * Context of current control flow.
 * @param <TResult>
 */
public class Context<TResult> {
    private final ITask<TResult> task;
    private final IContinuation cont;
    private final IScheduler scheduler;

    /**
     * Constructor.
     * @param task
     * @param cont
     * @param scheduler
     */
    Context(ITask<TResult> task, IContinuation cont, IScheduler scheduler) {
        this.task = task;
        this.cont = cont;
        this.scheduler = scheduler;
    }

    /**
     * Resume the control flow with specified state.
     * @param state
     */
    public void resume(TResult state) {
        cont.apply(state, task, scheduler);
    };
}
