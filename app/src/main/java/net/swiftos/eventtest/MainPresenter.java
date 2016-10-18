package net.swiftos.eventtest;

import android.view.View;
import android.widget.Toast;

import net.swiftos.eventposter.Core.EventPoster;
import net.swiftos.eventposter.Impls.ViewEvent.Annotation.OnClick;
import net.swiftos.eventposter.Impls.ViewEvent.Handler.ViewEventHandler;
import net.swiftos.eventposter.Impls.ViewEvent.Interface.OnViewAttachListener;
import net.swiftos.eventposter.Presenter.IPresenter;
import net.swiftos.eventposter.Presenter.Presenter;

/**
 * Created by gy939 on 2016/10/15.
 */

public class MainPresenter extends Presenter implements OnViewAttachListener {

    @OnClick(context = "main",viewIds = R.id.button)
    public void OnClick(View view){
        Toast.makeText(view.getContext(),"click",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPresenterDestory(IPresenter context) {
        super.onPresenterDestory(context);
        EventPoster.With(ViewEventHandler.class).removeViewAttachListener("main",this);
    }

    @Override
    public void onPresenterInit(IPresenter context) {
        super.onPresenterInit(context);
        EventPoster.With(ViewEventHandler.class).addViewAttachListener("main",this);
    }

    @Override
    public void onViewAttached(String context, View view) {
        Toast.makeText(view.getContext(),"view attached",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onViewDettached(String context, View view) {
        Toast.makeText(view.getContext(),"view dettached",Toast.LENGTH_LONG).show();
    }
}
