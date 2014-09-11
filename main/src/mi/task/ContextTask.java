package mi.task;

/**
 * @author goldolphin
 *         2014-09-08 21:47
 */
public class ContextTask<TResult> extends Task<TResult> {
    private final Action1<Context<?, TResult>> action;

    public ContextTask(Action1<Context<?, TResult>> action) {
        this.action = action;
    }

    @Override
    public void execute(Object state, IContinuation cont, IScheduler scheduler) {
        scheduler.schedule(this, state, cont, null);
    }

    @Override
    public void onExecute(Object state, final IContinuation cont, final ITask<?> previous, final IScheduler scheduler) {
        Context<?, TResult> context = new Context<Object, TResult>(this, state, cont, scheduler);
        action.apply(context);
    }
}