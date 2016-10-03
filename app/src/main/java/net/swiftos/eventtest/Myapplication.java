package net.swiftos.eventtest;

import android.app.Application;
import net.swiftos.eventposter.Core.EventPoster;

/**
 * Created by gy939 on 2016/10/3.
 */
public class Myapplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        EventPoster.init(this);
        EventPoster.PreLoad(new Class[]{MainActivity.class});
    }



}
