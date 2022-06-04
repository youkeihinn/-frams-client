package cn.projects.team.demo.present;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.projects.team.demo.model.AttendTime;
import cn.projects.team.demo.model.BaseModel;
import cn.projects.team.demo.model.Datas;
import cn.projects.team.demo.model.Leave;
import cn.projects.team.demo.model.RegisterUser;
import cn.projects.team.demo.model.UserDto;
import cn.projects.team.demo.net.Api;
import cn.projects.team.demo.ui.fragment.BaseLazyFragment;

public class PBaseFragment extends XPresent<BaseLazyFragment> {
    protected static final int PAGE_SIZE = 10;


    public void getPersonalData( ) {
        Api.getGankService().getPersonalData()
                .compose(XApi.<BaseModel<RegisterUser>>getApiTransformer())
                .compose(XApi.<BaseModel<RegisterUser>>getScheduler())
                .compose(getV().<BaseModel<RegisterUser>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel<RegisterUser>>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV(). hideLoading();
                        getV().resultError(0,error);
                    }
                    @Override
                    public void onNext(BaseModel<RegisterUser> results) {
                        getV(). hideLoading();
                        getV().resultData(3,0,results.data);
                    }
                });
    }


    public void getCompanyList( final int page,String name) {
        Api.getGankService().getCompanyList(PAGE_SIZE,page,name)
                .compose(XApi.<BaseModel<Datas<UserDto>>>getApiTransformer())
                .compose(XApi.<BaseModel<Datas<UserDto>>>getScheduler())
                .compose(getV().<BaseModel<Datas<UserDto>>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel<Datas<UserDto>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV(). hideLoading();
                        getV().resultError(0,error);
                    }
                    @Override
                    public void onNext(BaseModel<Datas<UserDto>> results) {
                        getV(). hideLoading();
                        getV().resultData(0,page,results.data);
                    }
                });
    }


    public void getNoticeList( final int page) {
        Api.getGankService().getNoticeList(PAGE_SIZE,page)
                .compose(XApi.<BaseModel<Datas<Leave>>>getApiTransformer())
                .compose(XApi.<BaseModel<Datas<Leave>>>getScheduler())
                .compose(getV().<BaseModel<Datas<Leave>>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel<Datas<Leave>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV(). hideLoading();
                        getV().resultError(0,error);
                    }
                    @Override
                    public void onNext(BaseModel<Datas<Leave>> results) {
                        getV(). hideLoading();
                        getV().resultData(0,page,results.data);
                    }
                });
    }


    public void getAttendTimeList( final int page,String name) {
        Api.getGankService().getAttendTimeList(PAGE_SIZE,page,name)
                .compose(XApi.<BaseModel<Datas<AttendTime>>>getApiTransformer())
                .compose(XApi.<BaseModel<Datas<AttendTime>>>getScheduler())
                .compose(getV().<BaseModel<Datas<AttendTime>>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel<Datas<AttendTime>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV(). hideLoading();
                        getV().resultError(0,error);
                    }
                    @Override
                    public void onNext(BaseModel<Datas<AttendTime>> results) {
                        getV(). hideLoading();
                        getV().resultData(0,page,results.data);
                    }
                });
    }






    public void getExitCardList( final int page) {

    }




}
