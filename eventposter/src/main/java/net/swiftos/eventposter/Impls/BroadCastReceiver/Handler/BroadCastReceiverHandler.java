package net.swiftos.eventposter.Impls.BroadCastReceiver.Handler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import net.swiftos.eventposter.Core.EventPoster;
import net.swiftos.eventposter.Entity.DynamicHandler;
import net.swiftos.eventposter.Entity.EventAnnoInfo;
import net.swiftos.eventposter.Impls.BroadCastReceiver.Annotation.BroadCastReceiver;
import net.swiftos.eventposter.Impls.BroadCastReceiver.Entity.BroadCastEntity;
import net.swiftos.eventposter.Impls.BroadCastReceiver.Entity.IReciver;
import net.swiftos.eventposter.Interface.IHandler;

import java.lang.reflect.Proxy;

/**
 * Created by gy939 on 2016/10/3.
 */
public class BroadCastReceiverHandler implements IHandler<BroadCastEntity> {

    private static final String ProxyMethod = "onReceiver";
    private Class<?> inter = IReciver.class;

    @Override
    public void init(Object... objects) {

    }

    @Override
    public void destory(Object... objects) {

    }

    @Override
    public BroadCastEntity parse(EventAnnoInfo annoInfo) {
        BroadCastReceiver receiver = (BroadCastReceiver) annoInfo.getAnnotation();
        BroadCastEntity broadCastEntity = new BroadCastEntity();
        broadCastEntity.setMethod(annoInfo.getMethod());
        IntentFilter filter = new IntentFilter();
        for (String str:receiver.value())
            filter.addAction(str);
        broadCastEntity.setFilter(filter);
        return broadCastEntity;
    }

    @Override
    public void load(BroadCastEntity eventEntity, Object object) {
        DynamicHandler handler = new DynamicHandler(object);
        handler.addMethod(ProxyMethod, eventEntity.getMethod());
        final Object bussnessproxy = Proxy.newProxyInstance(inter.getClassLoader(), new Class<?>[]{inter}, handler);
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ((IReciver) bussnessproxy).onReceiver(context,intent);
            }
        };
        EventPoster.getApp().registerReceiver(receiver,eventEntity.getFilter());
    }

    @Override
    public void unload(BroadCastEntity eventEntity, Object object) {

    }

    @Override
    public void inject(Object object) {

    }

    @Override
    public void remove(Object object) {

    }
}
