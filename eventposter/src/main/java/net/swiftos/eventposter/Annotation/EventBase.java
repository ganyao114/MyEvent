package net.swiftos.eventposter.Annotation;

import net.swiftos.eventposter.Interface.IHandler;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by gy939 on 2016/9/29.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.ANNOTATION_TYPE,ElementType.METHOD,ElementType.TYPE})
public @interface EventBase{
    Class<? extends IHandler> value();
}
