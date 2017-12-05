/*
 * Copyright (C) 2017 Haoge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
