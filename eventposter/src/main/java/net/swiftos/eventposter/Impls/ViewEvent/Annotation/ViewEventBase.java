package net.swiftos.eventposter.Impls.ViewEvent.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by gy939 on 2016/10/10.
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewEventBase {
    Class<?> listenerType();

    Class<?> viewType();

    String listenerSetter();

    String methodName();
}
