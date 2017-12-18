package cloudist.cc.library;

import java.util.concurrent.Future;

import cloudist.cc.library.process.ETProcess;

public class TaskManager<T> implements Task {

    private ETProcess mCallbackProcess;
    private boolean isCancel;
    private CallableWrapper mCallableWrapper;
    private Future mFuture;

    public TaskManager(CallableWrapper callableWrapper) {
        mCallableWrapper = callableWrapper;
    }

    public void setCallbackProcess(ETProcess mCallbackProcess) {
        this.mCallbackProcess = mCallbackProcess;
    }

    @Override
    public void cancel() {
        if (!isCancel) {
            isCancel = true;
        }
        mCallableWrapper.setCallback(null);
        if (mFuture != null) {
            mFuture.cancel(true);
        }
    }

    @Override
    public boolean isCancel() {
        return isCancel;
    }

    public Future getFuture() {
        return mFuture;
    }

    public void setFuture(Future mFuture) {
        this.mFuture = mFuture;
    }

    public CallableWrapper getCallableWrapper() {
        return mCallableWrapper;
    }

    public void setCallableWrapper(CallableWrapper mCallableWrapper) {
        this.mCallableWrapper = mCallableWrapper;
    }
}
