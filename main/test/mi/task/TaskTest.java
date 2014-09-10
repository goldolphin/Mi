package mi.task;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.Executors;

public class TaskTest {
    @Test
    public void testTask() throws Exception {
        // Executed on main thread.
        System.out.println(testAsync(1, new SynchronizedScheduler()).getResult());

        // Executed on a thread pool.
        System.out.println(testAsync(2, new ExecutorScheduler(Executors.newSingleThreadExecutor())).getResult());
    }

    /**
     * Build an async method running on a thread pool.
     * @param a
     * @param b
     * @param func
     */
    public static void addCallback(final int a, final int b, final Action1<Integer> func) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                func.apply(a + b);
            }
        });
    }

    /**
     * Wrap a callback based async method.
     * @param a
     * @param b
     * @return
     */
    public static ITask<Integer> addAsync(final int a, final int b) {
        return Task.fromCallback(new Action1<Context<Integer>>() {
            @Override
            public void apply(final Context<Integer> context) {
                addCallback(a, b, new Action1<Integer>() {
                    @Override
                    public void apply(Integer value) {
                        context.resume(value);
                    }
                });
            }
        });
    }

    /**
     * We combine several async actions to build a new one.
     * Don't Repeat Yourself when such action sequence must be reused.
     * @return A waiter, you can consider it as a traditional java future. Don't use it in a pure async program.
     */
    public Waiter<Integer> testAsync(int value, IScheduler scheduler) throws InterruptedException {
        // We use a Waiter to force main thread to wait the async task.
        // The Waiter is a friendly utility for testing.
        Waiter<Integer> waiter = testAsyncTask.continueWithWaiter();
        waiter.execute(value, scheduler);
        return waiter;
    }

    // The task is stateless, so we can build the task once and always reuse it.
    private static final Task<Integer> testAsyncTask =  Task.fromFunc(new Func1<Integer, Integer>() {
        // Create a task from a normal function.
        @Override
        public Integer apply(Integer value) {
            return value;
        }
    }).continueWith(new Func1<Integer, ITask<Integer>>() {
        // Serialize tasks.
        @Override
        public ITask<Integer> apply(Integer value) {
            // A multi-branch dispatch.
            if (value == 1) {
                // Run an async function to produce a nested task: ITask<ITask<Integer>>.
                return addAsync(value, 1);
            }
            return addAsync(value, 2);
        }
    }).flattenAndContinueWith(new Func1<Integer, Task<Integer>>() {
        // We must flatten the nested task(schedule the nested task) before we use the Integer.
        @Override
        public Task<Integer> apply(final Integer value) {
            // Fan out 2 tasks.
            final Task<Integer> task2 = Task.fromFunc(new Func0<Integer>() {
                @Override
                public Integer apply() {
                    return value * 2;
                }
            });

            final Task<Integer> task3 = Task.fromFunc(new Func0<Integer>() {
                @Override
                public Integer apply() {
                    return value * 3;
                }
            });

            // Collect results of the 2 task2
            return Task.continueWhenAll(task2, task3).continueWith(new Func1<Object[], Integer>() {
                @Override
                public Integer apply(Object[] value) {
                    return (Integer) value[0] + (Integer) value[1];
                }
            });
        }
    }).flattenAndContinueWith(new Func1<Integer, Integer>() {
        @Override
        public Integer apply(Integer value) {
            return value * 2;
        }
    });
}