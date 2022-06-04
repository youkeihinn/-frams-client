package cn.projects.team.demo.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.demo.R;
import cn.droidlover.xdroidmvp.router.Router;
import cn.projects.team.demo.model.Login;
import cn.projects.team.demo.model.RegisterUser;
import cn.projects.team.demo.present.PBase;

public class SettingPassWordActivity extends BaseActivity<PBase> {


    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.tv_newPass)
    EditText tvNewPass;
    @BindView(R.id.tv_reNewPass)
    EditText tvReNewPass;
    @BindView(R.id.confirm)
    TextView confirm;

    private String phone1;

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
        return R.layout.activity_setting_password;
    }

    @Override
    public PBase newP() {
        return new PBase();
    }

    @Override
    public void initView() {
        setTitlebarText("设置新密码");
        hideLoading();
        phone1 = SharedPref.getInstance(SettingPassWordActivity.this).getString("phone", "");
        this.phone.setText(phone1);
    }

    @Override
    public void resultData(int resultCode, int page, Object o) {
        switch (resultCode) {
            case 0:

                break;
            case 2:
                getvDelegate().toastShort("修改成功");
                finish();
                break;
        }
    }


    @OnClick({R.id.confirm})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:
                editPassWord();
                break;

        }


    }

    private void editPassWord() {
        String smsCode = phone.getText().toString();
        if (TextUtils.isEmpty(smsCode)) {
            getvDelegate().toastShort("请输入旧密码");
            return;
        }

        String newPass = tvNewPass.getText().toString();
        if (TextUtils.isEmpty(newPass)) {
            getvDelegate().toastShort("请设置6-25位新密码");
            return;
        }
        String reNewPass = tvReNewPass.getText().toString();
        if (TextUtils.isEmpty(reNewPass)) {
            getvDelegate().toastShort("请再次设置6-25位新密码");
            return;
        }
        if (!reNewPass.equals(newPass)) {
            getvDelegate().toastShort("2次输入的密码不一致");
            return;
        }

        RegisterUser user = new RegisterUser();
        user.userPass = reNewPass;
        user.juserPass = smsCode;
        getP().reNewPass(user);
    }







}
