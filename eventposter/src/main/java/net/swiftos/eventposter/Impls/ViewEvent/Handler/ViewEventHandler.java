package net.swiftos.eventposter.Impls.ViewEvent.Handler;

import android.util.SparseArray;
import android.view.View;

import net.swiftos.eventposter.Entity.EventAnnoInfo;
import net.swiftos.eventposter.Impls.ViewEvent.Annotation.ViewEventBase;
import net.swiftos.eventposter.Impls.ViewEvent.Entity.ViewEventEntity;
import net.swiftos.eventposter.Interface.IHandler;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by gy939 on 2016/10/10.
 */
public class ViewEventHandler implements IHandler<ViewEventEntity> {

    private Map<String,Map<Integer,WeakReference<View>>> viewMap = new ConcurrentHashMap<>();

    @Override
    public void init(Object... objects) {

    }

    @Override
    public void destory(Object... objects) {

    }

    @Override
    public ViewEventEntity parse(EventAnnoInfo annoInfo) {
        Annotation annotation = annoInfo.getAnnotation();
        ViewEventBase viewEventBase = annotation.annotationType().getAnnotation(ViewEventBase.class);
        if (viewEventBase == null)
            return null;    //throw exception better
        int[] ids = getIdFromAnno(annotation);
        String context = getContextFromAnno(annotation);
        if (ids == null||context == null)
            return null;    //throw exception better
        Method registMethod = null;
        Method interMethod = null;
        try {
            registMethod = viewEventBase.viewType().getDeclaredMethod(viewEventBase.listenerSetter());
            interMethod = viewEventBase.listenerType().getDeclaredMethod(viewEventBase.methodName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (registMethod == null||interMethod == null)
            return null;
        ViewEventEntity entity = new ViewEventEntity();
        entity.setRegistMethod(registMethod);
        entity.setIds(ids);
        entity.setContext(context);
        entity.setInter(viewEventBase.listenerType());
        entity.setCallBackMethod(interMethod);
        return entity;
    }

    @Override
    public void load(ViewEventEntity eventEntity, Object invoker) {

    }

    @Override
    public void unload(ViewEventEntity eventEntity, Object invoker) {

    }

    @Override
    public void inject(Object object) {

    }

    private int[] getIdFromAnno(Annotation annotation){
        int[] viewIds = null;
        try {
            Method aMethod = annotation.annotationType()
                    .getDeclaredMethod("viewIds");
            viewIds = (int[]) aMethod
                    .invoke(annotation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewIds;
    }

    private String getContextFromAnno(Annotation annotation){
        String context = null;
        try {
            Method aMethod = annotation.annotationType()
                    .getDeclaredMethod("context");
            context = (String) aMethod
                    .invoke(annotation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return context;
    }

    private void doRegistView(){

    }

    @Override
    public void remove(Object object) {

    }

    public void addView(String context,View view){

    }

    public void removeView(String context,View view){

    }

    public void removeViews(String context){

    }



}
