package net.swiftos.eventtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import net.swiftos.eventposter.Core.EventPoster;
import net.swiftos.eventposter.Impls.ViewEvent.Handler.ViewEventHandler;

public class Main2Activity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        button = (Button) findViewById(R.id.button);
        button.setTag("main2");
        EventPoster.With(ViewEventHandler.class).addView("main",button);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventPoster.With(ViewEventHandler.class).removeView("main",button);
    }

}
