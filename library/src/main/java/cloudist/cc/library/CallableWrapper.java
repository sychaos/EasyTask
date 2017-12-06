package cloudist.cc.library;

import java.util.concurrent.Callable;

import cloudist.cc.library.callback.Callback;

/**
 * A Callable Wrapper to delegate {@link Callable#call()}
 */
final class CallableWrapper<T> implements Callable<T> {
    private Callback callback;
    private Callable<T> proxy;

    CallableWrapper(Callback callback, Callable<T> proxy) {
        this.callback = callback;
        this.proxy = proxy;
    }

    // 方法 submit(Callable) 和方法 submit(Runnable) 比较类似，但是区别则在于它们接收不同的参数类型。
    // Callable 的实例与 Runnable 的实例很类似，但是 Callable 的 call() 方法可以返回壹個结果。方法 Runnable.run() 则不能返回结果。
    @Override
    public T call() throws Exception {
        // 当子线程发生错误的时候，利用该方法catch
        Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                if (callback != null) {
                    callback.error(e);
                }
            }
        });
        if (callback != null) {
            callback.onStart();
        }

        // avoid NullPointException
        T t = proxy == null ? null : proxy.call();
        if (callback != null) {
            callback.onFinish();
        }
        return t;
    }
}
