package mi.task;

/**
 * Context of current control flow.
 * @param <AResult>
 * @param <TResult>
 */
public class Context<AResult, TResult> {
    private final ITask<TResult> task;
    private final AResult state;
    private final IContinuation cont;
    private final IScheduler scheduler;

    /**
     * Constructor.
     * @param task
     * @param state
     * @param cont
     * @param scheduler
     */
    Context(ITask<TResult> task, AResult state, IContinuation cont, IScheduler scheduler) {
        this.task = task;
        this.state = state;
        this.cont = cont;
        this.scheduler = scheduler;
    }

    /**
     * Get current state.
     * @return
     */
    public AResult getState() {
        return state;
    }

    /**
     * Resume the control flow with specified new state.
     * @param newState
     */
    public void resume(TResult newState) {
        cont.apply(newState, task, scheduler);
    };
}
