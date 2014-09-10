package mi.task;

/**
 * Antecedent tasks.
 * @author caofuxiang
 *         2014-09-10 20:43
 */
public class Antecedent {
    public final Antecedent previous;
    public final ITask<?> task;

    public Antecedent(Antecedent previous, ITask<?> task) {
        this.previous = previous;
        this.task = task;
    }
}
