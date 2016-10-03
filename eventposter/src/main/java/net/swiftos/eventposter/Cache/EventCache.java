package net.swiftos.eventposter.Cache;

import net.swiftos.eventposter.Interface.IEventEntity;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by gy939 on 2016/10/3.
 */
public class EventCache {

    private static Map<Annotation,IEventEntity> eventEntityMap = new ConcurrentHashMap<>();

    public static IEventEntity getEventEntity(Annotation annotation){
        return eventEntityMap.get(annotation);
    }

    public static void addEventEntity(Annotation annotation,IEventEntity eventEntity){
        eventEntityMap.put(annotation, eventEntity);
    }

}
