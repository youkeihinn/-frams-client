package cn.projects.team.demo.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.droidlover.xdroidmvp.demo.R;
import cn.projects.team.demo.model.Leave;

public class NoticeAdapter extends BaseQuickAdapter<Leave, BaseViewHolder> {
    public NoticeAdapter(@Nullable List<Leave> data) {
        super(R.layout.adapter_notice,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Leave item) {
        helper.setText(R.id.tv_time,""+item.getStartTime());
        helper.setText(R.id.tv_mark,"回复："+item.getMark());
        helper.setText(R.id.tv_reason,"理由："+item.getReason());
        if(item.getStatus() == 0){
            helper.setText(R.id.tv_status,"待审核");

        }else if(item.getStatus() == 1){
            helper.setText(R.id.tv_status,"已通过");
        }else if(item.getStatus() == 2){
            helper.setText(R.id.tv_status,"未通过");

        }

    }
}
