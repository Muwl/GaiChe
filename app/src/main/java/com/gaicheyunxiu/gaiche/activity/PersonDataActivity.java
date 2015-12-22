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
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.gaicheyunxiu.gaiche.view.CircleImageView;


import java.io.File;

import static com.gaicheyunxiu.gaiche.R.id.persondata_nameview;

/**
 * Created by Mu on 2015/12/22.
 * 我的资料
 */
public class PersonDataActivity extends BaseActivity implements View.OnClickListener{

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

        back.setOnClickListener(this);
        title.setText("个人信息");
        iconView.setOnClickListener(this);
        nameView.setOnClickListener(this);
        phoneView.setOnClickListener(this);
        emailView.setOnClickListener(this);
        passwordView.setOnClickListener(this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 0)
            return;
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
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
                    icon.setImageBitmap(bitmap);
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
                startActivity(intent);
                break;
            case R.id.persondata_iconview:
                PhotoDialog dialog=new PhotoDialog(PersonDataActivity.this,handler);

                break;
            case R.id.persondata_phoneview:
                Intent intent2=new Intent(PersonDataActivity.this,UpdatePhoneActivity.class);
                startActivity(intent2);

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
}
