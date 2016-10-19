package net.swiftos.eventposter.Impls.ViewEvent.Handler;

import android.view.View;

import net.swiftos.eventposter.Cache.EventCache;
import net.swiftos.eventposter.Entity.EventAnnoInfo;
import net.swiftos.eventposter.Impls.ViewEvent.Annotation.ViewEventBase;
import net.swiftos.eventposter.Impls.ViewEvent.Entity.DynamicHandler;
import net.swiftos.eventposter.Impls.ViewEvent.Entity.ViewEventEntity;
import net.swiftos.eventposter.Impls.ViewEvent.Interface.OnViewAttachListener;
import net.swiftos.eventposter.Interface.IEventEntity;
import net.swiftos.eventposter.Interface.IHandler;
import net.swiftos.eventposter.Utils.LOG;
import net.swiftos.eventposter.Utils.SyncWeakList;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by gy939 on 2016/10/10.
 */
public class ViewEventHandler implements IHandler<ViewEventEntity>,OnViewAttachListener{

    private Map<String,Map<Integer,SyncWeakList<View>>> viewMap = new ConcurrentHashMap<>();
    private Map<String,Map<Integer,Vector<Annotation>>> viewEventMap = new ConcurrentHashMap<>();
    private Map<Annotation,DynamicHandler> viewProxyMap = new ConcurrentHashMap<>();

    private Map<String,SyncWeakList<OnViewAttachListener>> attachlsMap = new ConcurrentHashMap<>();

    @Override
    public void init(Object... objects) {
        LOG.e("ViewEvent --- init");
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
        try {
            registMethod = viewEventBase.viewType().getDeclaredMethod(viewEventBase.listenerSetter(),viewEventBase.listenerType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (registMethod == null)
            return null;
        ViewEventEntity entity = new ViewEventEntity();
        entity.setRegistMethod(registMethod);
        entity.setIds(ids);
        entity.setContext(context);
        entity.setInter(viewEventBase.listenerType());
        entity.setCallMethod(annoInfo.getMethod());
        entity.setAnnotation(annotation);
        entity.setCallBackMethodName(viewEventBase.methodName());
        entity.setClazz(annoInfo.getClazz());
        return entity;
    }

    @Override
    public void load(ViewEventEntity eventEntity, Object invoker) {
        String context = eventEntity.getContext();
        Map<Integer,Vector<Annotation>> eventMap = viewEventMap.get(eventEntity.getAnnotation());
        if (eventMap == null){
            eventMap = new ConcurrentHashMap<>();
            viewEventMap.put(context,eventMap);
        }
        int[] ids = eventEntity.getIds();
        for (int id:ids){
            Vector<Annotation> events = eventMap.get(id);
            if (events == null){
                events = new Vector<>();
                eventMap.put(id,events);
            }
            if (!events.contains(eventEntity.getAnnotation())){
                events.add(eventEntity.getAnnotation());
            }
            doRegistView(context,id);
        }


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

    private void doRegistView(String context,View view){
        Map<Integer,Vector<Annotation>> viewEvents = viewEventMap.get(context);
        if (viewEvents == null){
            LOG.e("未注册Context");
            return;
        }
        int id = view.getId();
        if (id == View.NO_ID){
            LOG.e("View没有ID");
            return;
        }
        Vector<Annotation> annos = viewEvents.get(id);
        if (annos == null){
            LOG.e("没有此ID");
            return;
        }
        for (Annotation annotation:annos){
            IEventEntity et = EventCache.getEventEntity(annotation);
            if (et == null) continue;
            ViewEventEntity entity = (ViewEventEntity) et;
            DynamicHandler handler = viewProxyMap.get(annotation);
            if (handler == null){
                handler = generateDymHandler(entity);
                viewProxyMap.put(annotation,handler);
            }else {
                handler.addHandler(entity.getClazz());
            }
            Object listener = Proxy.newProxyInstance(
                    entity.getInter().getClassLoader(),
                    new Class<?>[]{entity.getInter()}, handler);
            try {
                entity.getRegistMethod().invoke(view,listener);
            } catch (Exception e) {
                LOG.e("注册监听失败");
            }
        }
    }

    private void doRegistView(String context,int id){
        Map<Integer,SyncWeakList<View>> views = viewMap.get(context);
        if (views == null){
            LOG.e("未注册Context");
            return;
        }
        SyncWeakList<View> viewList = views.get(id);
        if (viewList == null){
            LOG.e("无id");
            return;
        }
        for (View view:viewList){
            doRegistView(context,view);
        }
    }

    private DynamicHandler generateDymHandler(ViewEventEntity entity){
        DynamicHandler handler = new DynamicHandler();
        handler.addMethod(entity.getCallBackMethodName(),entity.getCallMethod());
        handler.addHandler(entity.getClazz());
        return handler;
    }

    @Override
    public void remove(Object object) {

    }

    public void addView(String context,View view){
        Map<Integer,SyncWeakList<View>> views = viewMap.get(context);
        if (views == null){
            views = new ConcurrentHashMap<>();
            viewMap.put(context,views);
        }
        int id = view.getId();
        if (id == View.NO_ID){
            LOG.e("View没有ID");
            return;
        }
        SyncWeakList<View> viewList = views.get(id);
        if (viewList == null){
            viewList = new SyncWeakList<>();
            views.put(id,viewList);
        }
        if (viewList.contains(view)){
            LOG.e("View 已存在");
            return;
        }
        viewList.add(view);
        onViewAttached(context,view);
        doRegistView(context, view);
    }

    public void removeView(String context,View view){
        Map<Integer,SyncWeakList<View>> views = viewMap.get(context);
        if (views == null){
            LOG.e("未注册Context");
            return;
        }
        int id = view.getId();
        if (id == View.NO_ID){
            LOG.e("View没有ID");
            return;
        }
        SyncWeakList<View> viewList = views.get(id);
        if (viewList == null){
            LOG.e("没有此ID");
            return;
        }
        onViewDettached(context,view);
        viewList.remove(view);
    }

    public void removeViews(String context){
        Map<Integer,SyncWeakList<View>> views = viewMap.remove(context);
        if (views == null){
            LOG.e("未注册Context");
            return;
        }
        for (SyncWeakList<View> viewList:views.values()){
            for (View view:viewList) {
                onViewDettached(context, view);
            }
        }
    }

    public void addViewAttachListener(String context, OnViewAttachListener listener){
        synchronized (context) {
            SyncWeakList<OnViewAttachListener> attachListeners =  attachlsMap.get(context);
            if (attachListeners == null) {
                attachListeners = new SyncWeakList<>();
                attachlsMap.put(context,attachListeners);
            }
            if (!attachListeners.contains(listener)) {
                attachListeners.add(listener);
                Map<Integer,SyncWeakList<View>> views = viewMap.get(context);
                if (views == null) return;
                for (SyncWeakList<View> viewList:views.values()){
                    for (View view:viewList) {
                        onViewDettached(context, view);
                    }
                }
            }
        }
    }

    public void removeViewAttachListener(String context, OnViewAttachListener listener){
        SyncWeakList<OnViewAttachListener> attachListeners =  attachlsMap.get(context);
        if (attachListeners == null) return;
        synchronized (context) {
            attachListeners.remove(listener);
        }
    }


    @Override
    public void onViewAttached(String context, View view) {
        SyncWeakList<OnViewAttachListener> listeners = attachlsMap.get(context);
        if (listeners == null) return;
        for (OnViewAttachListener listener:listeners){
            listener.onViewAttached(context, view);
        }
    }

    @Override
    public void onViewDettached(String context, View view) {
        SyncWeakList<OnViewAttachListener> listeners = attachlsMap.get(context);
        if (listeners == null) return;
        for (OnViewAttachListener listener:listeners){
            listener.onViewDettached(context, view);
        }
    }
}
