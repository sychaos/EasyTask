package cloudist.cc.library.callback;

public interface Callback<T> {

    void error(Throwable t);

    void onStart();

    void onFinish(T t);
}
