package net.swiftos.eventposter.Impls.BroadCastReceiver.Entity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import net.swiftos.eventposter.Exception.EventInvokeException;
import net.swiftos.eventposter.Interface.IEventEntity;

import java.lang.reflect.Method;

/**
 * Created by gy939 on 2016/10/3.
 */
public class BroadCastEntity  implements IEventEntity{

    private Method method;
    private IntentFilter filter;

    public IntentFilter getFilter() {
        return filter;
    }

    public void setFilter(IntentFilter filter) {
        this.filter = filter;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    @Override
    public Object invoke(Object invoker, Object... pars) throws EventInvokeException {
        return null;
    }

}
