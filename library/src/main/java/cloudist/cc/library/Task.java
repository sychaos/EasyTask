package cloudist.cc.library;

/**
 * Created by cloudist on 2017/12/6.
 */

public interface Task {

    /**
     * 取消订阅
     * Now的意义在于是否调用{subscriber.cancel()} 中传入false
     * 即{mFuture.cancel() }
     */
    void unSubscribe();

    /**
     * 判断当前是否是订阅状态
     *
     * @return boolean
     */
    boolean isUnSubscribed();
}
