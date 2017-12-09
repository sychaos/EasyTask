package cloudist.cc.library;

import cloudist.cc.library.process.ETProcess;

public class TaskManager<T> implements Task {

    private ETProcess mCallbackProcess;
    private boolean isCancel;
    private RunnableWrapper mRunnableWrapper;
    private CallableWrapper mCallableWrapper;

    public TaskManager(RunnableWrapper runnableWrapper) {
        mRunnableWrapper = runnableWrapper;
    }

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
        mRunnableWrapper.setCallback(null);
        mCallableWrapper.setCallback(null);
    }

    @Override
    public boolean isCancel() {
        return isCancel;
    }

    public RunnableWrapper getRunnableWrapper() {
        return mRunnableWrapper;
    }

    public void setRunnableWrapper(RunnableWrapper mRunnableWrapper) {
        this.mRunnableWrapper = mRunnableWrapper;
    }

    public CallableWrapper getCallableWrapper() {
        return mCallableWrapper;
    }

    public void setCallableWrapper(CallableWrapper mCallableWrapper) {
        this.mCallableWrapper = mCallableWrapper;
    }
}
