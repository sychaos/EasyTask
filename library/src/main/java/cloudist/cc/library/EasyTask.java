package cloudist.cc.library;

import java.util.concurrent.Callable;

import cloudist.cc.library.callback.Callback;
import cloudist.cc.library.callback.DefaultCallback;
import cloudist.cc.library.process.ETProcess;
import cloudist.cc.library.process.Processes;

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

    public EasyTask<T> callback(Callback<T> callback) {
        mCallback = callback;
        return this;
    }

    // TODO 可以考虑返回一个EasyTask实例这样就可以。。。流式进行了
    public Task run() {
        if (mCallbackProcess == null) {
            mCallbackProcess = Processes.mainThread();
        }
        if (mTaskProcess == null) {
            mCallbackProcess = Processes.background();
        }
        if (mCallback == null) {
            mCallback = new DefaultCallback<>();
        }
        androidCallback = new AndroidCallback<T>(mCallback, mCallbackProcess);
        // 是否可以不传mTaskManager
        mTaskManager = new TaskManager<T>(new CallableWrapper<>(androidCallback, new Callable<T>() {
            @Override
            public T call() {
                return mTask.call(mTaskManager);
            }
        }));
        mTaskManager.setCallbackProcess(mCallbackProcess);
        // 如果工作线程为空，直接执行call方法
        // Processes.background() 的submit
        // fixedThreadPool为空直接执行，不为空加入线程池 执行call方法
        mTaskManager.setFuture(mTaskProcess.submit(mTaskManager.getCallableWrapper()));
        return mTaskManager;
    }

    // 其实是给Callback包了一层，利用主线程的Handler发送回调
    private static class AndroidCallback<T> implements Callback {
        private Callback delegate;
        private ETProcess callbackProcess;

        AndroidCallback(Callback delegate, ETProcess callbackProcess) {
            this.delegate = delegate;
            this.callbackProcess = callbackProcess;
        }

        @Override
        public void onError(final Throwable t) {
            callbackProcess.execute(new Runnable() {
                @Override
                public void run() {
                    if (delegate != null) {
                        delegate.onError(t);
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
        public void onFinish(final Object o) {
            callbackProcess.execute(new Runnable() {
                @Override
                public void run() {
                    if (delegate != null) {
                        delegate.onFinish(o);
                    }
                }
            });
        }

    }

    public interface OnExecute<T> {
        T call(TaskManager<T> mTaskManager);
    }

}
