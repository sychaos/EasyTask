package cloudist.cc.library.process;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程调度的配置类，这里你可以自己实现Scheduler,只需要继承 Scheduler即可,如果你不需要submit返回的Futrue对象的话，可以直接return null;
 * Created by jiang on 5/29/16.
 */
public class Schedules {
    private static int THREAD_SIZE = 5;

    /**
     * Observable用的线程调度器
     */
    private static ETProcess mWorkScheduler;

    /**
     * Subscriber用的线程调度器
     */
    private static ETProcess mMainScheduler;


    /**
     * 初始化线程池的数量
     *
     * @param thread_size
     */
    public static void init(int thread_size) {
        THREAD_SIZE = thread_size;
    }

    /**
     * 初始化配置Observable用的线程调度器的线程池
     *
     * @param executorService
     */
    public static void init(ExecutorService executorService) {
        if (executorService != null)
            mWorkScheduler = new WorkScheduler(executorService);
    }

    public static void setWorkScheduler(ETProcess mWorkScheduler) {
        Schedules.mWorkScheduler = mWorkScheduler;
    }

    public static void setMainScheduler(ETProcess mMainScheduler) {
        Schedules.mMainScheduler = mMainScheduler;
    }

    // 默认为size为5的线程池
    public static ETProcess background() {
        synchronized (Schedules.class) {
            if (mWorkScheduler == null) {
                mWorkScheduler = new WorkScheduler(Executors.newFixedThreadPool(THREAD_SIZE));
            }
        }
        return mWorkScheduler;
    }

    // new Handler(Looper.getMainLooper()) UI线程下的handle
    public static ETProcess mainThread() {
        synchronized (Schedules.class) {
            if (mMainScheduler == null) {
                // 整体的思路还是使用handler post来负责分发消息
                mMainScheduler = new MainScheduler(new Handler(Looper.getMainLooper()));
            }
        }
        return mMainScheduler;
    }

}
