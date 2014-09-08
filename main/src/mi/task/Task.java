package mi.task;

/**
 * @author goldolphin
 *         2014-09-06 15:12
 */
public abstract class Task<TResult> implements ITask<TResult> {
    private TResult result = null;

    @Override
    public TResult getResult() {
        return result;
    }

    /**
     * Set result of the task.
     * @param result
     */
    protected void setResult(TResult result) {
        this.result = result;
    }

    /**
     * Execute the task without any continuation.
     * @param scheduler
     */
    public void execute(IScheduler scheduler) {
        execute(IContinuation.END, scheduler);
    }

    public <SResult> Task<SResult> continueWith(Func1<TResult, SResult> func) {
        return new Func1Task<TResult, TResult, SResult>(this, func, false);
    }

    public <T, SResult> Task<SResult> flattenAndContinueWith( Func1<T, SResult> func) {
        return new Func1Task<T, TResult, SResult>(this, func, true);
    }

    public Waiter<TResult> continueWithWaiter() {
        return new Waiter<TResult>(this);
    }

    public static <TResult> Task<TResult> fromFunc(Func0<TResult> func) {
        return new Func0Task<TResult>(func);
    }

    public static <TResult> Task<TResult> fromCallback(Action1<CallbackTask.Context<TResult>> action) {
        return new CallbackTask<TResult>(action);
    }

    public static WhenAllTask continueWhenAll(ITask<?>... tasks) {
        return new WhenAllTask(tasks);
    }

    public static WhenAnyTask continueWhenAny(ITask<?>... tasks) {
        return new WhenAnyTask(tasks);
    }

    public static <TResult, TTask extends ITask<TResult>> Task<TResult> flatten(ITask<TTask> task) {
        return new FlattenTask<TResult, TTask>(task);
    }
}
