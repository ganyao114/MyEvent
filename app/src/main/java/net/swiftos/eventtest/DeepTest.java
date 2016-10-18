package net.swiftos.eventtest;

import android.content.Context;

import net.swiftos.eventposter.Core.EventPoster;

/**
 * Created by gy939 on 2016/10/18.
 */

public class DeepTest extends StickyTest {
    public DeepTest(Context context) {
        super(context);
        EventPoster.RegistDeep(this);
    }
}
