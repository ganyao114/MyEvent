package net.swiftos.eventposter.Invoker.pool.strategy;


import net.swiftos.eventposter.Invoker.pool.IThreadPool;
import net.swiftos.eventposter.Invoker.pool.configs.ThreadPoolConfigs;
import net.swiftos.eventposter.Invoker.pool.factory.WorkThreadFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

/**
 * Created by gy on 2016/2/22.
 */
public class CachedThreadPool implements IThreadPool {
    private ExecutorService executor;
    @Override
    public Future submit(Runnable r) {
        return executor.submit(r);
    }

    @Override
    public Future submit(Callable r) {
        return executor.submit(r);
    }

    @Override
    public <T> Future submit(Runnable task, T result) {
        return executor.submit(task,result);
    }

    @Override
    public void poolstart() {
        ThreadFactory factory = new WorkThreadFactory("thread-pool",
                android.os.Process.THREAD_PRIORITY_BACKGROUND);
        executor = Executors.newCachedThreadPool(factory);
    }

    @Override
    public void poolstop() {
        executor.shutdown();
    }

    @Override
    public void setconfigs(ThreadPoolConfigs configs) {

    }
}
