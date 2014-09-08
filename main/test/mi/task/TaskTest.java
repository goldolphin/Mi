package mi.task;

import org.junit.Test;

import java.util.concurrent.Executors;

public class TaskTest {
    @Test
    public void testTask() throws Exception {
        Waiter<Integer> waiter = Task.fromFunc(new Func0<Integer>() {
            // Create task from function.
            @Override
            public Integer apply() {
                return 1;
            }
        }).continueWith(new Func1<Integer, Integer>() {
            @Override
            public Integer apply(Integer value) {
                return value + 1;
            }
        }).continueWithAndFlatten(new Func1<Integer, Task<Integer>>() {
            @Override
            public Task<Integer> apply(final Integer value) {
                // Fan out
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

                // Collect
                return Task.continueWhenAll(task2, task3).continueWith(new Func1<WhenAllTask, Integer>() {
                    @Override
                    public Integer apply(WhenAllTask value) {
                        return task2.getResult() + task3.getResult();
                    }
                });
            }
        }).continueWith(new Func1<Integer, Integer>() {
            @Override
            public Integer apply(Integer value) {
                return value * 2;
            }
        }).continueWithWaiter();

        waiter.execute(new ExecutorScheduler(Executors.newSingleThreadExecutor()));
        System.out.println(waiter.getResult());

        waiter.execute(new SynchronizedScheduler());
        System.out.println(waiter.getResult());
    }

    public void addCallback(int a, int b, Action1<Integer> func) {
        func.apply(a + b);
    }

    public ITask<Integer> addAsync(final int a, final int b) {
        return new Task<Integer>() {
            @Override
            public void execute(IContinuation cont, IScheduler scheduler) {
                scheduler.schedule(this, cont);
            }

            @Override
            public void onExecute(final IContinuation cont, final IScheduler scheduler) {
                addCallback(a, b, new Action1<Integer>() {
                    @Override
                    public void apply(Integer value) {
                        setResult(value);
                        cont.apply(scheduler);
                    }
                });
            }

            @Override
            protected Integer evaluate() {
                throw new UnsupportedOperationException();
            }
        };
    }
}