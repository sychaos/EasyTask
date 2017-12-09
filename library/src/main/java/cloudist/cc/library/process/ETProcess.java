package cloudist.cc.library.process;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created by cloudist on 2017/12/4.
 */

public interface ETProcess {

    /**
     * 立刻执行
     *
     * @param runnable
     */
    public void execute(Runnable runnable);

    /**
     * 会返回Futrue对象
     *
     * @param callable
     * @return
     */
    public <T> Future<T> submit(Callable<T> callable);

}
