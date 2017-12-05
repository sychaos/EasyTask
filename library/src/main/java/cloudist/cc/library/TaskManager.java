package cloudist.cc.library;

import java.util.concurrent.Future;

import cloudist.cc.library.process.ETProcess;

/**
 * 订阅者管理器
 * Created by jiang on 5/29/16.
 */
public class TaskManager<T> implements Task{

    /**
     * Scheduler用于对线程的管理
     */
    private ETProcess mCallbackProcess;
    /**
     * 是否订阅的状态
     */
    private boolean unSubscribed;
    /**
     * 如果是子线程执行的Observable,则用于取消作业,否则为null
     */
    private Future<?> mFuture;

    private Callback<T> mCallback;

    @SuppressWarnings("unused")
    public Callback<T> getObserver() {
        return mCallback;
    }

    @SuppressWarnings("unused")
    public TaskManager() {
    }

    public TaskManager(Callback<T> callback) {
        mCallback = callback;
    }

    public void setCallbackProcess(ETProcess mCallbackProcess) {
        this.mCallbackProcess = mCallbackProcess;
    }

    public void setFuture(Future<?> future) {
        this.mFuture = future;
    }

    @Override
    public void unSubscribe() {
        if (unSubscribed == false) {
            unSubscribed = true;
            cancel(false);
        }
    }

    @Override
    public boolean isUnSubscribed() {
        return unSubscribed;
    }

    private void cancel(boolean interruptNow) {
        if (mFuture != null && !mFuture.isCancelled() && !mFuture.isDone()) {
            mFuture.cancel(interruptNow);
        }

    }

    /**
     * 在任务执行之前会被调用
     */
    public void onStart() {
        mCallback.onStart();
    }


    /**
     * 在任务执行之后会被调用
     */
    public void onAfter() {
        mCallback.onFinish();
    }
}
