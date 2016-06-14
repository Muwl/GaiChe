package com.gaicheyunxiu.gaiche.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.security.mobile.module.commonutils.LOG;
import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.activity.PaySuccessActivity;
import com.gaicheyunxiu.gaiche.activity.PaymentPwdActivity;
import com.gaicheyunxiu.gaiche.model.PayEntity;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.MD5Util;
import com.gaicheyunxiu.gaiche.utils.ShareDataTool;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.gaicheyunxiu.gaiche.utils.WalletPay;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Mu
 * @date 2015-3-6
 * @description 支付对话框
 */
public class PaymentQRDialog extends Dialog implements
        View.OnClickListener {
    private Context context;
    private ImageView close;
    private TextView money;
    private EditText pwd;
    private TextView name;
    private TextView forgetpwd;
    private TextView ok;
    private View pro;
    private String smoney;
    private String shopId;
    private String payInfo;


    public PaymentQRDialog(Context context, String shopId, String smoney, String payInfo) {
        super(context, R.style.dialog2);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.context = context;
        this.shopId = shopId;
        this.payInfo = payInfo;
        this.smoney = smoney;
        setContentView(R.layout.pay_dialog);
        getWindow().setBackgroundDrawable(new BitmapDrawable());
        show();
        initView();

    }

    private void initView() {
        close = (ImageView) findViewById(R.id.dialog_pay_close);
        money = (TextView) findViewById(R.id.dialog_pay_money);
        pwd = (EditText) findViewById(R.id.dialog_pay_pwd);
        forgetpwd = (TextView) findViewById(R.id.dialog_forget_pwd);
        name = (TextView) findViewById(R.id.dialog_pay_name);
        ok = (TextView) findViewById(R.id.dialog_pay_ok);
        pro = findViewById(R.id.dialog_pay_pro);
        money.setText("￥"+smoney);
        ok.setOnClickListener(this);
        close.setOnClickListener(this);
        forgetpwd.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_pay_close:
                dismiss();
                break;
            case R.id.dialog_pay_ok:

                walletPay();
//
                break;
            case R.id.dialog_forget_pwd:
                dismiss();
                Intent intent = new Intent(context, PaymentPwdActivity.class);
                context.startActivity(intent);
                break;
            default:
                break;
        }

    }

    /**
     * 钱包支付
     */
    private void walletPay() {
        if (ToosUtils.isTextEmpty(pwd)) {
            ToastUtils.displayShortToast(context, "请输入密码");
            return;
        }
        RequestParams rp = new RequestParams();
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        String url = "user/pay";

        long timeStamp= System.currentTimeMillis();
        String nonce= WalletPay.getNonceStr();
        Map<String,String> map=new HashMap<>();
        int monfen=(int)(Double.valueOf(smoney)*10);
        map.put("shopId",shopId);
        map.put("money", String.valueOf(monfen));
        map.put("payPwd", MD5Util.MD5(ToosUtils.getTextContent(pwd)));
        map.put("payDescribe", payInfo);
        String paySign=WalletPay.generateSign(map,timeStamp,nonce);

        rp.addBodyParameter("sign", ShareDataTool.getToken(context));
        rp.addBodyParameter("shopId", shopId);
        rp.addBodyParameter("money", String.valueOf(monfen));
        rp.addBodyParameter("payPwd",MD5Util.MD5(ToosUtils.getTextContent(pwd)));
        rp.addBodyParameter("payDescribe",payInfo);
        rp.addBodyParameter("timeStamp", String.valueOf(timeStamp));
        rp.addBodyParameter("nonce", nonce);
        rp.addBodyParameter("paySign", paySign);

        LogManager.LogShow("--------------------------",Constant.ROOT_PATH+url+"?sign="+ShareDataTool.getToken(context)+"&shopId="+shopId+"&money="+monfen+"&payPwd="+MD5Util.MD5(ToosUtils.getTextContent(pwd))+"&payDescribe="+payInfo+"&timeStamp="+String.valueOf(timeStamp)+"&nonce="+nonce+"&paySign="+paySign,LogManager.ERROR);


        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH+url, rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
//				pro.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                ToastUtils.displayFailureToast(context);
//				pro.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
//				pro.setVisibility(View.GONE);
                try {
                    Gson gson = new Gson();
                    ReturnState state = gson.fromJson(arg0.result,
                            ReturnState.class);
                    if (Constant.RETURN_OK.equals(state.msg)) {
                        LogManager.LogShow("-----", arg0.result,
                                LogManager.ERROR);
                        dismiss();
                        Intent intent1 = new Intent(context, PaySuccessActivity.class);
                        intent1.putExtra("money", smoney);
                        context.startActivity(intent1);
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(context,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(context);
                    } else {
                        ToastUtils.displayShortToast(context,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.displaySendFailureToast(context);
                }

            }
        });
    }

}
