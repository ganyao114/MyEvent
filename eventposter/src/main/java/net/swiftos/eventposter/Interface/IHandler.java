package net.swiftos.eventposter.Interface;

import net.swiftos.eventposter.Entity.EventAnnoInfo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by gy939 on 2016/10/3.
 */
public interface IHandler {
    public void inject(Annotation annotation, Method method, Object object);
    public void init(Object... objects);
    public void preLoad(EventAnnoInfo annoInfo);
}
