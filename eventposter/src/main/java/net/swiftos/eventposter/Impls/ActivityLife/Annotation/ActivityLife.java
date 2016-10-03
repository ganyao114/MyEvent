package net.swiftos.eventposter.Impls.ActivityLife.Annotation;

import android.app.Activity;

import net.swiftos.eventposter.Annotation.EventBase;
import net.swiftos.eventposter.Impls.ActivityLife.Entity.ActivityLifeType;
import net.swiftos.eventposter.Impls.ActivityLife.Handler.ActivityLifeHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by gy939 on 2016/9/26.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(ActivityLifeHandler.class)
public @interface ActivityLife {
    ActivityLifeType lifeType();
    Class<? extends Activity>[] activity();
}
