package mi.task;

/**
 * Abstract base class that simplify usage of tasks.
 * @param <TResult> result type.
 * @author goldolphin
 *         2014-09-06 15:12
 */
public abstract class Task<TResult> implements ITask<TResult> {
    /**
     * Execute the task with specified init state but no continuation.
     * @param state the init state of the task.
     * @param scheduler
     */
    public void execute(Object state, IScheduler scheduler) {
        execute(state, IContinuation.END_CONTINUATION, BEGIN_TASK, scheduler);
    }

    /**
     * After this task completes, continue to execute the specified function.
     * @param func
     * @param <SResult>
     * @return
     */
    public <SResult> Task<SResult> continueWith(Func1<TResult, SResult> func) {
        return new Func1SeqTask<TResult, TResult, SResult>(this, func, false);
    }

    /**
     * After this task completes, flatten the result and continue to execute the specified function.
     * @param func
     * @param <T>
     * @param <SResult>
     * @return
     */
    public <T, SResult> Task<SResult> flattenAndContinueWith( Func1<T, SResult> func) {
        return new Func1SeqTask<T, TResult, SResult>(this, func, true);
    }

    /**
     * After this task completes, continue to execute a waiter.
     * @return
     */
    public Waiter<TResult> continueWithWaiter() {
        return new Waiter<TResult>(this);
    }

    /**
     * Create a task from a function.
     * @param func
     * @param <TResult>
     * @return
     */
    public static <TResult> Task<TResult> fromFunc(Func0<TResult> func) {
        return new Func0Task<TResult>(func);
    }

    /**
     * Create a task from a function.
     * @param func
     * @param <TResult>
     * @return
     */
    public static <T, TResult> Task<TResult> fromFunc(Func1<T, TResult> func) {
        return new Func1Task<T, TResult>(func);
    }

    /**
     * Create a task from a callback based async call. {@link Context#resume} must be invoked in the
     * callback to resume following tasks.
     * @param action
     * @param <TResult>
     * @return
     */
    public static <TResult> Task<TResult> fromCallback(Action1<Context<TResult>> action) {
        return new CallbackTask<TResult>(action);
    }

    /**
     * Create a task which will complete when all specified tasks complete.
     * @param tasks
     * @return
     */
    public static WhenAllTask continueWhenAll(ITask<?>... tasks) {
        return new WhenAllTask(tasks);
    }

    /**
     * Create a task which will complete when any specified task complete.
     * @param tasks
     * @return
     */
    public static WhenAnyTask continueWhenAny(ITask<?>... tasks) {
        return new WhenAnyTask(tasks);
    }

    /**
     * Flatten the result of the specified task.
     * @param task
     * @param <TResult>
     * @param <TTask>
     * @return
     */
    public static <TResult, TTask extends ITask<TResult>> Task<TResult> flatten(ITask<TTask> task) {
        return new FlattenTask<TResult, TTask>(task);
    }
}
