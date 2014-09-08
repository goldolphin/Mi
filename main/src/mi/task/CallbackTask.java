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
        scheduler.schedule(this, cont, null);
    }

    @Override
    public void onExecute(final IContinuation cont, final ITask<?> previous, final IScheduler scheduler) {
        Context<TResult> context = new Context<TResult>(this, cont, scheduler);
        action.apply(context);
    }

    public static class Context<TResult> {
        private final CallbackTask<TResult> task;
        private final IContinuation cont;
        private final IScheduler scheduler;

        private Context(CallbackTask<TResult> task, IContinuation cont, IScheduler scheduler) {
            this.task = task;
            this.cont = cont;
            this.scheduler = scheduler;
        }

        public void resume(TResult value) {
            task.setResult(value);
            cont.apply(task, scheduler);
        };
    }
}
