package mi.task;

/**
 * @author goldolphin
 *         2014-09-08 21:47
 */
public class CallbackTask<TResult> extends Task<TResult> {
    private final Action1<Context<TResult>> action;

    public CallbackTask(Action1<Context<TResult>> action) {
        this.action = action;
    }

    @Override
    public void execute(IContinuation cont, IScheduler scheduler) {
        scheduler.schedule(this, null, cont, null);
    }

    @Override
    public void onExecute(Object state, final IContinuation cont, final ITask<?> previous, final IScheduler scheduler) {
        Context<TResult> context = new Context<TResult>(this, cont, scheduler);
        action.apply(context);
    }
}
