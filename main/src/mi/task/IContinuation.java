package mi.task;

/**
 * A Continuation.
 * @author goldolphin
 *         2014-09-06 14:22
 */
public interface IContinuation {
    /**
     * Continuation representing that nothing need to do.
     */
    public static IContinuation END_CONTINUATION = new IContinuation() {
        @Override
        public void apply(Object state, ITask<?> previous, IScheduler scheduler) {
            System.out.println("END_CONTINUATION");
        }
    };

    /**
     * Apply the continuation.
     * @param state
     * @param previous
     * @param scheduler
     */
    public void apply(Object state, ITask<?> previous, IScheduler scheduler);
}
