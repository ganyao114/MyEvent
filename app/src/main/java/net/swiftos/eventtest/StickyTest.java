package net.swiftos.eventtest;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import net.swiftos.eventposter.Core.EventPoster;
import net.swiftos.eventposter.Impls.CustomEvent.Annotation.InjectEvent;
import net.swiftos.eventposter.Impls.CustomEvent.Entity.RunContextType;

/**
 * Created by gy939 on 2016/10/6.
 */
public class StickyTest {

    private Context context;

    public StickyTest(Context context) {
        this.context = context;
        EventPoster.Regist(this);
    }

    @InjectEvent(runType = RunContextType.MainLoop,sticky = true)
    public void show(TestFlag flag){
        Toast.makeText(context,flag.getName(),Toast.LENGTH_LONG).show();
    }

}
