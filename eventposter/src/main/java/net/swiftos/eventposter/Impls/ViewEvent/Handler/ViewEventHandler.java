package net.swiftos.eventposter.Impls.ViewEvent.Handler;

import android.util.SparseArray;
import android.view.View;

import net.swiftos.eventposter.Entity.EventAnnoInfo;
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

    private Map<Integer,WeakReference<View>> viewMap = new ConcurrentHashMap<>();

    @Override
    public void init(Object... objects) {

    }

    @Override
    public void destory(Object... objects) {

    }

    @Override
    public ViewEventEntity parse(EventAnnoInfo annoInfo) {
        Annotation annotation = annoInfo.getAnnotation();
        int[] ids = getIdFromAnno(annotation);
        if (ids == null)
            return null;

        return null;
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
                    .getDeclaredMethod("value");
            viewIds = (int[]) aMethod
                    .invoke(annotation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewIds;
    }

    @Override
    public void remove(Object object) {

    }

    public void addView(int viewId,View view){

    }

    public void removeView(int viewId){

    }


}
