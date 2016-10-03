package net.swiftos.eventposter.Impls.ActivityLife.Entity;

import android.app.Activity;

import net.swiftos.eventposter.Exception.EventInvokeException;
import net.swiftos.eventposter.Interface.IEventEntity;

import java.lang.reflect.Method;

/**
 * Created by gy939 on 2016/9/28.
 */
public class LifeInvokerEntity implements IEventEntity{

    private Method method;
    private Class invokerType;
    private ActivityLifeType type;
    private Class<? extends Activity>[] tarTypes;

    public Class<? extends Activity>[] getTarTypes() {
        return tarTypes;
    }

    public void setTarTypes(Class<? extends Activity>[] tarTypes) {
        this.tarTypes = tarTypes;
    }

    public Class getInvokerType() {
        return invokerType;
    }

    public void setInvokerType(Class invokerType) {
        this.invokerType = invokerType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public ActivityLifeType getType() {
        return type;
    }

    public void setType(ActivityLifeType type) {
        this.type = type;
    }

    @Override
    public Object invoke(Object invoker,Object... pars)throws EventInvokeException {
        Object ret = null;
        try {
            ret = method.invoke(invoker,pars);
        } catch (Exception e) {
            throw new EventInvokeException(e.getMessage());
        }
        return ret;
    }

}
