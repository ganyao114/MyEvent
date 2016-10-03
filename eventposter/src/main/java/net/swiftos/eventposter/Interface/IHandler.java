package net.swiftos.eventposter.Interface;

import net.swiftos.eventposter.Entity.EventAnnoInfo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by gy939 on 2016/10/3.
 */
public interface IHandler {
    public void init(Object... objects);
    public void destory(Object... objects);
    public IEventEntity parse(EventAnnoInfo annoInfo);
    public void load(IEventEntity annoInfo);
    public void unload(IEventEntity annoInfo);
    public void inject(Object object);
    public void remove(Object object);
}
