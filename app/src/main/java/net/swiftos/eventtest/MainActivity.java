package net.swiftos.eventtest;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import net.swiftos.eventposter.Core.EventPoster;
import net.swiftos.eventposter.Impls.ActivityLife.Annotation.ActivityLife;
import net.swiftos.eventposter.Impls.ActivityLife.Entity.ActivityLifeType;
import net.swiftos.eventposter.Impls.ActivityLife.Handler.ActivityLifeHandler;

public class MainActivity extends AppCompatActivity {

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
    }

    @ActivityLife(lifeType = ActivityLifeType.OnCreate,activity = MainActivity.class)
    public void onCreate(Activity activity,Bundle bundle){
        Toast.makeText(activity,"create",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventPoster.UnRegist(this);
    }
}
