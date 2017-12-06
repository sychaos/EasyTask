package cloudist.cc.library;

import cloudist.cc.library.process.ETProcess;

public class TaskManager<T> implements Task {

    private ETProcess mCallbackProcess;
    private boolean isCancel;
    private RunnableWrapper mRunnableWrapper;

    public TaskManager(RunnableWrapper runnableWrapper) {
        mRunnableWrapper = runnableWrapper;
    }

    public void setCallbackProcess(ETProcess mCallbackProcess) {
        this.mCallbackProcess = mCallbackProcess;
    }

    @Override
    public void cancel() {
        if (isCancel == false) {
            isCancel = true;
        }
        mRunnableWrapper.setCallback(null);
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
}
