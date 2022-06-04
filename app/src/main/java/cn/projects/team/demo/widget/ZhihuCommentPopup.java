package cn.projects.team.demo.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;

import com.lxj.easyadapter.EasyAdapter;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.VerticalRecyclerView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import cn.droidlover.xdroidmvp.demo.R;

import cn.projects.team.demo.utils.GlideImageLoader;

public class ZhihuCommentPopup extends BottomPopupView {
    VerticalRecyclerView recyclerView;
    private ArrayList<String> data;
    private EasyAdapter<String> commonAdapter;
    private Activity activity ;
    public PopEnterPassword popEnterPassword;
    private Context context ;
    private String  roomPrice;

    public ZhihuCommentPopup(@NonNull Context context, Activity activity, String roomPrice ) {
        super(context);
        this.roomPrice = roomPrice ;
        this.activity= activity;
        this.context = context;

        popEnterPassword = new PopEnterPassword(activity,roomPrice+"");
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_bottom_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

    }


    public void showPayKeyBoard(View view) {

        // 显示窗口
        popEnterPassword.showAtLocation(view,
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置




    }

    private void initBanner(Banner banner, List<String> images) {
        banner.setVisibility(View.VISIBLE);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2 * 1000);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

    }
    //完全可见执行
    @Override
    protected void onShow() {
        super.onShow();
    }

    //完全消失执行
    @Override
    protected void onDismiss() {

    }

    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getWindowHeight(getContext()) * .85f);
    }

}
