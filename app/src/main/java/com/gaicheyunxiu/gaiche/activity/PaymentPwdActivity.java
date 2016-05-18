package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
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

/**
 * Created by Mu on 2015/12/22.
 * 修改支付密码
 */
public class PaymentPwdActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private TextView phone;

    private EditText code;

    private TextView getCode;

    private EditText pwd;

    private EditText rePwd;

    private TextView submit;

    private View pro;

    private TimeCount time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpwd);
        initView();

    }

    private void initView() {
        time = new TimeCount(60000, 1000);
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        phone= (TextView) findViewById(R.id.loginpwd_phone);
        code= (EditText) findViewById(R.id.loginpwd_code);
        getCode= (TextView) findViewById(R.id.loginpwd_getcode);
        pwd= (EditText) findViewById(R.id.loginpwd_pwd);
        rePwd= (EditText) findViewById(R.id.loginpwd_repwd);
        submit= (TextView) findViewById(R.id.loginpwd_ok);
        back.setOnClickListener(this);
        pro= findViewById(R.id.loginpwd_pro);
        title.setText("修改支付密码");
        getCode.setOnClickListener(this);
        submit.setOnClickListener(this);
        phone.setText(ShareDataTool.getRegiterEntity(this).mobile);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;

            case R.id.loginpwd_getcode:
                sendMessage();
                break;

            case R.id.loginpwd_ok:
                if (checkInput()){
                    UpdateUserInfo();
                }
                break;
        }
    }


    /**
     * 注册输入
     *
     * @return
     */
    private boolean checkInput() {
//        String temp = codeView.getText().toString().trim();

        if (ToosUtils.isTextEmpty(code)) {
            ToastUtils.displayShortToast(this, "验证码不能为空！");
            return false;
        }
        if (ToosUtils.isTextEmpty(pwd)) {
            ToastUtils.displayShortToast(this, "密码不能为空！");
            return false;
        }
        if (!ToosUtils.checkPwd(ToosUtils.getTextContent(pwd))) {
            ToastUtils.displayShortToast(this, "密码不能少于6位！");
            return false;
        }
        if (!ToosUtils.getTextContent(pwd).equals(ToosUtils.getTextContent(rePwd))) {
            ToastUtils.displayShortToast(this, "两次输入的密码不一致！");
            return false;
        }
        return true;

    }

    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            getCode.setText("发送验证码");
            getCode.setClickable(true);
            getCode.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            getCode.setClickable(false);
            getCode.setEnabled(false);
            getCode.setText(millisUntilFinished / 1000 + "S");
        }
    }



    // 发送短信
    private void sendMessage() {
        if (ToosUtils.isTextEmpty(phone)) {
            ToastUtils.displayShortToast(this, "手机号不能为空！");
            return ;
        }
        if (!ToosUtils.MatchPhone(ToosUtils.getTextContent(phone))) {
            ToastUtils.displayShortToast(this, "输入的手机号格式错误！");
            return ;
        }
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("mobile", ToosUtils.getTextContent(phone));
        HttpUtils utils = new HttpUtils();
        // HttpUtils  LogManager.LogShow("-----", Constant.ROOT_PATH + "sms/sendAuthCode?phone="+ ToosUtils.getTextContent(phoneView), LogManager.ERROR);
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH + "sms/sendAuthCode", rp,
                new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        pro.setVisibility(View.VISIBLE);
                        super.onStart();
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        pro.setVisibility(View.GONE);
                        ToastUtils.displayFailureToast(PaymentPwdActivity.this);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        pro.setVisibility(View.GONE);
                        Gson gson = new Gson();
                        try {
                            ReturnState state = gson.fromJson(arg0.result,
                                    ReturnState.class);
                            LogManager.LogShow("----", arg0.result,
                                    LogManager.ERROR);
                            if (Constant.RETURN_OK.equals(state.msg)) {
                                ToastUtils.displayShortToast(
                                        PaymentPwdActivity.this, "发送成功！");
                                time.start();
                            } else {
                                ToastUtils.displayShortToast(
                                        PaymentPwdActivity.this,
                                        (String) state.result);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils
                                    .displaySendFailureToast(PaymentPwdActivity.this);
                        }

                    }

                });

    }


    /**
     * 修改个人信息
     */
    private void UpdateUserInfo() {
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("username",ToosUtils.getTextContent(phone));
        rp.addBodyParameter("payPassword", ToosUtils.getTextContent(pwd));
        rp.addBodyParameter("authCode", ToosUtils.getTextContent(code));
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
//        LogManager.LogShow("-------",Constant.ROOT_PATH + "user/operationPayPwd");
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "user/operationPayPwd", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(PaymentPwdActivity.this);
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
                        ToastUtils.displayShortToast(PaymentPwdActivity.this, "修改成功");
                        Intent intent=new Intent();
                        setResult(RESULT_OK,intent);
                        finish();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(PaymentPwdActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(PaymentPwdActivity.this);
                    } else {
                        ToastUtils.displayShortToast(PaymentPwdActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(PaymentPwdActivity.this);
                }

            }
        });
    }
}
