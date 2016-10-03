package net.swiftos.eventposter.Impls.ActivityLife.Entity;

import android.app.Activity;

import net.swiftos.eventposter.Interface.IEventEntity;

import java.lang.reflect.Method;

/**
 * Created by gy939 on 2016/9/28.
 */
public class LifeInvokerEntity implements IEventEntity{

    private Method method;
    private Class invokerType;
    private ActivityLifeType type;
    private Class<? extends Activity> tarType;


    public Class<? extends Activity> getTarType() {
        return tarType;
    }

    public void setTarType(Class<? extends Activity> tarType) {
        this.tarType = tarType;
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


    public Object invoke(Object invoker,Object... pars){
        Object ret = null;
        try {
            ret = method.invoke(invoker,pars);
        } catch (Exception e) {

        }
        return ret;
    }

}
