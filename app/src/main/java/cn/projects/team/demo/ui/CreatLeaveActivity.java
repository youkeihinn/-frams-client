package cn.projects.team.demo.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.demo.R;
import cn.projects.team.demo.model.Leave;
import cn.projects.team.demo.present.PBase;

public class CreatLeaveActivity extends BaseActivity<PBase> {
    @BindView(R.id.tv_temperature)
    TextView tvTemperature;
    @BindView(R.id.tv_conditions)
    EditText tvConditions;
    @BindView(R.id.btn_login)
    SuperButton btnLogin;

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
        return R.layout.activity_create_leave;
    }

    @Override
    public PBase newP() {
        return new PBase();
    }

    @Override
    public void initView() {
        hideLoading();
        setTitlebarText("请假申请");
    }

    @Override
    public void resultData(int resultCode, int page, Object o) {
        getvDelegate().toastShort("申请成功");
         finish();
    }


    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        String s = tvTemperature.getText().toString();
        String s1 = tvConditions.getText().toString();

        if (TextUtils.isEmpty(s)) {
            getvDelegate().toastShort("请选择请假时间");
        }
        if (TextUtils.isEmpty(s1)) {
            getvDelegate().toastShort("请输入请假事由");
        }
        Leave leave =new Leave();
        leave.setStartTime(s);
        leave.setReason(s1);
        leave.setType("请假");
        getP().sendLeave(leave);
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @OnClick(R.id.tv_temperature)
    public void onViewClicked1() {

        TimePickerView pvTime = new TimePickerBuilder(CreatLeaveActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                String format = sdf.format(date);
                tvTemperature.setText(format);


            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示

                .build();//是否显示为对话框样式
        pvTime.show();
    }
}
