package net.swiftos.eventposter.Interface;

import net.swiftos.eventposter.Exception.EventInvokeException;

/**
 * Created by gy939 on 2016/10/3.
 */
public interface IEventEntity {
    public Object invoke(Object invoker,Object... pars)throws EventInvokeException;
}
