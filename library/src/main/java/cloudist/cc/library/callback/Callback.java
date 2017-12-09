package cloudist.cc.library.callback;

public interface Callback<T> {

    void onError(Throwable t);

    void onStart();

    void onFinish(T t);
}
