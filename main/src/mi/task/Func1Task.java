package mi.task;

/**
 * @author goldolphin
 *         2014-09-06 16:48
 */
public class Func1Task<T, TResult> extends SeqTask<T, TResult> {
    private final Func1<T, TResult> func;

    public Func1Task(ITask<T> antecedent, Func1<T, TResult> func) {
        super(antecedent);
        this.func = func;
    }

    @Override
    public TResult evaluate() {
        return func.apply(antecedent.getResult());
    }
}
