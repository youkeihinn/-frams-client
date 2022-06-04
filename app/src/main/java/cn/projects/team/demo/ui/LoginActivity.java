package cn.projects.team.demo.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.jakewharton.rxbinding2.view.RxView;
import com.lxj.xpopup.impl.LoadingPopupView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.demo.R;
import cn.droidlover.xdroidmvp.router.Router;

import cn.projects.team.demo.model.Login;
import cn.projects.team.demo.model.RegisterUser;
import cn.projects.team.demo.present.PBase;

public class LoginActivity extends BaseActivity<PBase> {


    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_phone)
    EditText tvPhone;
    @BindView(R.id.tv_password)
    EditText tvPassword;
    @BindView(R.id.btn_login)
    SuperButton btnLogin;
    @BindView(R.id.tv_sms)
    TextView tvSms;
    @BindView(R.id.tv_send)
    TextView tvSend;
    private MyCountDownTimer myCountDownTimer;
    public LoadingPopupView loadingPopup;
    private  boolean type ;
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
        return R.layout.activity_login;
    }

    @Override
    public PBase newP() {
        return new PBase();
    }

    @Override
    public void resultData(int resultCode, int page, Object o) {
        switch (resultCode) {
            case 0:
                myCountDownTimer.start();
                getvDelegate().toastShort("发送成功");
                break;
            case 1:

                getvDelegate().toastShort("登录成功");
                Login login = (Login) o;
                SharedPref.getInstance(LoginActivity.this).putString("token", login.token);
                SharedPref.getInstance(LoginActivity.this).putString("phone", login.phone);
                SharedPref.getInstance(LoginActivity.this).putString("nickName", login.nickName);
                Router.newIntent(LoginActivity.this)
                        .to(MainActivity.class)
                        .launch();
                finish();
                break;
        }

    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                String token = SharedPref.getInstance(LoginActivity.this).getString("token","");
                if(TextUtils.isEmpty(token)){

                }else{

                    Router.newIntent(LoginActivity.this)
                            .to(MainActivity.class)
                            .launch();
                    finish();
                }


            }
            return false;
        }
    });


    @Override
    public void initView() {
        getToolbar().setVisibility(View.GONE);
        handler.sendEmptyMessageDelayed(1, 10);
        setSwipe(false);
        hideLoading();

        //点击登录监听
        RxView.clicks(btnLogin)
                .throttleFirst(2, TimeUnit.SECONDS)//5秒内取第一个事件 防止重复点击发送事件
                .compose(bindToLifecycle())
                .subscribe(o -> {
                    login();
                });


    }

    private void login() {
        String phone = tvPhone.getText().toString().trim();
        RegisterUser registerUser = new RegisterUser();
        if(TextUtils.isEmpty(phone)){
            getvDelegate().toastShort("请输入用户名");
            return ;
        }
        registerUser.userName = phone;
        String password = tvPassword.getText().toString().trim();
        if(TextUtils.isEmpty(password)){
            if(!type){
                getvDelegate().toastShort("请输入密码");
            }else{
                getvDelegate().toastShort("请输入验证码");
            }

            return ;
        }
        registerUser.userPass = password;

        //showloadingPopup("正在登录...");
        getP().login(registerUser);
    }





    @OnClick({R.id.tv_register, R.id.btn_login, R.id.tv_sms,R.id.tv_send,R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                Router.newIntent(LoginActivity.this)
                        .to(RegisterActivity.class)
                        .launch();
                break;
            case R.id.btn_login:
                break;
            case R.id.tv_sms:
                type =!type;
                if(type){
                    tvSms.setText("账号密码登录");
                    tvTitle.setText("短信验证码登录");
                    tvPassword.setText("");
                    tvSend.setVisibility(View.VISIBLE);
                }else {
                    tvSms.setText("短信验证码登录");
                    tvTitle.setText("账号密码登录");
                    tvPassword.setText("");
                    tvSend.setVisibility(View.GONE);
                }
                break;
                case R.id.iv_back:
                    finish();
                    break;
        }
    }





    //倒计时函数
    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            tvSend.setClickable(false);
            tvSend.setText(l / 1000 + "秒");

        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            tvSend.setText("重新获取");
            //设置可点击
            tvSend.setClickable(true);
        }
    }

}
