package cn.projects.team.demo.ui.fragment;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.demo.R;
import cn.droidlover.xdroidmvp.router.Router;
import cn.projects.team.demo.present.PBaseFragment;
import cn.projects.team.demo.ui.LoginActivity;
import cn.projects.team.demo.ui.MyAttendanceActivity;
import cn.projects.team.demo.ui.PersonDataActivity;
import cn.projects.team.demo.ui.SettingPassWordActivity;

public class MeFragment extends BaseLazyFragment<PBaseFragment> {


    @BindView(R.id.root_notice)
    LinearLayout rootNotice;

    @BindView(R.id.tv_card)
    TextView tvCard;
    @BindView(R.id.tv_password)
    TextView tvPassword;
    @BindView(R.id.layout_out)
    LinearLayout layoutOut;

    @BindView(R.id.name)
    TextView name;



    public static MeFragment newInstance() {
        Bundle args = new Bundle();
        MeFragment fragment = new MeFragment();
        return fragment;
    }

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
        return R.layout.activity_person_data;
    }

    @Override
    public PBaseFragment newP() {
        return new PBaseFragment();
    }

    @Override
    public void initView() {
        String phoneS = SharedPref.getInstance(getActivity()).getString("phone", "");
        String nickName = SharedPref.getInstance(getActivity()).getString("nickName", "");

        name.setText(nickName);
    }

    @Override
    public void resultData(int resultCode, int page, Object o) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        /*if (isVisibleToUser) {
            String token = SharedPref.getInstance(getContext()).getString("token", "");
            if(!TextUtils.isEmpty(token)){
                getP().getPersonalData();
            }else{
                tvTitle.setText("点击登录");
                String headIcon = SharedPref.getInstance(getContext()).getString("head", "");
                ILFactory.getLoader().loadCircle(headIcon, head, new ILoader.Options(R.mipmap.default_head, R.mipmap.default_head));
            }

        }*/

    }


    @OnClick(R.id.tv_password)
    public void onClick() {
        Router.newIntent(getActivity())
                .to(SettingPassWordActivity.class)
                .requestCode(1000)
                .launch();
    }


    @OnClick(R.id.zhanghu)
    public void onClick11() {
        Router.newIntent(getActivity())
                .to(PersonDataActivity.class)
                .launch();

    }



    @OnClick(R.id.layout_out)
    public void onClick1() {


        SharedPref.getInstance(getActivity()).clear();
        Router.newIntent(getActivity())
                .to(LoginActivity.class)
                .launch();
        getActivity().finish();

    }

    @OnClick(R.id.myAttendance)
    public void myAttendance() {
        Router.newIntent(getActivity())
                .to(MyAttendanceActivity.class)
                .launch();

    }


}
