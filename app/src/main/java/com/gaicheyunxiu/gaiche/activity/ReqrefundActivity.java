package com.gaicheyunxiu.gaiche.activity;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.ReqrefundAdapter;
import com.gaicheyunxiu.gaiche.dialog.CustomeDialog;
import com.gaicheyunxiu.gaiche.dialog.PhotoDialog;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.DensityUtil;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.ShareDataTool;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.gaicheyunxiu.gaiche.view.MyGridView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/30.
 * 申请退货
 */
public class ReqrefundActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;

    private TextView title;

    private TextView money;

    private EditText reason;

    private EditText info;

    private MyGridView gridView;

    private TextView ok;

    private ReqrefundAdapter adapter;

    private View pro;

    private int width;

    private String orderId;

    private String commodityId;

    private double smoney;

    private List<File> files;

    public final int PHOTO_PICKED_WITH_DATA = 3021;

    public final int CAMERA_WITH_DATA = 3023;

    public final int CONTENT_WITH_DATA = 3024;

    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";

    private File tempFile;

    private Bitmap bitmap = null;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 40:
                    int position = msg.arg1;
                    files.remove(position);
                    adapter.notifyDataSetChanged();
                    break;
                case 82:
                    // 激活系统图库，选择一张图片
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

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reqrefund);
        width = DensityUtil.getScreenWidth(this);
        orderId = getIntent().getStringExtra("orderId");
        commodityId = getIntent().getStringExtra("commodityId");
        smoney = getIntent().getDoubleExtra("money",0);
        files = new ArrayList<>();
        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.title_back);
        title = (TextView) findViewById(R.id.title_text);
        money = (TextView) findViewById(R.id.reqrefund_money);
        money.setText("￥" + smoney);
        reason = (EditText) findViewById(R.id.reqrefund_reason);
        info = (EditText) findViewById(R.id.reqrefund_info);
        gridView = (MyGridView) findViewById(R.id.reqrefund_grid);
        ok = (TextView) findViewById(R.id.reqrefund_ok);
        pro = findViewById(R.id.reqrefund_pro);
        adapter = new ReqrefundAdapter(this, files, width);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == files.size()) {
                    PhotoDialog dialog = new PhotoDialog(ReqrefundActivity.this,
                            handler);
                }
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                if (position < files.size()) {
                    CustomeDialog dialog = new CustomeDialog(ReqrefundActivity.this,
                            handler, "确定要删除该照片？", position, position);
                }
                return true;
            }
        });

        back.setOnClickListener(this);
        title.setText("申请退货");
        ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;

            case R.id.reqrefund_ok:
                if (ToosUtils.isTextEmpty(reason)) {
                    ToastUtils.displayShortToast(ReqrefundActivity.this, "请填写退货原因");
                    return;
                }
                applyRefund();
                break;
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 0)
            return;
        switch (requestCode) {
            case CONTENT_WITH_DATA:
                try {
                    if (data != null) {
                        int degree = readPictureDegree(getRealPathFromURI(data
                                .getData()));
                        BitmapFactory.Options opts = new BitmapFactory.Options();// 获取缩略图显示到屏幕上
                        opts.inSampleSize = 2;
                        Bitmap cbitmap = BitmapFactory.decodeFile(
                                getRealPathFromURI(data.getData()), opts);
                        Bitmap newbitmap = rotaingImageView(degree, cbitmap);

                        // 得到图片的全路径
                        File file = ToosUtils.compressBmpToFile(newbitmap);
                        files.add(file);
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                }
                break;
            case CAMERA_WITH_DATA:
                if (ToosUtils.hasSdcard()) {
                    tempFile = new File(Environment.getExternalStorageDirectory(),
                            PHOTO_FILE_NAME);
                    int degree = readPictureDegree(tempFile.getAbsolutePath());
                    BitmapFactory.Options opts = new BitmapFactory.Options();// 获取缩略图显示到屏幕上
                    opts.inSampleSize = 2;
                    Bitmap cbitmap = BitmapFactory.decodeFile(
                            tempFile.getAbsolutePath(), opts);
                    Bitmap newbitmap = rotaingImageView(degree, cbitmap);
                    File file = ToosUtils.compressBmpToFile(newbitmap);
                    files.add(file);
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtils.displayShortToast(ReqrefundActivity.this,
                            "未找到存储卡，无法存储照片！");
                }
                break;
        }

    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /*
     * 旋转图片
     *
     * @param angle
     *
     * @param bitmap
     *
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        ;
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }


    /**
     * 通过Uri获取地址
     *
     * @param contentUri
     * @return
     */
    public String getRealPathFromURI(Uri contentUri) {
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            String result = null;

            CursorLoader cursorLoader = new CursorLoader(this, contentUri,
                    proj, null, null, null);
            Cursor cursor = cursorLoader.loadInBackground();

            if (cursor != null) {
                int column_index = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                result = cursor.getString(column_index);
                return result;
            }
        } catch (Exception o) {

        }
        return null;

    }

    /**
     * 申请退货
     */
    private void applyRefund() {
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));

        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("orderId",orderId);
        jsonObject.addProperty("commodityId",commodityId);
        jsonObject.addProperty("reason",ToosUtils.getTextContent(reason));
        jsonObject.addProperty("description",ToosUtils.getTextContent(info));
        jsonObject.addProperty("refundFee",smoney);

        rp.addBodyParameter("applyRefundStr", jsonObject.toString());

        for (int i=0;i<files.size();i++){
            rp.addBodyParameter("icon"+i,files.get(i));
        }

        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "commodityOrder/applyRefund", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(ReqrefundActivity.this);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                pro.setVisibility(View.GONE);
                try {
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        LogManager.LogShow("-----", arg0.result,
                                LogManager.ERROR);
                        ToastUtils.displayShortToast(ReqrefundActivity.this, "退货申请成功");
                        finish();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(ReqrefundActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(ReqrefundActivity.this);
                    } else {
                        ToastUtils.displayShortToast(ReqrefundActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(ReqrefundActivity.this);
                }

            }
        });
    }
}
