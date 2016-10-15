package net.swiftos.eventtest;

import android.view.View;
import android.widget.Toast;

import net.swiftos.eventposter.Impls.ViewEvent.Annotation.OnClick;
import net.swiftos.eventposter.Presenter.IPresenter;
import net.swiftos.eventposter.Presenter.Presenter;

/**
 * Created by gy939 on 2016/10/15.
 */

public class MainPresenter extends Presenter {

    @OnClick(context = "main",viewIds = R.id.button)
    public void OnClick(View view){
        Toast.makeText(view.getContext(),"click",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPresenterDestory(IPresenter context) {
        super.onPresenterDestory(context);
    }

    @Override
    public void onPresenterInit(IPresenter context) {
        super.onPresenterInit(context);
    }
}
