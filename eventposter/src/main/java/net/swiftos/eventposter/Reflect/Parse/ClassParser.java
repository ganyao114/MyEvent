package net.swiftos.eventposter.Reflect.Parse;

import net.swiftos.eventposter.Annotation.EventBase;
import net.swiftos.eventposter.Cache.ReflectionCache;
import net.swiftos.eventposter.Entity.EventAnnoInfo;
import net.swiftos.eventposter.Interface.IHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gy939 on 2016/10/3.
 */
public class ClassParser {

    public static EventAnnoInfo[] getAnnoInfo(Method method){
        EventAnnoInfo[] res = null;
        res = ReflectionCache.getAnnoInfo(method);
        if (res == null){
            synchronized (method){
                if (res == null){
                    Annotation[] annotations = method.getAnnotations();
                    if (annotations == null||annotations.length == 0) return null;
                    List<EventAnnoInfo> eventAnnoInfos = new ArrayList<>();
                    for (Annotation annotation:annotations){
                        EventBase eventBase = annotation.getClass().getAnnotation(EventBase.class);
                        if (eventBase == null) continue;
                        Class<? extends IHandler> handler = eventBase.value();
                        EventAnnoInfo eventAnnoInfo = new EventAnnoInfo();
                        eventAnnoInfo.setAnnotation(annotation);
                        eventAnnoInfo.setHandler(handler);
                        eventAnnoInfo.setMethod(method);
                        eventAnnoInfo.setClazz(method.getClass());
                        eventAnnoInfos.add(eventAnnoInfo);
                    }
                    if (eventAnnoInfos.size() > 0) {
                        res = eventAnnoInfos.toArray(new EventAnnoInfo[eventAnnoInfos.size()]);
                        ReflectionCache.addAnnoInfo(method, res);
                    }
                }
            }
        }
        return res;
    }

    public static Method[] getMethods(Class clazz){
        synchronized (clazz) {
            Method[] res = null;
            res = ReflectionCache.getMethods(clazz);
            if (res == null) {
                res = methods(clazz);
            }
            return res;
        }
    }

    private static Method[] methods(Class clazz){
        Method[] methods = clazz.getDeclaredMethods();
        Method[] res = null;
        List<Method> list = new ArrayList<>();
        for (Method method:methods){
            Annotation[] annotations = method.getAnnotations();
            if (annotations == null||annotations.length == 0) continue;
            List<EventAnnoInfo> eventAnnoInfos = new ArrayList<>();
            for (Annotation annotation:annotations){
                EventBase eventBase = annotation.annotationType().getAnnotation(EventBase.class);
                if (eventBase == null) continue;
                Class<? extends IHandler> handler = eventBase.value();
                EventAnnoInfo eventAnnoInfo = new EventAnnoInfo();
                eventAnnoInfo.setAnnotation(annotation);
                eventAnnoInfo.setHandler(handler);
                eventAnnoInfo.setMethod(method);
                eventAnnoInfo.setClazz(clazz);
                eventAnnoInfos.add(eventAnnoInfo);
            }
            if (eventAnnoInfos.size() > 0) {
                list.add(method);
                ReflectionCache.addAnnoInfo(method, eventAnnoInfos.toArray(new EventAnnoInfo[eventAnnoInfos.size()]));
            }
        }
        if (list.size() > 0) {
            res = list.toArray(new Method[list.size()]);
            ReflectionCache.addMethods(clazz, methods);
        }
        return res;
    }



}
