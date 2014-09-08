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
    public void onExecute(IContinuation cont, ITask<?> previous, IScheduler scheduler) {
        setResult(func.apply());
        System.out.println("Evaluate complete: " + getResult());

        cont.apply(this, scheduler);
    }

    @Override
    public void execute(IContinuation cont, IScheduler scheduler) {
        scheduler.schedule(this, cont, null);
    }
}
