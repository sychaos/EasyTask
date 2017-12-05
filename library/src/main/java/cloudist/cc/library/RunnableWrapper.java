package cloudist.cc.library;

/**
 * A Runnable Wrapper to delegate {@link Runnable#run()}
 */
final class RunnableWrapper implements Runnable {

    private Callback callback;
    private Runnable proxy;

    RunnableWrapper(Callback callback, Runnable proxy) {
        this.callback = callback;
        this.proxy = proxy;
    }

    @Override
    public void run() {
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
        // 执行子线程方法
        if (proxy != null) {
            proxy.run();
        }
        if (callback != null) {
            callback.onFinish();
        }
    }
}
