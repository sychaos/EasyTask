package cloudist.cc.library.process;

import android.os.Handler;

import java.util.concurrent.Future;

/**
 * 用于回调到主线程的Scheduler
 * Created by jiang on 5/29/16.
 */
public class MainScheduler implements ETProcess {

    private Handler mHandler;

    public MainScheduler() {
    }

    public MainScheduler(Handler handler) {
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
    public Future<?> submit(Runnable runnable) {
        execute(runnable);
        return null;
    }
}
