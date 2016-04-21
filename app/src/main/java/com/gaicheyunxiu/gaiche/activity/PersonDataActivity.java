package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.dialog.PhotoDialog;
import com.gaicheyunxiu.gaiche.model.PersonDataEntity;
import com.gaicheyunxiu.gaiche.model.PersonDynamicEntity;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.ShareDataTool;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.gaicheyunxiu.gaiche.view.CircleImageView;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;


import java.io.File;

import static com.gaicheyunxiu.gaiche.R.id.persondata_nameview;

/**
 * Created by Mu on 2015/12/22.
 * 我的资料
 */
public class PersonDataActivity extends BaseActivity implements View.OnClickListener{

    public static  final  int NAME_RESULT=5556;
    public static  final  int PHONE_RESULT=5557;

    private ImageView back;

    private TextView title;

    private View iconView;

    private CircleImageView icon;

    private TextView no;

    private TextView name;

    private View nameView;

    private TextView phone;

    private View  phoneView;

    private TextView email;

    private View emailView;

    private View passwordView;

    private File tempFile;

    public final int PHOTO_PICKED_WITH_DATA = 3021;

    public final int CAMERA_WITH_DATA = 3023;

    public final int CONTENT_WITH_DATA = 3024;

    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";

    private Bitmap bitmap = null;

    private BitmapUtils bitmapUtils;

    private View gv;

    private View pro;




    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 82:
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, CONTENT_WITH_DATA);
                    break;
                case 81:
                    Intent intent2 = new Intent(
                            "android.media.action.IMAGE_CAPTURE");
                    // 判断存储卡是否可以用，可用进行存储
                    if (ToosUtils.hasSdcard()) {
                        intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                                .fromFile(new File(Environment
                                        .getExternalStorageDirectory(),
                                        PHOTO_FILE_NAME)));
                    }
                    startActivityForResult(intent2, CAMERA_WITH_DATA);
                    break;
                default:
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persondata);
        initView();
    }

    private void initView() {
        bitmapUtils=new BitmapUtils(this);
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        icon= (CircleImageView) findViewById(R.id.persondata_icon);
        iconView=findViewById(R.id.persondata_iconview);
        name= (TextView) findViewById(R.id.persondata_name);
        nameView=findViewById(persondata_nameview);
        no = (TextView) findViewById(R.id.persondata_no);
        phone= (TextView) findViewById(R.id.persondata_phone);
        phoneView=findViewById(R.id.persondata_phoneview);
        email= (TextView) findViewById(R.id.persondata_email);
        emailView=findViewById(R.id.persondata_emailview);
        passwordView=findViewById(R.id.persondata_passwordview);
        pro=findViewById(R.id.persondata_pro);
        gv=findViewById(R.id.persondata_gv);

        back.setOnClickListener(this);
        title.setText("个人信息");
        iconView.setOnClickListener(this);
        nameView.setOnClickListener(this);
        phoneView.setOnClickListener(this);
        emailView.setOnClickListener(this);
        passwordView.setOnClickListener(this);
        getUserInfo();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 0)
            return;
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case NAME_RESULT:
                    String userName=data.getStringExtra("name");
                    name.setText(userName);
                    break;
                case PHONE_RESULT:
                    String phoneStr=data.getStringExtra("phone");
                    name.setText(phoneStr);
                    break;

                case CONTENT_WITH_DATA:
                    if (data != null) {
                        // 得到图片的全路径
                        Uri uri = data.getData();
                        crop(uri);
                    }
                    break;
                case CAMERA_WITH_DATA:
                    if (ToosUtils.hasSdcard()) {
                        tempFile = new File(
                                Environment.getExternalStorageDirectory(),
                                PHOTO_FILE_NAME);
                        crop(Uri.fromFile(tempFile));
                    } else {
                        ToastUtils.displayShortToast(PersonDataActivity.this,
                                "未找到存储卡，无法存储照片！");
                    }
                    break;
                case PHOTO_PICKED_WITH_DATA:
                    bitmap = data.getParcelableExtra("data");
                    if (bitmap != null) {
                        File file = null;
                        if (Environment.getExternalStorageState().equals(
                                android.os.Environment.MEDIA_MOUNTED)) {

                            file = ToosUtils.saveImage2SD(
                                    Environment.getExternalStorageDirectory() + "/gaiche/"
                                            + String.valueOf(System.currentTimeMillis())
                                            + ".JPEG", bitmap);
                            UpdateUserInfo(file);
                        } else {
                            ToastUtils.displayShortToast(PersonDataActivity.this,
                                    "无SD卡,无法上传图片");
                            return;
                        }

                    }
                    break;
            }

        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.persondata_nameview:
                Intent intent=new Intent(PersonDataActivity.this,PersonnameActivity.class);
                intent.putExtra("name",ToosUtils.getTextContent(name));
                startActivityForResult(intent,NAME_RESULT);
                break;
            case R.id.persondata_iconview:
                PhotoDialog dialog=new PhotoDialog(PersonDataActivity.this,handler);

                break;
            case R.id.persondata_phoneview:
                Intent intent2=new Intent(PersonDataActivity.this,UpdatePhoneActivity.class);
                intent2.putExtra("phone",ToosUtils.getTextContent(phone));
                startActivityForResult(intent2, PHONE_RESULT);
                break;
            case R.id.persondata_emailview:
                Intent intent3=new Intent(PersonDataActivity.this,PersonemailActivity.class);
                startActivity(intent3);
                break;
            case R.id.persondata_passwordview:
                Intent intent4=new Intent(PersonDataActivity.this,PasswordActivity.class);
                startActivity(intent4);
                break;
        }
    }




    /**
     * 剪切图片
     *
     * @function:
     * @author:Jerry
     * @date:2013-12-30
     * @param uri
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
    }



    /**
     * 获取我的动态数
     */
    private void getUserInfo() {
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "user/findUserInfo", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                gv.setVisibility(View.GONE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                gv.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(PersonDataActivity.this);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                pro.setVisibility(View.GONE);
                try {
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        gv.setVisibility(View.GONE);
                        LogManager.LogShow("-----", arg0.result,
                                LogManager.ERROR);
                        PersonDataEntity personDataEntity = gson.fromJson(arg0.result, PersonDataEntity.class);
                        setValue(personDataEntity.result);

                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(PersonDataActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(PersonDataActivity.this);
                    } else {
                        ToastUtils.displayShortToast(PersonDataActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(PersonDataActivity.this);
                }

            }
        });
    }

    private void setValue(PersonDataEntity.PersonData personData){
        bitmapUtils.display(icon,personData.icon);
        no.setText(personData.gcCode);
        name.setText(personData.nickname);
        phone.setText(personData.mobile);
        email.setText(personData.email);

    }


    /**
     * 修改个人信息
     */
    private void UpdateUserInfo(final File file) {
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("icon", file);
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "user/update", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(PersonDataActivity.this);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                try {
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        LogManager.LogShow("-----", arg0.result,
                                LogManager.ERROR);
                        bitmapUtils.display(icon,file.getAbsolutePath());
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(PersonDataActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(PersonDataActivity.this);
                    } else {
                        ToastUtils.displayShortToast(PersonDataActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(PersonDataActivity.this);
                }

            }
        });
    }
}
