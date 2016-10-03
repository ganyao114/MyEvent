package net.swiftos.eventposter.Core;

import net.swiftos.eventposter.Cache.ReflectionCache;
import net.swiftos.eventposter.Entity.EventAnnoInfo;
import net.swiftos.eventposter.Factory.HandlerFactory;
import net.swiftos.eventposter.Interface.IHandler;
import net.swiftos.eventposter.Reflect.Parse.ClassParser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by gy939 on 2016/10/3.
 */
public class Injecter {

    private static Map<Class,Vector> instMap = new ConcurrentHashMap<>();

    public static void inject(Object object){
        load(object.getClass());

    }

    public static void remove(Object object){

    }

    public static void load(Class clazz){
        if (instMap.containsKey(clazz))
            return;
        Method[] methods = ClassParser.getMethods(clazz);
        if (methods == null)
            return;
        for (Method method:methods){
            EventAnnoInfo[] eventAnnoInfos = ReflectionCache.getAnnoInfo(method);
            if (eventAnnoInfos == null||eventAnnoInfos.length == 0) continue;
            for (EventAnnoInfo eventAnnoInfo:eventAnnoInfos){
                Class<? extends IHandler> handlerType = eventAnnoInfo.getHandler();
                IHandler handler = HandlerFactory.getHandler(handlerType);
                if (handler == null) continue;
                handler.load(eventAnnoInfo);
            }
        }
    }

}
