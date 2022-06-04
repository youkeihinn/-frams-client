package cn.projects.team.demo.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.demo.R;
import cn.droidlover.xdroidmvp.imageloader.ILFactory;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.projects.team.demo.model.BaseModel;
import cn.projects.team.demo.model.Dept;
import cn.projects.team.demo.model.RegisterUser;
import cn.projects.team.demo.net.Api;
import cn.projects.team.demo.present.PBase;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class PersonDataActivity extends BaseActivity<PBase> implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.icon)
    ImageView icon;

    private static final int PRC_PHOTO_PICKER = 1;
    @BindView(R.id.root_notice)
    LinearLayout rootNotice;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.nickName)
    EditText nickName;
    @BindView(R.id.root_out)
    LinearLayout rootOut;
    @BindView(R.id.address)
    TextView address;

    @BindView(R.id.tel)
    EditText tel;
    @BindView(R.id.no)
    EditText no;
    private List<Dept> list = new ArrayList<>();
    private List<String> sexList1 = new ArrayList<>();
    private List<String> sexList = new ArrayList<>();
    private static final int RC_CHOOSE_PHOTO = 1;
    private static final int RC_PHOTO_PREVIEW = 2;
    private int options1;

    @Override
    public void getNetData() {

    }

    @Override
    public void notifyClearUI() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
            getP().getDeptList();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_person_datas;
    }

    @Override
    public PBase newP() {
        return new PBase();
    }

    @Override
    public void initView() {
        setTitlebarText("个人信息");
        hideLoading();
        ILFactory.getLoader().loadCircle(SharedPref.getInstance(getApplicationContext()).getString("head", ""), icon, null);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getP().getPersonalData();
            }
        }, 1000);
    }

    @Override
    public void resultData(int resultCode, int page, Object o) {
        switch (resultCode) {
            case 0:
                ILFactory.getLoader().loadCircle(SharedPref.getInstance(getApplicationContext()).getString("head", ""), icon, null);
                getvDelegate().toastLong("上传成功");
                break;
            case 1:
                getvDelegate().toastLong("修改成功");
                finish();
                break;

            case 3:
                RegisterUser user = (RegisterUser) o;
                ILFactory.getLoader().loadCircle(user.headImage, icon, null);
                sex.setText(user.sex);
                nickName.setText(user.name);
                no.setText(user.no);
                address.setText(user.deptName);
                address.setTag(user.adrress);
                tel.setText(user.tel);
                pics.clear();
                pics.add(user.headImage);
                break;
            case 6:
                sexList1.clear();
                list = (List<Dept>) o;
                for (Dept dept:list) {
                    sexList1.add(dept.getName());
                }
                break;

        }

    }


    @OnClick({R.id.icon, R.id.sex, R.id.submit,R.id.address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon:
                choicePhotoWrapper();
                break;
            case  R.id.address:
                //条件选择器
                OptionsPickerView pvOptions1 = new OptionsPickerBuilder(PersonDataActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        address.setText(sexList1.get(options1));
                        address.setTag(list.get(options1).getDeptId());
                    }
                }).build();
                pvOptions1.setPicker(sexList1);
                pvOptions1.setTitleText("部门");
                pvOptions1.show();

                break;


            case R.id.sex:
                sexList.clear();
                sexList.add("男");
                sexList.add("女");
                //条件选择器
                OptionsPickerView pvOptions = new OptionsPickerBuilder(PersonDataActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        PersonDataActivity.this.options1 = options1;

                        sex.setText(sexList.get(options1));

                    }
                }).build();
                pvOptions.setPicker(sexList);
                pvOptions.setTitleText("请选择性别");
                pvOptions.show();
                break;
            case R.id.submit:
                String s = sex.getText().toString();
                String s1 = nickName.getText().toString();
                String s2 = address.getText().toString();
                String s3 = no.getText().toString();

                String s5 = tel.getText().toString();

                if (TextUtils.isEmpty(s)) {
                    getvDelegate().toastLong("请选择性别");
                    return;
                }
                if (TextUtils.isEmpty(s1)) {
                    getvDelegate().toastLong("请输入姓名");
                    return;
                }


                RegisterUser registerUser = new RegisterUser();
                registerUser.name = s1;
                registerUser.sex = s;
                registerUser.tel = s5;
                registerUser.headImage = pics.get(0);
                registerUser.no = s3;
                registerUser.adrress = address.getTag()+"";
                getP().updateRegisterUser(registerUser);

                break;


        }

    }

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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
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
        } else if (requestCode == 1000 && resultCode == 2000) {
            nickName.setText(SharedPref.getInstance(PersonDataActivity.this).getString("nickName", ""));
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

        Api.getGankService().uploadPic(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<BaseModel<List<String>>>() {
                    @Override
                    public void onNext(BaseModel<List<String>> o) {
                        //Toast.makeText(MyActivity.this, "上传成功", Toast.LENGTH_LONG).show();
                        pics = o.data;
                        RegisterUser user = new RegisterUser();
                        user.headImage = pics.get(0);
                        SharedPref.getInstance(getApplicationContext()).putString("head", user.headImage);
                        ILFactory.getLoader().loadCircle(SharedPref.getInstance(getApplicationContext()).getString("head", ""), icon, null);
                    }

                    @Override
                    protected void onFail(NetError error) {

                    }


                });


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
