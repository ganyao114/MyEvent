package net.swiftos.eventposter.Impls.ActivityLife.Handler;

import net.swiftos.eventposter.Entity.EventAnnoInfo;
import net.swiftos.eventposter.Interface.IHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by gy939 on 2016/10/3.
 */
public class ActivityLifeHandler implements IHandler {
    @Override
    public void inject(Annotation annotation, Method method, Object object) {

    }

    @Override
    public void init(Object... objects) {

    }

    @Override
    public void load(EventAnnoInfo annoInfo) {

    }
}
