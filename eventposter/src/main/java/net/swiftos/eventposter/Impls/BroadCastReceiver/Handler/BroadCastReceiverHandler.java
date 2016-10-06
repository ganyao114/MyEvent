package net.swiftos.eventposter.Impls.BroadCastReceiver.Handler;

import net.swiftos.eventposter.Entity.EventAnnoInfo;
import net.swiftos.eventposter.Impls.BroadCastReceiver.Entity.BroadCastEntity;
import net.swiftos.eventposter.Interface.IHandler;

/**
 * Created by gy939 on 2016/10/3.
 */
public class BroadCastReceiverHandler implements IHandler<BroadCastEntity> {
    @Override
    public void init(Object... objects) {

    }

    @Override
    public void destory(Object... objects) {

    }

    @Override
    public BroadCastEntity parse(EventAnnoInfo annoInfo) {
        return null;
    }

    @Override
    public void load(BroadCastEntity eventEntity, Object object) {

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
