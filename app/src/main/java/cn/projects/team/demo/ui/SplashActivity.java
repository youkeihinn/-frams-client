package cn.projects.team.demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.demo.R;
import cn.droidlover.xdroidmvp.router.Router;
import cn.projects.team.demo.present.PBase;

public class SplashActivity extends BaseActivity<PBase> {
    @BindView(R.id.banner)
    ImageView banner;

    @Override
    public void getNetData() {

    }

    @Override
    public void notifyClearUI() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//满屏显示
        return R.layout.activity_splash;
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
               String token = SharedPref.getInstance(SplashActivity.this).getString("token","");
                if(TextUtils.isEmpty(token)){
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else{

                    Router.newIntent(SplashActivity.this)
                            .to(MainActivity.class)
                            .launch();
                    finish();
                }


            }
            return false;
        }
    });

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return false;
    }

    @Override
    public PBase newP() {
        return new PBase();
    }

    @Override
    public void initView() {
        hideLoading();
        getToolbar().setVisibility(View.GONE);
        setSwipe(false);

        String bannerUrl = SharedPref.getInstance(SplashActivity.this).getString("bannerUrl", "");
        if(!TextUtils.isEmpty(bannerUrl)){
            Glide.with(context).load(bannerUrl).into(banner);
        }
        handler.sendEmptyMessageDelayed(1, 5000);

    }

    @Override
    public void resultData(int resultCode, int page, Object o) {



    }


}
