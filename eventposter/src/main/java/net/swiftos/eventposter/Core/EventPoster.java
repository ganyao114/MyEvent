package net.swiftos.eventposter.Core;

import net.swiftos.eventposter.Factory.HandlerFactory;
import net.swiftos.eventposter.Interface.IHandler;

/**
 * Created by gy939 on 2016/10/3.
 */
public class EventPoster {

    public static <T extends IHandler> T With(Class<T> handlerType){
        IHandler handler = HandlerFactory.getHandler(handlerType);
        if (handler == null) return null;
        return (T) handler;
    }

}
