package net.swiftos.eventposter.Impls.ViewEvent.Interface;

import android.content.Context;
import android.view.View;

/**
 * Created by gy939 on 2016/10/16.
 */

public interface OnViewAttachListener {
    public void onViewAttached(String context,View view);
    public void onViewDettached(String context,View view);
}
