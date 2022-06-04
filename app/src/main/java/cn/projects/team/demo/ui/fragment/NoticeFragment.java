package cn.projects.team.demo.ui.fragment;

import android.os.Bundle;
import android.view.View;

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
import cn.projects.team.demo.adapter.NoticeAdapter;
import cn.projects.team.demo.model.Datas;
import cn.projects.team.demo.model.Leave;
import cn.projects.team.demo.present.PBaseFragment;
import cn.projects.team.demo.ui.CreatLeaveActivity;

public class NoticeFragment extends BaseLazyFragment<PBaseFragment> {


    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;
    @BindView(R.id.xRecycler)
    XRecyclerContentLayout contentLayout;
    private List<Leave> list = new ArrayList<>();
    private NoticeAdapter adapter;
    public static NoticeFragment newInstance() {
        Bundle args = new Bundle();
        NoticeFragment fragment = new NoticeFragment();
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
        getP().getNoticeList(1);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_notice;
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
        adapter = new NoticeAdapter(list);
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

                        getP().getNoticeList(1);
                    }

                    @Override
                    public void onLoadMore(int page) {
                        getP().getNoticeList(page);
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

    @OnClick(R.id.fab_button_group)
    public void onFabClick() {
        Router.newIntent(context)
                .to(CreatLeaveActivity.class)
                .launch();
    }


}