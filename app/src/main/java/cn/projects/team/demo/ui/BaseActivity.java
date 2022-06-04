package cn.projects.team.demo.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jude.swipbackhelper.SwipeBackHelper;
import com.jude.swipbackhelper.SwipeListener;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.demo.R;
import cn.droidlover.xdroidmvp.mvp.IPresent;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.router.Router;
import cn.projects.team.demo.utils.AppManager;

public abstract class BaseActivity<P extends IPresent> extends XActivity<P> {
    private boolean isSwipe= false ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppManager.getAppManager().addActivity(this);
        if(isSwipe()){
            initSwipeBackHelper();
        }


    }


    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {

        return super.onCreateView(name, context, attrs);
    }
    public boolean isSwipe(){
        return isSwipe;
    }

    public void setSwipe(boolean swipe) {
        isSwipe = swipe;
    }

    public CommonTitleBar getToolbar() {
        return titlebar;
    }
    public CommonTitleBar getRightToolbar() {
        return titlebars;
    }

    public void setTitlebarText(String centerText) {
        titlebar.getCenterTextView().setText(centerText);
        //titlebars.getCenterTextView().setText(centerText);
        EditText centerSearchEditText = titlebars.getCenterSearchEditText();
        centerSearchEditText.setHint("查询");
       /* Drawable[] compoundDrawables = centerSearchEditText.getCompoundDrawables();
        centerSearchEditText.setCompoundDrawables(compoundDrawables[0],null,null,null);
*/
        titlebar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    finish();
                }
                // CommonTitleBar.ACTION_LEFT_TEXT;        // 左边TextView被点击
                // CommonTitleBar.ACTION_LEFT_BUTTON;      // 左边ImageBtn被点击
                // CommonTitleBar.ACTION_RIGHT_TEXT;       // 右边TextView被点击
                // CommonTitleBar.ACTION_RIGHT_BUTTON;     // 右边ImageBtn被点击
                // CommonTitleBar.ACTION_SEARCH;           // 搜索框被点击,搜索框不可输入的状态下会被触发
                // CommonTitleBar.ACTION_SEARCH_SUBMIT;    // 搜索框输入状态下,键盘提交触发，此时，extra为输入内容
                // CommonTitleBar.ACTION_SEARCH_VOICE;     // 语音按钮被点击
                // CommonTitleBar.ACTION_SEARCH_DELETE;    // 搜索删除按钮被点击
                // CommonTitleBar.ACTION_CENTER_TEXT;      // 中间文字点击
            }
        });
        titlebars.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    finish();
                }
                // CommonTitleBar.ACTION_LEFT_TEXT;        // 左边TextView被点击
                // CommonTitleBar.ACTION_LEFT_BUTTON;      // 左边ImageBtn被点击
                // CommonTitleBar.ACTION_RIGHT_TEXT;       // 右边TextView被点击
                // CommonTitleBar.ACTION_RIGHT_BUTTON;     // 右边ImageBtn被点击
                // CommonTitleBar.ACTION_SEARCH;           // 搜索框被点击,搜索框不可输入的状态下会被触发
                // CommonTitleBar.ACTION_SEARCH_SUBMIT;    // 搜索框输入状态下,键盘提交触发，此时，extra为输入内容
                // CommonTitleBar.ACTION_SEARCH_VOICE;     // 语音按钮被点击
                // CommonTitleBar.ACTION_SEARCH_DELETE;    // 搜索删除按钮被点击
                // CommonTitleBar.ACTION_CENTER_TEXT;      // 中间文字点击
            }
        });
    }
    public void setLeftDrawable(boolean t) {

        titlebar.getLeftTextView().setVisibility( t ? View.VISIBLE :View.INVISIBLE);
        titlebars.getLeftTextView().setVisibility( t ? View.VISIBLE :View.INVISIBLE);

    }


    /**
     * 初始化侧滑关闭
     */
    private void initSwipeBackHelper() {
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(true)//设置是否可滑动
                .setSwipeSensitivity(0.5f)//对横向滑动手势的敏感程度。0为迟钝 1为敏感
                .setSwipeRelateEnable(true)//是否与下一级activity联动(微信效果)。默认关
                .setSwipeEdgePercent(0.1f)//可滑动的范围。百分比。0.2表示为左边20%的屏幕
                .setSwipeRelateOffset(300).addListener(new SwipeListener() {//滑动监听
            @Override
            public void onScroll(float percent, int px) {//滑动的百分比与距离
            }

            @Override
            public void onEdgeTouch() {//当开始滑动
            }

            @Override
            public void onScrollToClose() {//当滑动关闭
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isSwipe()){
            SwipeBackHelper.onDestroy(this);
        }
        AppManager.getAppManager().finishActivity(this);
    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(isSwipe()){
            SwipeBackHelper.onPostCreate(this);
        }
    }

    protected boolean isShowBack() {
        return true;
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }



    @Override
    public void resultError(int errorCode,NetError error) {
        hideLoading();
        if (error != null&& errorCode == 100) {

            switch (error.getType()) {
                case NetError.BusinessError:
                    getvDelegate().toastLong(error.getMessage());
                    break;
                case NetError.NoDataError:
                    getvDelegate().toastLong(error.getMessage());
                    break;
                default:
                    if (emptyView != null) {
                        emptyView.show(false, "网络异常，稍后重试！", null, "", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                emptyView.show(true);
                                getNetData();
                            }
                        });
                    }
                    break;
            }



        }else{
            switch (error.getType()) {

                case NetError.BusinessError:
                    getvDelegate().toastLong(error.getMessage());
                    notifyClearUI();
                    break;
                case NetError.NoDataError:
                    //emptyView.show("还没有相关数据",null);
                    notifyClearUI();
                    break;
                case NetError.AuthError:
                    /*Router.newIntent(BaseActivity.this)
                            .to(LoginActivity.class)
                            .launch();
                    SharedPref.getInstance(BaseActivity.this).putString("token","");
                    SharedPref.getInstance(BaseActivity.this).putString("token","");
                    SharedPref.getInstance(BaseActivity.this).putString("head","");
                    SharedPref.getInstance(BaseActivity.this).putInt("audit",0);
                    SharedPref.getInstance(BaseActivity.this).putString("name","");
                    getvDelegate().toastLong("登录信息失效");
                    finish();*/
                    break;
                case NetError.FrozenError:
                    Router.newIntent(BaseActivity.this)
                            .to(LoginActivity.class)
                            .launch();
                    SharedPref.getInstance(BaseActivity.this).putString("token","");
                    getvDelegate().toastLong(error.getMessage());
                    SharedPref.getInstance(BaseActivity.this).putString("token","");
                    SharedPref.getInstance(BaseActivity.this).putString("token","");
                    SharedPref.getInstance(BaseActivity.this).putString("head","");
                    SharedPref.getInstance(BaseActivity.this).putInt("audit",0);
                    SharedPref.getInstance(BaseActivity.this).putString("name","");
                    finish();
                    break;
                default:
                    notifyClearUI();
                    getvDelegate().toastLong("网络异常，稍后重试！");
                    break;
            }

        }
    }


    public void hideLoading() {
        if (emptyView != null) {
            emptyView.hide();
        }
    }





    public abstract void getNetData();
    public abstract void notifyClearUI();
}
