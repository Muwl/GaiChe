package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.RegisterState;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.ShareDataTool;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by Administrator on 2016/4/19.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;
    private ImageView back;
    private EditText phoneView;
    private EditText codeView;
    private TextView getCodeView;
    private EditText gcView;
    private EditText pwdView;
    private EditText repwdView;
    private CheckBox checkBox;
    private TextView submit;
    private View pro;
    private String sphone;
    private TimeCount time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        time = new TimeCount(60000, 1000);
        title= (TextView) findViewById(R.id.title_text);
        back= (ImageView) findViewById(R.id.title_back);
        phoneView= (EditText) findViewById(R.id.regiter_phone);
        codeView= (EditText) findViewById(R.id.regiter_code);
        getCodeView= (TextView) findViewById(R.id.regiter_getcode);
        gcView= (EditText) findViewById(R.id.regiter_gc);
        pwdView= (EditText) findViewById(R.id.regiter_pwd);
        repwdView= (EditText) findViewById(R.id.regiter_repwd);
        checkBox= (CheckBox) findViewById(R.id.regiter_radio);
        submit= (TextView) findViewById(R.id.regiter_ok);
        pro=  findViewById(R.id.regiter_pro);

        title.setText("手机快速注册");
        back.setOnClickListener(this);
        getCodeView.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.regiter_getcode:
                sendMessage();

                break;
            case R.id.regiter_ok:
                if (checkInput()){
                    sendSub();
                }

                break;
        }
    }

    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            getCodeView.setText("发送验证码");
            getCodeView.setClickable(true);
            getCodeView.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            getCodeView.setClickable(false);
            getCodeView.setEnabled(false);
            getCodeView.setText(millisUntilFinished / 1000 + "S");
        }
    }


    /**
     * 注册输入
     *
     * @return
     */
    private boolean checkInput() {
//        String temp = codeView.getText().toString().trim();
        if (!sphone.equals(ToosUtils.getTextContent(phoneView))) {
            ToastUtils.displayShortToast(RegisterActivity.this, "请输入正确的验证码");
            return false;
        }
        if (ToosUtils.isTextEmpty(phoneView)) {
            ToastUtils.displayShortToast(this, "手机号不能为空！");
            return false;
        }
        if (!ToosUtils.MatchPhone(ToosUtils.getTextContent(phoneView))) {
            ToastUtils.displayShortToast(this, "输入的手机号格式错误！");
            return false;
        }
        if (ToosUtils.isTextEmpty(pwdView)) {
            ToastUtils.displayShortToast(this, "密码不能为空！");
            return false;
        }
        if (!ToosUtils.checkPwd(ToosUtils.getTextContent(pwdView))) {
            ToastUtils.displayShortToast(this, "密码不能少于6位！");
            return false;
        }
        if (!ToosUtils.getTextContent(pwdView).equals(ToosUtils.getTextContent(repwdView))) {
            ToastUtils.displayShortToast(this, "两次输入的密码不一致！");
            return false;
        }
        return true;

    }

    // 发送短信
    private void sendMessage() {
        if (ToosUtils.isTextEmpty(phoneView)) {
            ToastUtils.displayShortToast(this, "手机号不能为空！");
            return ;
        }
        if (!ToosUtils.MatchPhone(ToosUtils.getTextContent(phoneView))) {
            ToastUtils.displayShortToast(this, "输入的手机号格式错误！");
            return ;
        }
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("mobile", ToosUtils.getTextContent(phoneView));
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
                        ToastUtils.displayFailureToast(RegisterActivity.this);
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
                                        RegisterActivity.this, "发送成功！");
                                sphone = ToosUtils.getTextContent(phoneView);
                                time.start();
                            } else {
                                ToastUtils.displayShortToast(
                                        RegisterActivity.this,
                                        (String) state.result);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils
                                    .displaySendFailureToast(RegisterActivity.this);
                        }

                    }

                });

    }


    /**
     * 联网注册
     */
    private void sendSub() {
        RequestParams rp = new RequestParams();
        Gson gson = new Gson();
        rp.addBodyParameter("username",ToosUtils.getTextContent(phoneView));
        rp.addBodyParameter("password",ToosUtils.getTextContent(pwdView));
        rp.addBodyParameter("authCode",ToosUtils.getTextContent(codeView));
        if (!ToosUtils.isTextEmpty(gcView)){
            rp.addBodyParameter("recommendGc", ToosUtils.getTextContent(gcView));
        }
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH + "user/register",
                rp, new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                         pro.setVisibility(View.VISIBLE);
                        super.onStart();
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        pro.setVisibility(View.GONE);
                        ToastUtils.displayFailureToast(RegisterActivity.this);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        pro.setVisibility(View.GONE);
                        try {
                            Gson gson = new Gson();
                            LogManager.LogShow("----", arg0.result,
                                    LogManager.ERROR);
                            ReturnState state = gson.fromJson(arg0.result,
                                    ReturnState.class);
                            if (Constant.RETURN_OK.equals(state.msg)) {
                                RegisterState registerState=gson.fromJson(arg0.result,RegisterState.class);
                                registerState.result.phone=ToosUtils.getTextContent(phoneView);
                                ShareDataTool.SaveInfo(RegisterActivity.this,registerState.result);
                                Intent intent=new Intent();
                                setResult(RESULT_OK,intent);
                                finish();
                            } else {
                                ToastUtils.displayShortToast(
                                        RegisterActivity.this,
                                        (String) state.result);
                            }
                        } catch (Exception e) {
                            ToastUtils
                                    .displaySendFailureToast(RegisterActivity.this);
                        }

                    }
                });

    }
}
