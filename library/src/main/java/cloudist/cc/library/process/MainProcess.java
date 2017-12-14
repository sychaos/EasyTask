package cloudist.cc.library.process;

import android.os.Handler;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 用于回调到主线程的Scheduler
 * Created by jiang on 5/29/16.
 */
public class MainProcess implements ETProcess {

    private Handler mHandler;

    public MainProcess() {
    }

    public MainProcess(Handler handler) {
        mHandler = handler;
    }

    public Handler getHandler() {
        return mHandler;
    }

    public void setHandler(Handler handler) {
        mHandler = handler;
    }

    @Override
    public void execute(Runnable runnable) {
        if (mHandler == null) {
            runnable.run();
        } else {
            mHandler.post(runnable);
        }
    }

    @Override
    public <T> Future<T> submit(Callable<T> callable) {
        FutureTask<T> futureTask = new FutureTask(callable);
        Thread thread = new Thread(futureTask);
        thread.start();
        return null;
    }

}
