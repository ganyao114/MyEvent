package net.swiftos.eventposter.Entity;

import net.swiftos.eventposter.Interface.IHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by gy939 on 2016/10/3.
 */
public class EventAnnoInfo {

    private Class clazz;
    private Method method;
    private Class<? extends IHandler> handler;
    private Annotation annotation;

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<? extends IHandler> getHandler() {
        return handler;
    }

    public void setHandler(Class<? extends IHandler> handler) {
        this.handler = handler;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }
}
