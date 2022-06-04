package cn.projects.team.demo.ui;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.demo.R;
import cn.droidlover.xrecyclerview.XRecyclerContentLayout;
import cn.droidlover.xrecyclerview.XRecyclerView;
import cn.projects.team.demo.adapter.NoticeAdapter;
import cn.projects.team.demo.adapter.SignAdapter;
import cn.projects.team.demo.model.Attendance;
import cn.projects.team.demo.model.Datas;
import cn.projects.team.demo.present.PBase;

public class MyAttendanceActivity extends BaseActivity<PBase> {
    private List<Attendance> list = new ArrayList<>();
    private SignAdapter adapter;
    @BindView(R.id.xRecycler)
    XRecyclerContentLayout contentLayout;

    @Override
    public void getNetData() {

    }

    @Override
    public void notifyClearUI() {
        adapter.notifyDataSetChanged();
        contentLayout.notifyContent();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getP().getMessageList(1);
    }

    private void initAdapter() {
        contentLayout.getRecyclerView().verticalLayoutManager(context);
        adapter = new SignAdapter(list,1);
        contentLayout.getRecyclerView()
                .setAdapter(adapter);
        //条目点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {


            }

        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {


            }
        });
        contentLayout.getRecyclerView()
                .setOnRefreshAndLoadMoreListener(new XRecyclerView.OnRefreshAndLoadMoreListener() {
                    @Override
                    public void onRefresh() {

                        getP().getMessageList(1);
                    }

                    @Override
                    public void onLoadMore(int page) {
                        getP().getMessageList(page);
                    }
                });
        contentLayout.getRecyclerView().useDefLoadMoreView();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_attendance;
    }

    @Override
    public PBase newP() {
        return new PBase();
    }

    @Override
    public void initView() {
        initAdapter();
        setTitlebarText("我的考勤记录");
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
}
