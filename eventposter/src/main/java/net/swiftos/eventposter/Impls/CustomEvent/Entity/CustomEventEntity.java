package net.swiftos.eventposter.Impls.CustomEvent.Entity;

import net.swiftos.eventposter.Exception.EventInvokeException;
import net.swiftos.eventposter.Interface.IEventEntity;
import net.swiftos.eventposter.Invoker.Invoker;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by gy939 on 2016/10/6.
 */
public class CustomEventEntity implements IEventEntity{

    private Method method;
    private int delay;
    private RunContextType contextType;
    private Annotation annotation;
    private String name;
    private Class parType;
    private Class invokeType;

    public Class getInvokeType() {
        return invokeType;
    }

    public void setInvokeType(Class invokeType) {
        this.invokeType = invokeType;
    }

    public Class getParType() {
        return parType;
    }

    public void setParType(Class parType) {
        this.parType = parType;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public RunContextType getContextType() {
        return contextType;
    }

    public void setContextType(RunContextType contextType) {
        this.contextType = contextType;
    }

    @Override
    public Object invoke(Object invoker, Object... pars) throws EventInvokeException {
        Object ret = null;
        ret = Invoker.getInstance().invoke(method,invoker,contextType,delay,pars);
        return ret;
    }
}
