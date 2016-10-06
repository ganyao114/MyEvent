package net.swiftos.eventposter.Impls.CustomEvent.Handler;

import net.swiftos.eventposter.Core.Injecter;
import net.swiftos.eventposter.Entity.EventAnnoInfo;
import net.swiftos.eventposter.Exception.EventInvokeException;
import net.swiftos.eventposter.Impls.CustomEvent.Annotation.InjectEvent;
import net.swiftos.eventposter.Impls.CustomEvent.Entity.CustomEventEntity;
import net.swiftos.eventposter.Impls.CustomEvent.Entity.RunContextType;
import net.swiftos.eventposter.Interface.IEventEntity;
import net.swiftos.eventposter.Interface.IHandler;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by gy939 on 2016/10/5.
 */
public class CustomEventHandler implements IHandler<CustomEventEntity>{

    private Map<Class,Map<String,CustomEventEntity>> eventMap = new ConcurrentHashMap<>();

    @Override
    public void init(Object... objects) {

    }

    @Override
    public void destory(Object... objects) {

    }

    @Override
    public CustomEventEntity parse(EventAnnoInfo annoInfo) {
        InjectEvent eventAnno = (InjectEvent) annoInfo.getAnnotation();
        int delay = eventAnno.delay();
        RunContextType type = eventAnno.runType();
        String name = eventAnno.name();
        Method method = annoInfo.getMethod();
        if (name== null||name.equals(""))
            name = method.getName();
        Class parType = method.getParameterTypes()[0];
        CustomEventEntity entity = new CustomEventEntity();
        entity.setMethod(method);
        entity.setContextType(type);
        entity.setDelay(delay);
        entity.setName(name);
        entity.setAnnotation(eventAnno);
        entity.setParType(parType);
        entity.setInvokeType(annoInfo.getClazz());
        return entity;
    }

    @Override
    public void load(CustomEventEntity eventEntity) {
        Map<String,CustomEventEntity> map = eventMap.get(eventEntity.getParType());
        if (map == null){
            map = new ConcurrentHashMap<>();
            eventMap.put(eventEntity.getParType(),map);
        }
        map.put(eventEntity.getName(),eventEntity);
    }

    @Override
    public void unload(CustomEventEntity eventEntity) {
        Map<String,CustomEventEntity> map = eventMap.get(eventEntity.getParType());
        if (map == null)
            return;
        map.remove(eventEntity.getName());
    }

    @Override
    public void inject(Object object) {

    }

    @Override
    public void remove(Object object) {

    }

    public void broadcast(Object object){
        Map<String,CustomEventEntity> map = eventMap.get(object.getClass());
        if (map == null)
            return;
        for (Map.Entry<String,CustomEventEntity> entry:map.entrySet()){
            CustomEventEntity entity = entry.getValue();
            Vector invokers = Injecter.getInsts(entity.getInvokeType());
            for (Object invoker:invokers){
                try {
                    entity.invoke(invoker,object);
                } catch (EventInvokeException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void post(Object object,String name){
        Map<String,CustomEventEntity> map = eventMap.get(object.getClass());
        if (map == null)
            return;
    }

    public class ExtCall{

    }

}
