package mi.task;

/**
 * @author goldolphin
 *         2014-09-06 14:22
 */
public interface IContinuation {
    public static IContinuation END = new IContinuation() {
        @Override
        public void apply(IScheduler scheduler) {
            System.out.println("IContinuation.END");
        }
    };

    public void apply(IScheduler scheduler);
}
