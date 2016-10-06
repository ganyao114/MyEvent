package net.swiftos.eventposter.Core;

import net.swiftos.eventposter.Cache.EventCache;
import net.swiftos.eventposter.Cache.ReflectionCache;
import net.swiftos.eventposter.Entity.EventAnnoInfo;
import net.swiftos.eventposter.Factory.HandlerFactory;
import net.swiftos.eventposter.Interface.IEventEntity;
import net.swiftos.eventposter.Interface.IHandler;
import net.swiftos.eventposter.Reflect.Parse.ClassParser;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by gy939 on 2016/10/3.
 */
public class Injecter {

    private static Map<Class,Vector> instMap = new ConcurrentHashMap<>();

    public static void inject(Object object){
        Class clazz = object.getClass();
        if (instMap.get(clazz) == null) {
            synchronized (clazz) {
                if (instMap.get(clazz) == null) {
                    instMap.put(clazz, new Vector());
                }
            }
        }
        load(object,clazz);
        instMap.get(clazz).add(object);
    }


    public static void remove(Object object){
        Class clazz = object.getClass();
        synchronized (clazz) {
            Vector vector = instMap.get(object.getClass());
            if (vector == null)
                return;
            vector.remove(object);
            dispatchRemove(object);
        }
    }

    private static void dispatchRemove(Object object) {
        Method[] methods = ReflectionCache.getMethods(object.getClass());
        if (methods == null)
            return;
        Map<Class<? extends IHandler>,IHandler> handlerMap = new HashMap<>();
        for (Method method:methods){
            EventAnnoInfo[] infos = ReflectionCache.getAnnoInfo(method);
            if (infos == null) continue;
            for (EventAnnoInfo eventAnnoInfo:infos){
                IHandler handler = HandlerFactory.getHandler(eventAnnoInfo.getHandler());
                IEventEntity eventEntity = EventCache.getEventEntity(eventAnnoInfo.getAnnotation());
                if (handler == null) continue;
                handler.unload(eventEntity,object);
                if (!handlerMap.containsKey(eventAnnoInfo.getHandler()))
                    handlerMap.put(eventAnnoInfo.getHandler(),handler);
            }
        }
        for (Map.Entry<Class<? extends IHandler>,IHandler> entry:handlerMap.entrySet()){
            entry.getValue().remove(object);
        }
    }

    public static Vector getInsts(Class clazz){
        return instMap.get(clazz);
    }

    public static void load(Object object,Class clazz){
        synchronized (clazz) {
            Method[] methods = ClassParser.getMethods(clazz);
            if (methods == null)
                return;
            Map<Class<? extends IHandler>,IHandler> handlerMap = new HashMap<>();
            for (Method method : methods) {
                EventAnnoInfo[] eventAnnoInfos = ReflectionCache.getAnnoInfo(method);
                if (eventAnnoInfos == null || eventAnnoInfos.length == 0) continue;
                for (EventAnnoInfo eventAnnoInfo : eventAnnoInfos) {
                    IEventEntity eventEntity = EventCache.getEventEntity(eventAnnoInfo.getAnnotation());
                    Class<? extends IHandler> handlerType = eventAnnoInfo.getHandler();
                    IHandler handler = HandlerFactory.getHandler(handlerType);
                    if (eventEntity == null){
                        if (handler == null) continue;
                        eventEntity = handler.parse(eventAnnoInfo);
                        EventCache.addEventEntity(eventAnnoInfo.getAnnotation(), eventEntity);
                    }
                    if (object == null) continue;
                    handler.load(eventEntity,object);
                    if (!handlerMap.containsKey(eventAnnoInfo.getHandler())) {
                        handlerMap.put(eventAnnoInfo.getHandler(), handler);
                    }
                }
            }
            if (object == null)
                return;
            for (Map.Entry<Class<? extends IHandler>,IHandler> entry:handlerMap.entrySet()){
                entry.getValue().inject(object);
            }
        }
    }

}
