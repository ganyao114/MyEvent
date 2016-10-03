package net.swiftos.eventposter.Cache;

import net.swiftos.eventposter.Entity.EventAnnoInfo;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by gy939 on 2016/10/3.
 */
public class ReflectionCache {

    private static Map<Class,Method[]> methodsMap = new ConcurrentHashMap<>();
    private static Map<Method,EventAnnoInfo[]> anooMaps = new ConcurrentHashMap<>();

    public static Method[] getMethods(Class clazz){
        return methodsMap.get(clazz);
    }

    public static void addMethods(Class clazz,Method[] methods){
        methodsMap.put(clazz, methods);
    }

    public static EventAnnoInfo[] getAnnoInfo(Method method){
        return anooMaps.get(method);
    }

    public static void addAnnoInfo(Method method,EventAnnoInfo[] eventAnnoInfos){
        anooMaps.put(method,eventAnnoInfos);
    }

}
