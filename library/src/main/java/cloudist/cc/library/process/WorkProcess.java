package cloudist.cc.library.process;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created by jiang on 5/29/16.
 */
public class WorkProcess implements ETProcess {
    public ExecutorService fixedThreadPool;

    public WorkProcess(ExecutorService fixedThreadPool) {
        this.fixedThreadPool = fixedThreadPool;
    }


    @Override
    public void execute(Runnable runnable) {
        if (fixedThreadPool == null) {
            runnable.run();
        } else {
            fixedThreadPool.execute(runnable);
        }
    }

    @Override
    public <T> Future<T> submit(Callable<T> callable) {
        if (fixedThreadPool == null) {
            throw new IllegalArgumentException("no ThreadPool");
        } else {
            return fixedThreadPool.submit(callable);
        }
    }

}
