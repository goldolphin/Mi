package mi.task;

/**
 * @author caofuxiang
 *         2014-09-10 20:16
 */
public class TaskSeqTask<AResult, TResult> extends SeqTask<AResult, TResult> {
    private final ITask<TResult> task;

    public TaskSeqTask(ITask<AResult> parent, ITask<TResult> task, boolean flatten) {
        super(parent, flatten);
        this.task = task;
    }

    @Override
    protected TResult evaluate(Object value) {
        return null;
    }

    @Override
    public void execute(Object state, IContinuation cont, ITask<?> antecedent, IScheduler scheduler) {
        super.execute(state, cont, antecedent, scheduler);
    }

    @Override
    public void onExecute(Object state, IContinuation cont, ITask<?> previous, IScheduler scheduler) {
        super.onExecute(state, cont, previous, scheduler);
    }
}
