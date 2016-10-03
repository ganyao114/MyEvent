package net.swiftos.eventposter.Impls.BroadCastReceiver.Annotation;

import net.swiftos.eventposter.Annotation.EventBase;
import net.swiftos.eventposter.Impls.BroadCastReceiver.Handler.BroadCastReceiverHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by gy939 on 2016/10/3.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(BroadCastReceiverHandler.class)
public @interface BroadCastReceiver {
    String[] value();
}
