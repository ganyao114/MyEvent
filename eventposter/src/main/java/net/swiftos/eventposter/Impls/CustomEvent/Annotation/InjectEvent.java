package net.swiftos.eventposter.Impls.CustomEvent.Annotation;

import net.swiftos.eventposter.Annotation.EventBase;
import net.swiftos.eventposter.Impls.CustomEvent.Entity.RunContextType;
import net.swiftos.eventposter.Impls.CustomEvent.Handler.CustomEventHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by gy939 on 2016/10/5.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(CustomEventHandler.class)
public @interface InjectEvent {
    String name() default "";
    RunContextType runType() default RunContextType.CurrentThread;
    int delay() default 0;
    boolean sticky() default false;
}
