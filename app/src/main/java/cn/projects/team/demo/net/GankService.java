package cn.projects.team.demo.net;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.projects.team.demo.model.AttendTime;
import cn.projects.team.demo.model.Attendance;
import cn.projects.team.demo.model.BaseModel;

import cn.projects.team.demo.model.Datas;
import cn.projects.team.demo.model.Dept;
import cn.projects.team.demo.model.Leave;
import cn.projects.team.demo.model.Login;
import cn.projects.team.demo.model.RegisterUser;
import cn.projects.team.demo.model.UserDto;
import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 *
 */

public interface GankService {

    @POST("loginUser")
    Flowable<BaseModel<Login>> login(@Body RegisterUser registerUser);


    @POST("updateRegisterUser")
    Flowable<BaseModel> updateRegisterUser(@Body RegisterUser registerUser);



    @POST("sendLeave")
    Flowable<BaseModel> sendLeave(@Body Leave leave);



    @POST("registerUser")
    Flowable<BaseModel<Login>> registerUser(@Body RegisterUser registerUser);
    @Multipart
    @POST("upload")
    Flowable<BaseModel<List<String>>> uploadFiles(@PartMap() Map<String, RequestBody> maps);
    @Multipart
    @POST("uploadPic")
    Flowable<BaseModel<List<String>>> uploadPic(@PartMap() Map<String, RequestBody> maps);

    @GET("getPersonalData")
    Flowable<BaseModel<RegisterUser>> getPersonalData();
    @GET("getSignList")
    Flowable<BaseModel<AttendTime>> getSignList(@Query("timeId") String timeId);



    @POST("saveSign")
    Flowable<BaseModel> saveSign(@Body Attendance sign);

    @GET("getMyAttendance")
    Flowable<BaseModel<Datas<Attendance>>> getMessageList(@Query("limit") int limit, @Query("page") int page);

    @GET("getDeptList")
    Flowable<BaseModel<List<Dept>>> getDeptList();


    @POST("setHeadImage")
    Flowable<BaseModel> setHeadImage(@Body RegisterUser user);
    @POST("setNickName")
    Flowable<BaseModel> setNickName(@Body RegisterUser user);

    @POST("setCard")
    Flowable<BaseModel> setCard(@Body RegisterUser user);


    @POST("setSex")
    Flowable<BaseModel> setSex(@Body RegisterUser user);
    @POST("reNewPass")
    Flowable<BaseModel> reNewPass(@Body RegisterUser user);


    @GET("getAttendTimeList")
    Flowable<BaseModel<Datas<AttendTime>>> getAttendTimeList(@Query("limit") int limit, @Query("page") int page, @Query("name") String name);

    @GET("getCompanyList")
    Flowable<BaseModel<Datas<UserDto>>> getCompanyList(@Query("limit") int limit, @Query("page") int page,@Query("name")String name);

    @GET("getLeaveList")
    Flowable<BaseModel<Datas<Leave>>> getNoticeList(@Query("limit") int limit, @Query("page") int page);
    @GET("bindUserTeam")
    Flowable<BaseModel>bindUserTeam(@Query("code") String code);

    @GET("recharge")
    Flowable<BaseModel>recharge(@Query("cardNo") String cardNo);




    @GET("getTodayPrice")
    Flowable<BaseModel<BigDecimal>>getTodayPrice(@Query("time")String time);

    @GET("getTodayPriceProfit")
    Flowable<BaseModel<BigDecimal>>getTodayPriceProfit(@Query("time")String time);

}
