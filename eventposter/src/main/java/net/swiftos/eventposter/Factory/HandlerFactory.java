package net.swiftos.eventposter.Factory;

import net.swiftos.eventposter.Interface.IHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by gy939 on 2016/9/12.
 */
public class HandlerFactory {

    private static Map<Class<? extends IHandler>,IHandler> map =
            new ConcurrentHashMap<>();

    public static IHandler getHandler(Class<? extends IHandler> type){
        IHandler handler = map.get(type);
        if (handler == null) {
            synchronized (type) {
                if (handler == null) {
                    try {
                        handler = type.newInstance();
                        map.put(type, handler);
                    } catch (Exception e) {
                        return null;
                    }
                }
            }
        }
        return handler;
    }

}
