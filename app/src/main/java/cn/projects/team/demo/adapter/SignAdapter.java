package cn.projects.team.demo.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.droidlover.xdroidmvp.demo.R;
import cn.projects.team.demo.model.Attendance;

public class SignAdapter extends BaseQuickAdapter<Attendance, BaseViewHolder> {
    private  Context context;
    int type ;
    public SignAdapter(@Nullable List<Attendance> data,int type) {
        super(R.layout.adapter_sign,data);
        this.type =type  ;
    }

    @Override
    protected void convert(BaseViewHolder helper, Attendance item) {
        helper.setText(R.id.time,item.getTime());
        helper.setText(R.id.address,item.getAddress());
        helper.setText(R.id.type,item.getType());

        if(type==0){
            helper.setText(R.id.userName,"第"+(helper.getLayoutPosition()+1)+"名    "+item.getName());
        }else{
            helper.setText(R.id.userName,item.getName());
        }
    }
    }


