package net.swiftos.eventtest;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import net.swiftos.eventposter.Core.EventPoster;
import net.swiftos.eventposter.Presenter.Presenter;

/**
 * Created by gy939 on 2016/10/3.
 */
public class Myapplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        EventPoster.init(this);
        EventPoster.PreLoadDeep(new Class[]{MainActivity.class,DeepTest.class,MainPresenter.class});
        Presenter.establish();
        Presenter.With(null).start(MainPresenter.class);
        LeakCanary.install(this);
    }



}
