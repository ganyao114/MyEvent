package net.swiftos.eventtest;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import net.swiftos.eventposter.Core.EventPoster;
import net.swiftos.eventposter.Impls.ActivityLife.Annotation.ActivityLife;
import net.swiftos.eventposter.Impls.ActivityLife.Entity.ActivityLifeType;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventPoster.Regist(this);
    }

    @ActivityLife(lifeType = ActivityLifeType.OnPause,activity = MainActivity.class)
    public void onPause(Activity activity){
        Toast.makeText(activity,"pause",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventPoster.UnRegist(this);
    }
}
