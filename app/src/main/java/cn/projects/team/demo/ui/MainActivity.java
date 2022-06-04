package cn.projects.team.demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.blankj.rxbus.RxBus;
import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.XFragmentAdapter;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.demo.R;

import cn.projects.team.demo.model.Login;
import cn.projects.team.demo.present.PBase;
import cn.projects.team.demo.ui.fragment.HomeFragment;
import cn.projects.team.demo.ui.fragment.MeFragment;
import cn.projects.team.demo.ui.fragment.NoticeFragment;
import cn.projects.team.demo.widget.ViewPagerSlide;

public class MainActivity extends BaseActivity<PBase> {

    @BindView(R.id.bbl)
    BottomBarLayout mBottomBarLayout;
    @BindView(R.id.vp_content)
    ViewPagerSlide mVpContent;
    private List<Fragment> fragmentList = new ArrayList<>();
    private RotateAnimation mRotateAnimation;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private Handler mHandler = new Handler();
    private int[] mNormalIconIds = new int[]{
            R.mipmap.tab_home_normal,R.mipmap.ribao_no,R.mipmap.tab_me_normal
    };

    private int[] mSelectedIconIds = new int[]{
            R.mipmap.tab_home_selected,R.mipmap.ribao_select,R.mipmap.tab_me_selected
    };

    private int[] mTitleIds = new int[]{
            R.string.tab_home ,

            R.string.tab_car,

            R.string.tab_me
    };

    @Override
    public void getNetData() {

    }

    @Override
    public void notifyClearUI() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        for (int i = 0; i < mTitleIds.length; i++) {
            //创建item
            BottomBarItem item = createBottomBarItem(i);
            mBottomBarLayout.addItem(item);

        }

        initListener();
    }



    private void setHomeVpAdapter() {

    /*    CarFragment carFragment = new CarFragment();
        ConversationListFragment conversationListFragment = new ConversationListFragment();
       ;*/
        HomeFragment homeFragment = new HomeFragment();
        MeFragment meFragment = new MeFragment();
        NoticeFragment noticeFragment = new NoticeFragment();

       /* fragmentList.add(carFragment);
        fragmentList.add(conversationListFragment);
        fragmentList.add(serviceOrdersFragment);*/
        fragmentList.add(homeFragment);
        fragmentList.add(noticeFragment);
        fragmentList.add(meFragment);
        XFragmentAdapter xFragmentAdapter = new XFragmentAdapter(getSupportFragmentManager(), fragmentList, null);
        mVpContent.setAdapter(xFragmentAdapter);
        mVpContent.setOffscreenPageLimit(3);
        mBottomBarLayout.setViewPager(mVpContent);
    }



    private void initListener() {
        setHomeVpAdapter();
        mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final BottomBarItem bottomBarItem, int previousPosition, final int currentPosition) {
                Log.i("MainActivity", "position: " + currentPosition);
                //setTitlebarText(getString(mTitleIds[currentPosition]));
                //changeFragment(currentPosition);

                if (currentPosition == 0) {


                    //如果是第一个，即首页
                    if (previousPosition == currentPosition) {
                        //如果是在原来位置上点击,更换首页图标并播放旋转动画
                        if (mRotateAnimation != null && !mRotateAnimation.hasEnded()){
                            //如果当前动画正在执行
                            return;
                        }

                        bottomBarItem.setSelectedIcon(R.mipmap.tab_loading);//更换成加载图标 setResId

                        //播放旋转动画
                        if (mRotateAnimation == null) {
                            mRotateAnimation = new RotateAnimation(0, 360,
                                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                                    0.5f);
                            mRotateAnimation.setDuration(800);
                            mRotateAnimation.setRepeatCount(-1);
                        }
                        ImageView bottomImageView = bottomBarItem.getImageView();
                        bottomImageView.setAnimation(mRotateAnimation);
                        bottomImageView.startAnimation(mRotateAnimation);//播放旋转动画

                        //模拟数据刷新完毕
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                boolean tabNotChanged = mBottomBarLayout.getCurrentItem() == currentPosition; //是否还停留在当前页签
                                bottomBarItem.setSelectedIcon(R.mipmap.tab_home_selected);//更换成首页原来选中图标
                                cancelTabLoading(bottomBarItem);
                            }
                        }, 3000);
                        return;
                    }
                }

                //如果点击了其他条目
                BottomBarItem bottomItem = mBottomBarLayout.getBottomItem(0);
                bottomItem.setSelectedIcon(R.mipmap.tab_home_selected);//更换为原来的图标
                cancelTabLoading(bottomItem);//停止旋转动画
            }
        });

       /* mBottomBarLayout.setUnread(0, 20);//设置第一个页签的未读数为20
        mBottomBarLayout.setUnread(1, 1001);//设置第二个页签的未读数*/

    }


    /**
     * 停止首页页签的旋转动画
     */
    private void cancelTabLoading(BottomBarItem bottomItem) {
        Animation animation = bottomItem.getImageView().getAnimation();
        if (animation != null) {
            animation.cancel();
        }
    }

    private BottomBarItem createBottomBarItem(int i) {
        int marginTop = 7;
        if(i==1){
            marginTop=3;
        }
        BottomBarItem item = new BottomBarItem.Builder(this)
                .titleTextSize(8)
                .titleNormalColor(R.color.tab_normal_color)
                .titleSelectedColor(R.color.C1296db)
//              .openTouchBg(false)
                .marginTop(marginTop)
   //             .itemPadding(8)
//              .unreadNumThreshold(99)
//              .unreadTextColor(R.color.white)

                //还有很多属性，详情请查看Builder里面的方法
                //There are still many properties, please see the methods in the Builder for details.
                .create(mNormalIconIds[i], mSelectedIconIds[i], getString(mTitleIds[i]));
        return item;
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public PBase newP() {
        return new PBase();
    }

    @Override
    public void initView() {
        getToolbar().setVisibility(View.GONE);
        //setLeftDrawable(false);
        //setSwipe(false);
        hideLoading();

        RxBus.getDefault().subscribeSticky(this, new RxBus.Callback<String>() {
            @Override
            public void onEvent(String s) {
                if(s.equals("finish")){
                    finish();
                }

            }
        });

        String phone = SharedPref.getInstance(MainActivity.this).getString("phone","");
        if(!android.text.TextUtils.isEmpty(phone)){
            Login login = new Login();
            login.phone= phone;

        }

    }

    @Override
    public void resultData(int resultCode, int page, Object o) {


    }
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return false;
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
        }
        return true;


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销
        RxBus.getDefault().unregister(this);
    }




}
