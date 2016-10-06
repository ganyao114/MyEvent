package net.swiftos.eventposter.Interface;

import net.swiftos.eventposter.Entity.EventAnnoInfo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by gy939 on 2016/10/3.
 */
public interface IHandler<T extends IEventEntity> {
    public void init(Object... objects);
    public void destory(Object... objects);
    public T parse(EventAnnoInfo annoInfo);
    public void load(T eventEntity);
    public void unload(T eventEntity);
    public void inject(Object object);
    public void remove(Object object);
}
