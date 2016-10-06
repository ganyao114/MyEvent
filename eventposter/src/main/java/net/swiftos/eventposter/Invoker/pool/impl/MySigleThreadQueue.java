package net.swiftos.eventposter.Invoker.pool.impl;

import net.swiftos.eventposter.Invoker.pool.control.ThreadPoolControl;
import net.swiftos.eventposter.Invoker.pool.strategy.SigleThreadPool;

/**
 * Created by gy on 2016/2/22.
 */
public class MySigleThreadQueue {

    private static ThreadPoolControl control;

    private static void initPool(){
        control = new ThreadPoolControl(new SigleThreadPool());
        control.poolstart();
    }

    public static void AddTask(Runnable runnable){
        synchronized (MySigleThreadQueue.class){
            if (control == null)
                initPool();
        }
        control.submit(runnable);
    }
}
