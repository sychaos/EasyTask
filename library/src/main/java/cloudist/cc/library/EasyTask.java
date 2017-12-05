package cloudist.cc.library;

import cloudist.cc.library.process.ETProcess;

/**
 * Created by cloudist on 2017/12/4.
 */

public class EasyTask<T> {

    private AndroidCallback androidCallback;

    private Callback<T> mCallback;
    private OnExecute<T> mTask;

    private TaskManager<T> mTaskManager;

    private ETProcess mCallbackProcess;
    private ETProcess mTaskProcess;

    private EasyTask(OnExecute<T> task) {
       this.mTask = task;
    }

    //JObservable 新增一个onSubscribe事件
    public static <T> EasyTask<T> create(OnExecute<T> task) {
        if (task == null) {
            throw new NullPointerException("task can not be null");
        }
        return new EasyTask<T>(task);
    }

    public EasyTask<T> runOn(ETProcess process) {
        mTaskProcess = process;
        return this;
    }

    public EasyTask<T> callbackOn(ETProcess process) {
        mCallbackProcess = process;
        return this;
    }

    public void run(Callback<T> callback) {
        if (mCallbackProcess == null) {
            throw new IllegalArgumentException("not appointed callbackProcess");
        }
        if (mTaskProcess == null) {
            throw new IllegalArgumentException("not appointed mTaskProcess");
        }
        androidCallback = new AndroidCallback(callback, mCallbackProcess);
        mTaskManager = new TaskManager<T>(callback);
        mTaskManager.setCallbackProcess(mCallbackProcess);
        // 如果工作线程为空，直接执行call方法
        // Schedules.background() 的submit
        // fixedThreadPool为空直接执行，不为空加入线程池 执行call方法
        mTaskProcess.execute(new RunnableWrapper(androidCallback, new Runnable() {
            @Override
            public void run() {
                mTask.execute(mTaskManager);
            }
        }));
    }

    // 其实是给Callback包了一层，利用主线程的Handler发送回调
    private static class AndroidCallback implements Callback {
        private Callback delegate;
        private ETProcess callbackProcess;

        AndroidCallback(Callback delegate, ETProcess callbackProcess) {
            this.delegate = delegate;
            this.callbackProcess = callbackProcess;
        }

        @Override
        public void error(final Throwable t) {
            callbackProcess.execute(new Runnable() {
                @Override
                public void run() {
                    if (delegate != null) {
                        delegate.error(t);
                    }
                }
            });
        }

        @Override
        public void onStart() {
            callbackProcess.execute(new Runnable() {
                @Override
                public void run() {
                    if (delegate != null) {
                        delegate.onStart();
                    }
                }
            });
        }

        @Override
        public void onFinish() {
            callbackProcess.execute(new Runnable() {
                @Override
                public void run() {
                    if (delegate != null) {
                        delegate.onFinish();
                    }
                }
            });
        }
    }

    public interface OnExecute<T> {
        void execute(TaskManager<T> mTaskManager);
    }

}
