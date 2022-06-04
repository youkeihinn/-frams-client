package cn.projects.team.demo.present;

import java.util.List;
import java.util.Map;

import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

import cn.projects.team.demo.model.AttendTime;
import cn.projects.team.demo.model.Attendance;
import cn.projects.team.demo.model.BaseModel;
import cn.projects.team.demo.model.Datas;
import cn.projects.team.demo.model.Dept;
import cn.projects.team.demo.model.Leave;
import cn.projects.team.demo.model.Login;
import cn.projects.team.demo.model.RegisterUser;
import cn.projects.team.demo.net.Api;
import okhttp3.RequestBody;

/**
 * Created by lv on 2019/7/30 for Projects-master
 */
public class PBase extends XPresent<XActivity> {

    protected static final int PAGE_SIZE = 10;



    public void login( RegisterUser registerUser) {
        Api.getGankService().login(registerUser)
                .compose(XApi.<BaseModel<Login>>getApiTransformer())
                .compose(XApi.<BaseModel<Login>>getScheduler())
                .compose(getV().<BaseModel<Login>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel<Login>>() {
                    @Override
                    protected void onFail(NetError error) {
                        //getV(). hideloadingPopup();
                        getV().resultError(0,error);
                    }
                    @Override
                    public void onNext(BaseModel<Login> results) {
                        //getV(). hideloadingPopup();
                        Login login = results.data;
                        getV().resultData(1,0,login);
                    }
                });
    }


    public void sendLeave( Leave leave) {
        Api.getGankService().sendLeave(leave)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        //getV(). hideloadingPopup();
                        getV().resultError(0,error);
                    }
                    @Override
                    public void onNext(BaseModel results) {
                        //getV(). hideloadingPopup();

                        getV().resultData(2,0,results.data);
                    }
                });
    }




    public void updateRegisterUser( RegisterUser registerUser) {
        Api.getGankService().updateRegisterUser(registerUser)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        //getV(). hideloadingPopup();
                        getV().resultError(0,error);
                    }
                    @Override
                    public void onNext(BaseModel results) {
                        //getV(). hideloadingPopup();
                        getV().resultData(1,0,results.data);
                    }
                });
    }


    public void getCourseData( String courseId) {

    }
    public void saveSign(Attendance sign ) {
        Api.getGankService().saveSign(sign)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV(). hideLoading();
                        getV().resultError(0,error);
                    }
                    @Override
                    public void onNext(BaseModel results) {
                        getV(). hideLoading();
                        getV().resultData(1,0,results.data);
                    }
                });
    }


    public void getSignList( String timeId) {
        Api.getGankService().getSignList(timeId)
                .compose(XApi.<BaseModel<AttendTime>>getApiTransformer())
                .compose(XApi.<BaseModel<AttendTime>>getScheduler())
                .compose(getV().<BaseModel<AttendTime>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel<AttendTime>>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV(). hideLoading();
                        getV().resultError(0,error);
                    }
                    @Override
                    public void onNext(BaseModel<AttendTime> results) {
                        getV(). hideLoading();
                        getV().resultData(0,0,results.data);
                    }
                });
    }



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




    public void getDeptList() {
        Api.getGankService().getDeptList()
                .compose(XApi.<BaseModel<List<Dept>>>getApiTransformer())
                .compose(XApi.<BaseModel<List<Dept>>>getScheduler())
                .compose(getV().<BaseModel<List<Dept>>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel<List<Dept>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV(). hideLoading();
                        getV().resultError(0,error);
                    }
                    @Override
                    public void onNext(BaseModel<List<Dept>> results) {
                        getV(). hideLoading();
                        getV().resultData(6,0,results.data);
                    }
                });
    }


    public void getMessageList( final int page) {
        Api.getGankService().getMessageList(PAGE_SIZE,page)
                .compose(XApi.<BaseModel<Datas<Attendance>>>getApiTransformer())
                .compose(XApi.<BaseModel<Datas<Attendance>>>getScheduler())
                .compose(getV().<BaseModel<Datas<Attendance>>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel<Datas<Attendance>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV(). hideLoading();
                        getV().resultError(0,error);
                    }
                    @Override
                    public void onNext(BaseModel<Datas<Attendance>> results) {
                        getV(). hideLoading();
                        getV().resultData(0,page,results.data);
                    }
                });
    }




    public void registerUser( RegisterUser registerUser) {
        Api.getGankService().registerUser(registerUser)
                .compose(XApi.<BaseModel<Login>>getApiTransformer())
                .compose(XApi.<BaseModel<Login>>getScheduler())
                .compose(getV().<BaseModel<Login>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel<Login>>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV(). hideloadingPopup();
                        getV().resultError(0,error);
                    }
                    @Override
                    public void onNext(BaseModel<Login> results) {
                        getV(). hideloadingPopup();
                        Login login = results.data;
                        getV().resultData(1,0,login);
                    }
                });
    }




    public void uploadFiles(Map<String, RequestBody > map) {
        Api.getGankService().uploadFiles(map)
                .compose(XApi.<BaseModel<List<String>>>getApiTransformer())
                .compose(XApi.<BaseModel<List<String>>>getScheduler())
                .compose(getV().<BaseModel<List<String>>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel<List<String>>>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV(). hideloadingPopup();
                        getV().resultError(0,error);
                    }
                    @Override
                    public void onNext(BaseModel<List<String>> results) {
                        getV(). hideloadingPopup();
                        List<String>    pics = results.data;
                        getV().resultData(1,0,pics);
                    }
                });

    }






    public void setHeadImage( RegisterUser user) {
        Api.getGankService().setHeadImage(user)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().resultError(0, error);
                    }

                    @Override
                    public void onNext(BaseModel results) {
                        getV().resultData(0, 0, results);
                    }
                });

    }

        public void setNickName(RegisterUser user) {
            Api.getGankService().setNickName(user)
                    .compose(XApi.<BaseModel>getApiTransformer())
                    .compose(XApi.<BaseModel>getScheduler())
                    .compose(getV().<BaseModel>bindToLifecycle())
                    .subscribe(new ApiSubscriber<BaseModel>() {
                        @Override
                        protected void onFail(NetError error) {
                            getV().resultError(0,error);
                        }

                        @Override
                        public void onNext(BaseModel results) {
                            getV().resultData(0,0,results);
                        }
                    });

    }

    public void setCard(RegisterUser user) {
        Api.getGankService().setCard(user)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().resultError(0,error);
                    }

                    @Override
                    public void onNext(BaseModel results) {
                        getV().resultData(0,0,results);
                    }
                });

    }

    public void setSex(RegisterUser user) {
        Api.getGankService().setSex(user)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().resultError(0,error);
                    }

                    @Override
                    public void onNext(BaseModel results) {
                        getV().resultData(1,0,results);
                    }
                });

    }


    public void reNewPass(RegisterUser user) {
        Api.getGankService().reNewPass(user)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().resultError(0,error);
                    }

                    @Override
                    public void onNext(BaseModel results) {
                        getV().resultData(2,0,results);
                    }
                });

    }





}
