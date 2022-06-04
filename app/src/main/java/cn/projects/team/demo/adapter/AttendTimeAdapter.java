package cn.projects.team.demo.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;
import java.util.Random;

import cn.droidlover.xdroidmvp.demo.R;
import cn.projects.team.demo.model.AttendTime;

public class AttendTimeAdapter extends BaseQuickAdapter<AttendTime, BaseViewHolder> {
    private  Context context;
    private  int[]  a = new int[]{R.color.blue_btn_text_press,R.color.focus_text_color,R.color.vip_button_bg_color,R.color.jmui_jpush_blue,R.color.yellow_30};
    public AttendTimeAdapter(@Nullable List< AttendTime> data, Context context) {
        super(R.layout.adapter_movie,data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper,  AttendTime item) {
        try {
            helper.setText(R.id.time,item.getStartTime().substring(5,10));
            helper.setBackgroundRes(R.id.time,a[new Random().nextInt(a.length)]);
            helper.setText(R.id.mark,item.getMark());
            helper.setText(R.id.name,item.getAddress());
            helper.setText(R.id.address,item.getStartTime()+" - "+item.getEndTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


 }



