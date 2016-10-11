package net.swiftos.eventposter.Impls.ViewEvent.Entity;

import net.swiftos.eventposter.Exception.EventInvokeException;
import net.swiftos.eventposter.Interface.IEventEntity;

import java.lang.reflect.Method;

/**
 * Created by gy939 on 2016/10/10.
 */
public class ViewEventEntity implements IEventEntity{

    private String context;
    private int[] ids;
    private Method registMethod;
    private Method callBackMethod;
    private Class inter;


    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int[] getIds() {
        return ids;
    }

    public void setIds(int[] ids) {
        this.ids = ids;
    }

    public Method getRegistMethod() {
        return registMethod;
    }

    public void setRegistMethod(Method registMethod) {
        this.registMethod = registMethod;
    }

    public Method getCallBackMethod() {
        return callBackMethod;
    }

    public void setCallBackMethod(Method callBackMethod) {
        this.callBackMethod = callBackMethod;
    }

    public Class getInter() {
        return inter;
    }

    public void setInter(Class inter) {
        this.inter = inter;
    }

    @Override
    public Object invoke(Object invoker, Object... pars) throws EventInvokeException {
        return null;
    }
}
