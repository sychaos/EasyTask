package cloudist.cc.library.callback;

/**
 * 订阅者的基类，这里的{@getNotify()} 和 {@getError()} 只会执行其中一个方法
 * Created by jiang on 5/29/16.
 */
public interface Callback<T> {

    void error(Throwable t);

    void onStart();

    void onFinish();
}
