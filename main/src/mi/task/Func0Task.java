package mi.task;

/**
 * @author goldolphin
 *         2014-09-07 00:33
 */
public class Func0Task<TResult> extends Task<TResult> {
    private final Func0<TResult> func;

    public Func0Task(Func0<TResult> func) {
        this.func = func;
    }

    @Override
    protected TResult evaluate() {
        return func.apply();
    }

    @Override
    public void plan(IContinuation cont, IScheduler scheduler) {
        scheduler.schedule(this, cont);
    }
}
