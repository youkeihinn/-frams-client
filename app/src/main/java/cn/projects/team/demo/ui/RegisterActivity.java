package cn.projects.team.demo.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.allen.library.SuperButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.demo.R;
import cn.projects.team.demo.model.Login;
import cn.projects.team.demo.model.RegisterUser;
import cn.projects.team.demo.present.PBase;

public class RegisterActivity extends BaseActivity<PBase> {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_phone)
    EditText tvPhone;

    @BindView(R.id.tv_password)
    EditText tvPassword;
    @BindView(R.id.btn_register)
    SuperButton btnRegister;
    @BindView(R.id.tv_name)
    EditText tvName;


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
        return R.layout.activity_register;
    }

    @Override
    public PBase newP() {
        return new PBase();
    }

    @Override
    public void initView() {
        getToolbar().setVisibility(View.GONE);
        setTitlebarText("用户注册");
        hideLoading();


    }

    private void registerApp() {

        String phone = tvPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            getvDelegate().toastShort("请输入用户名");
            return;
        }
        String smsCode = tvName.getText().toString();
        if (TextUtils.isEmpty(smsCode)) {
            getvDelegate().toastShort("请输入姓名");
            return;
        }
        String password = tvPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            getvDelegate().toastShort("请输入密码");
            return;
        }
        showloadingPopup("注册中");
        RegisterUser user = new RegisterUser();
        user.name = smsCode;
        user.userPass = password;
        user.userName = phone;
        getP().registerUser(user);
    }

    @Override
    public void resultData(int resultCode, int page, Object o) {
        switch (resultCode) {

            case 1:
                getvDelegate().toastShort("注册成功");
                finish();
                Login login = (Login) o;

                break;

        }

    }


    @OnClick({R.id.iv_back, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.btn_register:
                registerApp();
                break;
        }
    }


}
