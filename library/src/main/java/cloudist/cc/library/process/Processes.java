package cloudist.cc.library.process;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Processes {
    private static int THREAD_SIZE = 5;

    /**
     * Observable用的线程调度器
     */
    private static ETProcess mWorkProcess;

    /**
     * Subscriber用的线程调度器
     */
    private static ETProcess mMainProcess;


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
            mWorkProcess = new WorkProcess(executorService);
    }

    public static void setWorkScheduler(ETProcess mWorkScheduler) {
        Processes.mWorkProcess = mWorkScheduler;
    }

    public static void setMainScheduler(ETProcess mMainScheduler) {
        Processes.mMainProcess = mMainScheduler;
    }

    // 默认为size为5的线程池 TODO 提供更多的选择
    public static ETProcess background() {
        synchronized (Processes.class) {
            if (mWorkProcess == null) {
                mWorkProcess = new WorkProcess(Executors.newFixedThreadPool(THREAD_SIZE));
            }
        }
        return mWorkProcess;
    }

    // new Handler(Looper.getMainLooper()) UI线程下的handle
    public static ETProcess mainThread() {
        synchronized (Processes.class) {
            if (mMainProcess == null) {
                // 整体的思路还是使用handler post来负责分发消息
                mMainProcess = new MainProcess(new Handler(Looper.getMainLooper()));
            }
        }
        return mMainProcess;
    }

}
