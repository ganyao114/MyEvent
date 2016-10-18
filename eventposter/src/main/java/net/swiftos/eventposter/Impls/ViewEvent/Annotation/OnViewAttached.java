package net.swiftos.eventposter.Impls.ViewEvent.Annotation;

import android.view.View;

import net.swiftos.eventposter.Annotation.EventBase;
import net.swiftos.eventposter.Impls.ViewEvent.Handler.ViewEventHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by gy939 on 2016/10/16.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(ViewEventHandler.class)
@ViewEventBase(listenerType = View.OnClickListener.class, listenerSetter = "setOnClickListener", methodName = "onClick",viewType = View.class)
public @interface OnViewAttached {

}
