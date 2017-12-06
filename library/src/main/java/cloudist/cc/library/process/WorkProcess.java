package cloudist.cc.library.process;

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
    public Future<?> submit(Runnable runnable) {
        if (fixedThreadPool == null) {
            runnable.run();
            return null;
        } else {
            return fixedThreadPool.submit(runnable);
        }
    }
}
