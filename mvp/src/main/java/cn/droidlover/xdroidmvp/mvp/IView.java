package cn.droidlover.xdroidmvp.mvp;

import android.os.Bundle;
import android.view.View;

import cn.droidlover.xdroidmvp.net.NetError;

/**
 * Created by wanglei on 2016/12/29.
 */

public interface IView<P> {
    void bindUI(View rootView);

    void bindEvent();

    void initData(Bundle savedInstanceState);

    int getOptionsMenuId();

    int getLayoutId();

    boolean useEventBus();

    P newP();

    void  initView();

    void resultData(int resultCode ,int page ,Object o);

    void resultError(int errorCode,NetError error);

}
