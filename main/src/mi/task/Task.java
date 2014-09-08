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

    protected void setResult(TResult result) {
        this.result = result;
    }

    @Override
    public void execute(IContinuation cont, IScheduler scheduler) {
        result = evaluate();
        System.out.println("Evaluate complete: " + result);

        cont.apply(scheduler);
    }

    public void schedule(IScheduler scheduler) {
        plan(IContinuation.END, scheduler);
    }

    protected abstract TResult evaluate();

    public <SResult> Task<SResult> continueWith(Func1<TResult, SResult> func) {
        return new Func1Task<TResult, SResult>(this, func);
    }

    public <SResult> Task<SResult> continueWithAndFlatten(Func1<TResult, Task<SResult>> func) {
        return flatten(continueWith(func));
    }

    public Waiter<TResult> continueWithWaiter() {
        return new Waiter<TResult>(this);
    }

    public static <TResult> Task<TResult> fromFunc(Func0<TResult> func) {
        return new Func0Task<TResult>(func);
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