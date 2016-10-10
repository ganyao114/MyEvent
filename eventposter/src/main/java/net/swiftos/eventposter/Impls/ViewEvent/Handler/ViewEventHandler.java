package net.swiftos.eventposter.Impls.ViewEvent.Handler;

import net.swiftos.eventposter.Entity.EventAnnoInfo;
import net.swiftos.eventposter.Impls.ViewEvent.Entity.ViewEventEntity;
import net.swiftos.eventposter.Interface.IHandler;

/**
 * Created by gy939 on 2016/10/10.
 */
public class ViewEventHandler implements IHandler<ViewEventEntity> {
    @Override
    public void init(Object... objects) {

    }

    @Override
    public void destory(Object... objects) {

    }

    @Override
    public ViewEventEntity parse(EventAnnoInfo annoInfo) {
        return null;
    }

    @Override
    public void load(ViewEventEntity eventEntity, Object invoker) {

    }

    @Override
    public void unload(ViewEventEntity eventEntity, Object invoker) {

    }

    @Override
    public void inject(Object object) {

    }

    @Override
    public void remove(Object object) {

    }
}
