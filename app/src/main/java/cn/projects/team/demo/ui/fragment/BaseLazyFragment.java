package cn.projects.team.demo.ui.fragment;

import android.text.TextUtils;
import android.view.View;

import com.qmuiteam.qmui.widget.QMUIEmptyView;

import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.demo.R;
import cn.droidlover.xdroidmvp.mvp.IPresent;
import cn.droidlover.xdroidmvp.mvp.XLazyFragment;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.router.Router;
import cn.projects.team.demo.ui.LoginActivity;

/**
 * Created by Administrator on 2018/6/4 0004.
 */

public abstract class BaseLazyFragment<P extends IPresent> extends XLazyFragment<P> {
    protected QMUIEmptyView emptyView;
    public static final String CONTENT = "content";

    @Override
    public void bindUI(View rootView) {
        super.bindUI(rootView);
        emptyView = (QMUIEmptyView) rootView.findViewById(R.id.empty_loading_layout);
    }

    public void setRetryView(NetError error) {
        if (emptyView != null) {
            emptyView.show(false, error.getMessage(), null, "点击重试", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    emptyView.show(true);
                    getNetData();
                }
            });
        }
    }


    public void hideLoading() {
        if (emptyView != null) {
            emptyView.hide();
        }
    }

    public abstract void getNetData();
    public abstract void notifyClearUI();
    @Override
    public void resultError(int errorCode,NetError error) {
        hideLoading();
        switch (error.getType()) {
            case NetError.BusinessError:
                getvDelegate().toastLong(error.getMessage());

                break;
            case NetError.NoDataError:
                emptyView.show("还没有数据哦",null);
                notifyClearUI();
                break;
            case NetError.AuthError:

               Router.newIntent(getActivity())
                        .to(LoginActivity.class)
                        .launch();
                SharedPref.getInstance(getActivity()).clear();

                getvDelegate().toastLong("登录信息失效");
                getActivity().finish();
                break;
            case NetError.FrozenError:
                Router.newIntent(getActivity())
                        .to(LoginActivity.class)
                        .launch();
                SharedPref.getInstance(getActivity()).clear();
                getvDelegate().toastLong(error.getMessage());
                getActivity().finish();
                break;

            default:

                getvDelegate().toastLong("网络超时，点击重试！");
                break;
        }
    }


    public boolean isLogin() {

        String token = SharedPref.getInstance(getContext()).getString("token", "");
        if(TextUtils.isEmpty(token)){
            Router.newIntent(getActivity())
                    .to(LoginActivity.class)
                    .launch();
            return false;
        }

        return true;
    }
}
