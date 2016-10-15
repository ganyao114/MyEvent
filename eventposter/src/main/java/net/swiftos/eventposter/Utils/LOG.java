package net.swiftos.eventposter.Utils;


import android.util.Log;

/**
 * Created by ifanr on 16/10/11.
 */

public class LOG {

    private final static String TAG = "EventPoster";
    private static boolean isDebug = true;

    public static void setIsDebug(boolean isDebug) {
        LOG.isDebug = isDebug;
    }

    public static void e(String msg){
        if (isDebug)
            Log.e(TAG,msg);
    }
}
