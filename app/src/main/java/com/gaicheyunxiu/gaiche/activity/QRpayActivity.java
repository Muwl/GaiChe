package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.dialog.PaymentDialog;
import com.gaicheyunxiu.gaiche.dialog.PaymentQRDialog;
import com.gaicheyunxiu.gaiche.model.AdState;
import com.gaicheyunxiu.gaiche.model.QREntity;
import com.gaicheyunxiu.gaiche.model.QRState;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.ShareDataTool;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.gaicheyunxiu.gaiche.view.RoundAngleImageView;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by Administrator on 2016/3/13.
 * 扫码支付
 */
public class QRpayActivity extends BaseActivity implements View.OnClickListener{

    private static final int  LOGIN_RES=1662;
    private static final int  PWD_RES=1663;

    private ImageView back;

    private TextView title;

    private RoundAngleImageView imageView;

    private TextView name;

    private EditText money;

    private EditText info;

    private TextView pay;

    private View pro;

    private String gcNo;

    private BitmapUtils bitmapUtils;

    private QREntity qrEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrpay);
        initView();
        if (ToosUtils.isStringEmpty(ShareDataTool.getToken(this))){
            Intent intent=new Intent(QRpayActivity.this,LoginActivity.class);
            startActivityForResult(intent,LOGIN_RES);
        }else{
            getShopInfo();
        }
    }

    private void initView() {
        gcNo=getIntent().getStringExtra("gc");
        title= (TextView) findViewById(R.id.title_text);
        back= (ImageView) findViewById(R.id.title_back);
        imageView= (RoundAngleImageView) findViewById(R.id.qrpay_iamge);
        name= (TextView) findViewById(R.id.qrpay_name);
        money= (EditText) findViewById(R.id.qrpay_money);
        info= (EditText) findViewById(R.id.qrpay_info);
        pay= (TextView) findViewById(R.id.qrpay_pay);
        pro= findViewById(R.id.qrpay_pro);
        title.setText("扫码支付");
        back.setOnClickListener(this);
        pay.setOnClickListener(this);
        bitmapUtils=new BitmapUtils(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;

            case R.id.qrpay_pay:
                if (ToosUtils.isTextEmpty(money) || Double.valueOf(ToosUtils.getTextContent(money))<=0){
                    ToastUtils.displayShortToast(QRpayActivity.this,"请输入支付金额");
                    return;
                }
                if (ToosUtils.isTextEmpty(info)){
                    ToastUtils.displayShortToast(QRpayActivity.this,"请输入支付说明");
                    return;
                }

                PaymentQRDialog dialog=new PaymentQRDialog(QRpayActivity.this,qrEntity.id,ToosUtils.getTextContent(money),ToosUtils.getTextContent(info));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode!=RESULT_OK){
            finish();
        }
        if (requestCode==LOGIN_RES){
            getShopInfo();
        }
    }

    /**
     * 获取广告
     */
    private void getShopInfo() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("gcCode",gcNo);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "user/shopInfo", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(QRpayActivity.this);
                pro.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                try {
                    pro.setVisibility(View.GONE);
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        LogManager.LogShow("-----", arg0.result,
                                LogManager.ERROR);
                        QRState qrState=gson.fromJson(arg0.result,QRState.class);
                        qrEntity=qrState.result;
                        bitmapUtils.display(imageView,qrEntity.icon);
                        name.setText(qrEntity.name);
                        if ("0".equals(qrEntity.isHavePayPwd)){
                            Intent intent=new Intent(QRpayActivity.this,PaymentPwdActivity.class);
                            intent.putExtra("flag",1);
                            intent.putExtra("phone",qrEntity.isHaveMobile);
                            startActivityForResult(intent,PWD_RES);
                        }
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(QRpayActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(QRpayActivity.this);
                    } else {
                        ToastUtils.displayShortToast(QRpayActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(QRpayActivity.this);
                }

            }
        });
    }
}
