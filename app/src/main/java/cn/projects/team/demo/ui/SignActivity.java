package cn.projects.team.demo.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.droidlover.xdroidmvp.demo.R;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xrecyclerview.XRecyclerContentLayout;
import cn.droidlover.xrecyclerview.XRecyclerView;
import cn.projects.team.demo.adapter.SignAdapter;
import cn.projects.team.demo.model.AttendTime;
import cn.projects.team.demo.model.Attendance;
import cn.projects.team.demo.model.BaseModel;
import cn.projects.team.demo.net.Api;
import cn.projects.team.demo.present.PBase;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class SignActivity extends BaseActivity<PBase> implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;
    @BindView(R.id.xRecycler)
    XRecyclerContentLayout contentLayout;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.sx)
    TextView sx;
    @BindView(R.id.shangban_clock)
    TextView shangbanClock;
    @BindView(R.id.shangban_dakaBtn)
    LinearLayout shangbanDakaBtn;
    private static final int msgKey1 = 1;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.mark)
    TextView mark;
    @BindView(R.id.address)
    TextView address1;


    private String address;
    public LocationClient mLocationClient;
    private MyLocationListener mMyLocationListener;
    private List<Attendance> list = new ArrayList<>();
    private SignAdapter adapter;
    private Double longitude;
    private Double latitude;
    private String roomId;

    @Override
    public void getNetData() {

    }

    @Override
    public void notifyClearUI() {
        //list.clear();
        adapter.notifyDataSetChanged();
        contentLayout.notifyContent();
    }

    private void initLocation() {
        mLocationClient.stop();
        mLocationClient.start();
        requestPermission();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case msgKey1:
                    try {
                        long time = System.currentTimeMillis();
                        Date date = new Date(time);
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                        shangbanClock.setText(format.format(date));

                    } catch (Exception e) {

                    }

                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public class TimeThread extends Thread {
        @Override
        public void run() {
            super.run();
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = msgKey1;
                    mHandler.sendMessage(msg);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while (true);
        }
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        getP().getSignList(roomId);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sign;
    }

    @Override
    public PBase newP() {
        return new PBase();
    }

    @Override
    public void initView() {
        setTitlebarText("考勤签到");
        roomId = getIntent().getStringExtra("roomId");
        hideLoading();
        initAdapter();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        shangbanClock.setText(format.format(date));
        new TimeThread().start();
        mLocationClient = new LocationClient(SignActivity.this);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
        option.setNeedDeviceDirect(true);// 设置返回结果包含手机的方向
        option.setOpenGps(true);
        option.setAddrType("all");// 返回的定位结果包含地址信息
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
        option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
        option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mLocationClient.setLocOption(option);
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        initLocation();
    }

    private void initAdapter() {
        contentLayout.getRecyclerView().verticalLayoutManager(context);
        adapter = new SignAdapter(list,0);
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

                switch (view.getId()) {
                    case R.id.iv_grab:
                        break;
                }
            }
        });
        contentLayout.getRecyclerView()
                .setOnRefreshAndLoadMoreListener(new XRecyclerView.OnRefreshAndLoadMoreListener() {
                    @Override
                    public void onRefresh() {

                        getP().getSignList(roomId);
                    }

                    @Override
                    public void onLoadMore(int page) {
                        // getP().getSignList(signTime.getText().toString(),endTime.getText().toString());
                    }
                });
        contentLayout.getRecyclerView().useDefLoadMoreView();

    }

    @Override
    public void resultData(int resultCode, int page, Object o) {
        switch (resultCode) {
            case 0:
                AttendTime signData = (AttendTime) o;
                 name.setText(signData.getAddress());
                 mark.setText(signData.getMark());
                 address1.setText(signData.getStartTime()+" - " +signData.getEndTime());
                list.clear();
                list.addAll(signData.getList());

                adapter.setNewData(list);
                break;
            case 1:
                getvDelegate().toastLong("打卡成功");
                getP().getSignList(roomId);
                break;


        }
    }




    @OnClick(R.id.shangban_dakaBtn)
    public void onClick() {
        final AlertDialog alertDialog = new AlertDialog.Builder(SignActivity.this).setTitle("确定签到打卡吗")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (null != longitude) {
                            choicePhotoWrapper();
                           
                          /*  sign();
                            Router.newIntent(SignActivity.this)
                                    //  .to(ActivityScanerCode.class)
                                    .requestCode(1000)
                                    .launch();*/
                        } else
                            Toast.makeText(SignActivity.this, "正在定位中，请稍后", Toast.LENGTH_SHORT).show();
                        // Toast.makeText(getContext(), "打卡成功", Toast.LENGTH_SHORT).show();

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setTextColor(Color.GREEN);
                Button button1 = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                button1.setTextColor(Color.RED);
            }
        });
        alertDialog.show();

    }


    /**
     * 实现实位回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.e("info", "city = " + location.getCity());
            //获取定位结果

            if (location.getCity() == null) {


            } else {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                address = location.getLocationDescribe() + "(" + location.getLongitude() + "," + location.getLatitude() + ")";
                tvAdd.setText("当前定位:" + location.getLocationDescribe() + "(" + location.getLongitude() + "," + location.getLatitude() + ")");


            }

        }

    }

    private static final int REQUEST_CODE_ACCESS_COARSE_LOCATION = 1;

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission() {
        //Android 6.0判断用户是否授予定位权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//如果 API level 是大于等于 23(Android 6.0) 时
            //判断是否具有权限
            if (ContextCompat.checkSelfPermission(SignActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //判断是否需要向用户解释为什么需要申请该权限
                if (ActivityCompat.shouldShowRequestPermissionRationale(SignActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    Toast.makeText(SignActivity.this, "自Android 6.0开始需要打开位置权限", Toast.LENGTH_SHORT).show();
                }
                //请求权限
                ActivityCompat.requestPermissions(SignActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_CODE_ACCESS_COARSE_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_ACCESS_COARSE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //用户允许改权限，0表示允许，-1表示拒绝 PERMISSION_GRANTED = 0， PERMISSION_DENIED = -1
                //permission was granted, yay! Do the contacts-related task you need to do.
                //这里进行授权被允许的处理
            } else {
                //permission denied, boo! Disable the functionality that depends on this permission.
                //这里进行权限被拒绝的处理
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private void sign(String pic) {
        Attendance sign = new Attendance();
        sign.setAddress(address);
        sign.setTimeId(Long.parseLong(roomId));
        sign.setPic(pic);
        getP().saveSign(sign);

    }


    private static final int RC_CHOOSE_PHOTO = 1;
    private static final int RC_PHOTO_PREVIEW = 2;
    private static final int PRC_PHOTO_PICKER = 1;

    @AfterPermissionGranted(PRC_PHOTO_PICKER)
    private void choicePhotoWrapper() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerTakePhoto");

            Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(this)
                    .cameraFileDir(takePhotoDir) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                    .maxChooseCount(1) // 图片选择张数的最大值
                    .selectedPhotos(null) // 当前已选中的图片路径集合
                    .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                    .build();
            startActivityForResult(photoPickerIntent, RC_CHOOSE_PHOTO);
        } else {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", PRC_PHOTO_PICKER, perms);
        }
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == PRC_PHOTO_PICKER) {
            Toast.makeText(this, "您拒绝了「图片选择」所需要的相关权限!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RC_CHOOSE_PHOTO) {
            upload(BGAPhotoPickerActivity.getSelectedPhotos(data));
        } else if (requestCode == RC_PHOTO_PREVIEW) {
            upload(BGAPhotoPickerActivity.getSelectedPhotos(data));
        }
    }


    private List<String> pics = new ArrayList<>();

    private void upload(ArrayList<String> selectedPhotos) {
        List<File> list = new ArrayList<>();
        pics.clear();
        for (String path : selectedPhotos) {
            File file = new File(path);
            list.add(file);
        }
        HashMap<String, RequestBody> map = new HashMap<>();
        MultipartBody.Part[] parts = new MultipartBody.Part[list.size()];
        for (File file : list) {

            map.put("uploadfile\"; filename=\"" + file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        }

        Api.getGankService().uploadFiles(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<BaseModel<List<String>>>() {
                    @Override
                    public void onNext(BaseModel<List<String>> o) {
                        //Toast.makeText(MyActivity.this, "上传成功", Toast.LENGTH_LONG).show();
                        pics = o.data;
                        sign(pics.get(0));
                    }

                    @Override
                    protected void onFail(NetError error) {

                    }


                });


    }

}
