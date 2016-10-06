package net.swiftos.eventposter.Impls.ActivityLife.Handler;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import net.swiftos.eventposter.Core.Injecter;
import net.swiftos.eventposter.Entity.EventAnnoInfo;
import net.swiftos.eventposter.Exception.EventInvokeException;
import net.swiftos.eventposter.Impls.ActivityLife.Annotation.ActivityLife;
import net.swiftos.eventposter.Impls.ActivityLife.Entity.ActivityBinder;
import net.swiftos.eventposter.Impls.ActivityLife.Entity.ActivityLifeType;
import net.swiftos.eventposter.Impls.ActivityLife.Entity.LifeInvokerEntity;
import net.swiftos.eventposter.Interface.IEventEntity;
import net.swiftos.eventposter.Interface.IHandler;

import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by gy939 on 2016/10/3.
 */
public class ActivityLifeHandler implements IHandler,Application.ActivityLifecycleCallbacks{

    private Map<Class<? extends Activity>,ActivityBinder> activityBinderMap = new ConcurrentHashMap<>();
    private Map<Class,Vector> invokers = new ConcurrentHashMap<>();
    private boolean isRegist = false;

    @Override
    public void init(Object... objects) {
        Application application = (Application) objects[0];
        synchronized (this){
            if (isRegist)
                return;
            application.registerActivityLifecycleCallbacks(this);
            isRegist = true;
        }
    }

    @Override
    public void destory(Object... objects) {
        Application application = (Application) objects[0];
        synchronized (this){
            if (!isRegist)
                return;
            application.unregisterActivityLifecycleCallbacks(this);
            isRegist = false;
        }
    }

    @Override
    public IEventEntity parse(EventAnnoInfo annoInfo) {
        return decode(annoInfo);
    }

    @Override
    public void load(IEventEntity eventEntity, Object object) {
        if (eventEntity == null)
            return;
        LifeInvokerEntity entity = (LifeInvokerEntity) eventEntity;
        if (Injecter.getInsts(entity.getInvokerType()).size() == 0)
            dispathEntities(entity);
    }

    @Override
    public void unload(IEventEntity eventEntity, Object object) {
        if (eventEntity == null)
            return;
        LifeInvokerEntity entity = (LifeInvokerEntity) eventEntity;
        if (Injecter.getInsts(entity.getInvokerType()).size() == 0)
            removeEntities(entity);
    }

    @Override
    public void inject(Object object) {
    }

    @Override
    public void remove(Object object) {
    }

    private void removeEntities(LifeInvokerEntity entity) {
        Class<? extends Activity>[] tarAcTypes = entity.getTarTypes();
        ActivityLifeType lifeType = entity.getType();
        for (Class<? extends Activity> tarAcType:tarAcTypes) {
            ActivityBinder binder = activityBinderMap.get(tarAcType);
            if (binder == null) continue;
            Vector<LifeInvokerEntity> lifeInvokerEntities = binder.getLifes()[lifeType.value()];
            if (lifeInvokerEntities == null) continue;
            lifeInvokerEntities.remove(entity);
        }
    }

    private void dispathEntities(LifeInvokerEntity entity) {
        Class<? extends Activity>[] tarAcTypes = entity.getTarTypes();
        ActivityLifeType lifeType = entity.getType();
        for (Class<? extends Activity> tarAcType:tarAcTypes) {
            ActivityBinder binder = activityBinderMap.get(tarAcType);
            if (binder == null) {
                binder = new ActivityBinder();
                activityBinderMap.put(tarAcType, binder);
            }
            Vector<LifeInvokerEntity> lifeInvokerEntities = binder.getLifes()[lifeType.value()];
            if (lifeInvokerEntities == null){
                lifeInvokerEntities = new Vector<>();
                binder.getLifes()[lifeType.value()] = lifeInvokerEntities;
            }
            if (!lifeInvokerEntities.contains(entity))
                lifeInvokerEntities.add(entity);
        }
    }

    private void dispatchEvent(Class acType, ActivityLifeType type, Object... pars) {
        ActivityBinder binder = activityBinderMap.get(acType);
        if (binder == null)
            return;
        Vector<LifeInvokerEntity> list = binder.getLifes()[type.value()];
        if (list == null)
            return;
        for (LifeInvokerEntity entity:list){
            Vector iks = Injecter.getInsts(entity.getInvokerType());
            if (iks == null||iks.size() == 0)
                continue;
            for (Object invoker:iks){
                try {
                    entity.invoke(invoker,pars);
                } catch (EventInvokeException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private LifeInvokerEntity decode(EventAnnoInfo annoInfo){
        ActivityLife activityLife = (ActivityLife) annoInfo.getAnnotation();
        LifeInvokerEntity entity = new LifeInvokerEntity();
        entity.setMethod(annoInfo.getMethod());
        entity.setType(activityLife.lifeType());
        entity.setTarTypes(activityLife.activity());
        entity.setInvokerType(annoInfo.getClazz());
        return entity;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        dispatchEvent(activity.getClass(),ActivityLifeType.OnCreate,activity,savedInstanceState);
    }



    @Override
    public void onActivityStarted(Activity activity) {
        dispatchEvent(activity.getClass(),ActivityLifeType.OnStart,activity);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        dispatchEvent(activity.getClass(),ActivityLifeType.OnResume,activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        dispatchEvent(activity.getClass(),ActivityLifeType.OnPause,activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        dispatchEvent(activity.getClass(),ActivityLifeType.OnStop,activity);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        dispatchEvent(activity.getClass(),ActivityLifeType.OnSave,activity,outState);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        dispatchEvent(activity.getClass(),ActivityLifeType.OnDestory,activity);
    }
}
