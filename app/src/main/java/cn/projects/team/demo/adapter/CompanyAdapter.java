package cn.projects.team.demo.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.droidlover.xdroidmvp.demo.R;
import cn.droidlover.xdroidmvp.imageloader.ILFactory;
import cn.projects.team.demo.model.UserDto;

public class CompanyAdapter extends BaseQuickAdapter<UserDto, BaseViewHolder> {
    public CompanyAdapter(@Nullable List<UserDto> data) {
        super(R.layout.adapter_record,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserDto item) {
        ILFactory.getLoader().loadNet( helper.getView(R.id.pic),item.getAvatar(), null);
        helper.setText(R.id.companyName,item.getName());

    }
}
