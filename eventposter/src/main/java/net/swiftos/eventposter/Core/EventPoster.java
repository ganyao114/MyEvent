package net.swiftos.eventposter.Core;

import android.app.Application;

import net.swiftos.eventposter.Factory.HandlerFactory;
import net.swiftos.eventposter.Impls.ActivityLife.Handler.ActivityLifeHandler;
import net.swiftos.eventposter.Interface.IHandler;

/**
 * Created by gy939 on 2016/10/3.
 */
public class EventPoster {

    private static Application app;

    public static <T extends IHandler> T With(Class<T> handlerType){
        IHandler handler = HandlerFactory.getHandler(handlerType);
        if (handler == null) return null;
        return (T) handler;
    }

    public static void Regist(Object object){
        Injecter.inject(object);
    }

    public static void UnRegist(Object object){
        Injecter.remove(object);
    }

    public static void RegistDeep(Object object){
        Injecter.injectDeep(object);
    }

    public static void UnRegistDeep(Object object){
        Injecter.removeDeep(object);
    }

    public static void init(Application application){
        app = application;
        HandlerFactory.getHandler(ActivityLifeHandler.class).init(application);
    }

    public static void destory(Application application){
        app = null;
        HandlerFactory.getHandler(ActivityLifeHandler.class).destory(application);
    }

    public static Application getApp(){
        return app;
    }

    public static void PreLoad(final Class[] classes){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Class clazz:classes){
                    Injecter.load(null,clazz);
                }
            }
        }).start();
    }

    public static void PreLoadDeep(final Class[] classes){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Class clazz:classes){
                    Injecter.loadDeep(null,clazz);
                }
            }
        }).start();
    }

}
