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
    public void onExecute(Object state, IContinuation cont, ITask<?> previous, IScheduler scheduler) {
        TResult result = func.apply();
        System.out.println("Evaluate complete: " + result);

        cont.apply(result, this, scheduler);
    }

    @Override
    public void execute(Object state, IContinuation cont, ITask<?> antecedent, IScheduler scheduler) {
        antecedent.execute(state, new Continuation(cont, this), BEGIN_TASK, scheduler);
    }
}
