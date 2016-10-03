package net.swiftos.eventposter.Impls.BroadCastReceiver.Entity;

import net.swiftos.eventposter.Exception.EventInvokeException;
import net.swiftos.eventposter.Interface.IEventEntity;

/**
 * Created by gy939 on 2016/10/3.
 */
public class BroadCastEntity implements IEventEntity{
    @Override
    public Object invoke(Object invoker, Object... pars) throws EventInvokeException {
        return null;
    }
}
