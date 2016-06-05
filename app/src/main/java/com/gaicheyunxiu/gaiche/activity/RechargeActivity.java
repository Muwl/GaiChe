package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.dialog.PaymentDialog;
import com.gaicheyunxiu.gaiche.model.PayResult;
import com.gaicheyunxiu.gaiche.model.PayState;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.MyApplication;
import com.gaicheyunxiu.gaiche.utils.ShareDataTool;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by Mu on 2016/1/7.
 * 充值页面
 */
public class RechargeActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private EditText money;

    private View zhifubao;

    private CheckBox zhifubaocb;

    private View weixin;

    private CheckBox weixincb;

    private View yinlian;

    private CheckBox yinliancb;

    private TextView ok;

    private IWXAPI api;

    private View pro;

    public static final int SDK_PAY_FLAG = 4462;

    private int checkIndex=1;//1支付宝 2 微信 3银联


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){

                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Intent intent=new Intent(RechargeActivity.this,RechargeSucActivity.class);
                        startActivity(intent);
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(RechargeActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(RechargeActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID);
        api.registerApp(Constant.APP_ID);
        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        money= (EditText) findViewById(R.id.recharge_money);
        zhifubao=findViewById(R.id.recharge_zhifubao);
        zhifubaocb= (CheckBox) findViewById(R.id.recharge_zhifubao_cb);
        weixin=findViewById(R.id.recharge_weixin);
        weixincb= (CheckBox) findViewById(R.id.recharge_weixin_cb);
        yinlian=findViewById(R.id.recharge_yinlian);
        yinliancb= (CheckBox) findViewById(R.id.recharge_yinlian_cb);
        ok= (TextView) findViewById(R.id.recharge_ok);
        pro= findViewById(R.id.recharge_pro);

        title.setText("付款");
        back.setOnClickListener(this);
        zhifubao.setOnClickListener(this);
        weixin.setOnClickListener(this);
        yinlian.setOnClickListener(this);
        ok.setOnClickListener(this);


        zhifubaocb.setChecked(true);
        weixincb.setChecked(false);
        yinliancb.setChecked(false);
        checkIndex=1;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;

            case R.id.recharge_zhifubao:
                zhifubaocb.setChecked(true);
                weixincb.setChecked(false);
                yinliancb.setChecked(false);
                checkIndex=1;

                break;

            case R.id.recharge_weixin:
                zhifubaocb.setChecked(false);
                weixincb.setChecked(true);
                yinliancb.setChecked(false);
                checkIndex=2;
                break;

            case R.id.recharge_yinlian:
                zhifubaocb.setChecked(false);
                weixincb.setChecked(false);
                yinliancb.setChecked(true);
                checkIndex=3;
                break;

            case R.id.recharge_ok:
                if (ToosUtils.isTextEmpty(money)){
                    ToastUtils.displayShortToast(RechargeActivity.this,"请输入充值金额");
                    return;
                }
                try{
                    if (Integer.valueOf(String.valueOf(ToosUtils.getTextContent(money)))>0){

                    }else{
                        ToastUtils.displayShortToast(RechargeActivity.this,"请输入正确的充值金额");
                        return;
                    }
                }catch (Exception e){
                    ToastUtils.displayShortToast(RechargeActivity.this,"请输入正确的充值金额");
                    return;
                }
                getCharge();
//                Intent intent=new Intent(RechargeActivity.this,RechargeSucActivity.class);
//                startActivity(intent);
                break;
        }
    }


    /**
     * 获取支付签名
     */
    private void getCharge() {
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        String url="user/recharge";
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("payType", String.valueOf(checkIndex));
        rp.addBodyParameter("money", ToosUtils.getTextContent(money));
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + url, rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(RechargeActivity.this);
                pro.setVisibility(View.GONE);
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
                        PayState payState=gson.fromJson(arg0.result,PayState.class);
                        if (checkIndex==0){
                            Intent intent=new Intent(RechargeActivity.this,RechargeSucActivity.class);
                            startActivity(intent);
                        }else if(checkIndex==1){
                            zhifubaoPay(ToosUtils.getEncryptto(payState.result.content));
                        }else if(checkIndex==2){
                            MyApplication.getInstance().setPayFlag(1);
                            MyApplication.getInstance().setWeixinmoney(ToosUtils.getTextContent(money));
                            PayReq request = new PayReq();
                            request.appId = payState.result.appid;
                            request.partnerId =  payState.result.parentId;
                            request.prepayId= ToosUtils.getEncryptto(payState.result.content);
                            request.packageValue = "Sign=WXPay";
                            request.nonceStr= payState.result.noncestr;
                            request.timeStamp= payState.result.timestamp;
                            request.sign= payState.result.paySign;
                            api.sendReq(request);
                            LogManager.LogShow("-----", ToosUtils.getEncryptto(payState.result.content),
                                    LogManager.ERROR);
                        }

                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(RechargeActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(RechargeActivity.this);
                    } else {
                        ToastUtils.displayShortToast(RechargeActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(RechargeActivity.this);
                }

            }
        });
    }


    private void zhifubaoPay(final String sign){
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(RechargeActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(sign, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
