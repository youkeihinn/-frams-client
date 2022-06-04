package cn.projects.team.demo.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.demo.R;
import cn.droidlover.xdroidmvp.router.Router;
import cn.droidlover.xrecyclerview.XRecyclerContentLayout;
import cn.droidlover.xrecyclerview.XRecyclerView;
import cn.projects.team.demo.adapter.AttendTimeAdapter;
import cn.projects.team.demo.model.AttendTime;
import cn.projects.team.demo.model.Datas;
import cn.projects.team.demo.present.PBaseFragment;
import cn.projects.team.demo.ui.SignActivity;

public class HomeFragment extends BaseLazyFragment<PBaseFragment> {


    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;
    @BindView(R.id.xRecycler)
    XRecyclerContentLayout contentLayout;
    @BindView(R.id.et_nam)
    EditText etNam;

    private List<AttendTime> list = new ArrayList<>();
    private AttendTimeAdapter adapter;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void getNetData() {

    }

    @Override
    public void notifyClearUI() {
        list.clear();
        adapter.notifyDataSetChanged();
        contentLayout.notifyContent();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getP().getAttendTimeList(1,etNam.getText().toString());
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public PBaseFragment newP() {
        return new PBaseFragment();
    }

    @Override
    public void initView() {
        hideLoading();
        initAdapter();
    }

    private void initAdapter() {
        contentLayout.getRecyclerView().verticalLayoutManager(context);
        adapter = new AttendTimeAdapter(list, getContext());
        contentLayout.getRecyclerView()
                .setAdapter(adapter);
        //条目点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AttendTime recruit = (AttendTime) adapter.getData().get(position);
                Router.newIntent(getActivity())
                        .to(SignActivity.class)
                        .putString("roomId", recruit.getTime() + "")
                        .launch();

            }

        });

        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {


                return false;
            }
        });

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                switch (view.getId()) {
                    case R.id.iv_grab:
                       /* new XPopup.Builder(getContext())
//                         .dismissOnTouchOutside(false)
//                         .autoDismiss(false)
//                        .popupAnimation(PopupAnimation.NoAnimation)
                                .setPopupCallback(new SimpleCallback() {
                                    @Override
                                    public void onCreated() {

                                    }

                                    @Override
                                    public void onShow() {

                                    }

                                    @Override
                                    public void onDismiss() {

                                    }

                                    //如果你自己想拦截返回按键事件，则重写这个方法，返回true即可
                                    @Override
                                    public boolean onBackPressed() {

                                        return true;
                                    }
                                }).asConfirm("提示", "确认抢单吗?",
                                "取消", "确定",
                                new OnConfirmListener() {
                                    @Override
                                    public void onConfirm() {
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Order order = new Order();
                                                order.goodsId = goods.goodsId;
                                                getP().grabSheetOrder(order);
                                            }
                                        },2000);

                                    }
                                }, null, false)
                                .show();

                        break;*/
                }
            }
        });
        contentLayout.getRecyclerView()
                .setOnRefreshAndLoadMoreListener(new XRecyclerView.OnRefreshAndLoadMoreListener() {
                    @Override
                    public void onRefresh() {

                        getP().getAttendTimeList(1,etNam.getText().toString());
                    }

                    @Override
                    public void onLoadMore(int page) {
                        getP().getAttendTimeList(page,etNam.getText().toString());
                    }
                });
        contentLayout.getRecyclerView().useDefLoadMoreView();

    }


    @Override
    public void resultData(int resultCode, int page, Object o) {
        switch (resultCode) {
            case 0:
                Datas datas = (Datas) o;
                contentLayout.getRecyclerView().setPage(page, datas.maxPizes);
                if (page == 1) {
                    list.clear();
                    list.addAll(datas.data);
                    adapter.setNewData(list);
                    if (adapter.getItemCount() < 1) {
                        contentLayout.notifyContent();
                        return;
                    }
                } else {
                    list.addAll(datas.data);
                    adapter.notifyDataSetChanged();
                }
                break;
            case 1:

                break;


        }
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        getP().getAttendTimeList(1,etNam.getText().toString());
    }


}
