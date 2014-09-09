package mi.task;

/**
 * @author goldolphin
 *         2014-09-06 14:22
 */
public interface IContinuation {
    /**
     * Continuation representing that nothing need to do.
     */
    public static IContinuation END = new IContinuation() {
        @Override
        public void apply(Object state, ITask<?> previous, IScheduler scheduler) {
            System.out.println("IContinuation.END");
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
