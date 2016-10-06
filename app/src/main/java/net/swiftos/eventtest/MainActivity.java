package net.swiftos.eventtest;

import android.app.Activity;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import net.swiftos.eventposter.Core.EventPoster;
import net.swiftos.eventposter.Impls.ActivityLife.Annotation.ActivityLife;
import net.swiftos.eventposter.Impls.ActivityLife.Entity.ActivityLifeType;
import net.swiftos.eventposter.Impls.ActivityLife.Handler.ActivityLifeHandler;
import net.swiftos.eventposter.Impls.CustomEvent.Annotation.InjectEvent;
import net.swiftos.eventposter.Impls.CustomEvent.Entity.RunContextType;
import net.swiftos.eventposter.Impls.CustomEvent.Handler.CustomEventHandler;

public class MainActivity extends AppCompatActivity {

    StickyTest stickyTest;

    public MainActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventPoster.Regist(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @ActivityLife(lifeType = ActivityLifeType.OnPause,activity = MainActivity.class)
    public void onPause(Activity activity){
        Toast.makeText(activity,"pause",Toast.LENGTH_LONG).show();
        stickyTest = new StickyTest(this);
    }

    @ActivityLife(lifeType = ActivityLifeType.OnCreate,activity = MainActivity.class)
    public void onCreate(Activity activity,Bundle bundle){
        TestFlag flag = new TestFlag();
        flag.setName("haha");
        EventPoster.With(CustomEventHandler.class).As(flag).BroadCastSticky();
    }

    @InjectEvent(name = "event_a",runType = RunContextType.MainThread)
    public void Eventa(TestFlag flag){
        Toast.makeText(this,flag.getName(),Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventPoster.UnRegist(this);
        EventPoster.UnRegist(stickyTest);
    }
}
