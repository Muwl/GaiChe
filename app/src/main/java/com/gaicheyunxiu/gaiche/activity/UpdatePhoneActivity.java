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

import java.io.File;

/**
 * Created by Mu on 2015/12/22.]
 * 修改手机号
 */
public class UpdatePhoneActivity extends  BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private TextView oldPhone;

    private EditText oldcode;

    private EditText newPhone;

    private EditText newCode;

    private TextView getOldCode;

    private TextView getNewCode;

    private TextView submit;

    private String oldPhoneStr;

    private View pro;

    private TimeCount time;

    private TimeCount2 time2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatephone);
        oldPhoneStr=getIntent().getStringExtra("phone");
        initView();
    }

    private void initView() {
        time = new TimeCount(60000, 1000);
        time2 = new TimeCount2(60000, 1000);
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        oldPhone= (TextView) findViewById(R.id.updatephone_oldphone);
        oldcode= (EditText) findViewById(R.id.updatephone_oldcode);
        getOldCode= (TextView) findViewById(R.id.updatephone_getoldcode);
        newPhone= (EditText) findViewById(R.id.updatephone_newphone);
        newCode= (EditText) findViewById(R.id.updatephone_newcode);
        getNewCode= (TextView) findViewById(R.id.updatephone_getnewcode);
        submit= (TextView) findViewById(R.id.updatephone_ok);
        back.setOnClickListener(this);
        title.setText("修改手机号");
        getNewCode.setOnClickListener(this);
        getOldCode.setOnClickListener(this);
        submit.setOnClickListener(this);

        oldPhone.setText(oldPhoneStr);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.updatephone_getoldcode:
                sendMessage();
                break;

            case R.id.updatephone_getnewcode:
                sendMessage2();
                break;

            case R.id.updatephone_ok:
                if (checkInput()){
                    UpdateUserInfo();
                }
//                finish();
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

        if (ToosUtils.isTextEmpty(oldPhone)) {
            ToastUtils.displayShortToast(this, "旧手机号不能为空！");
            return false;
        }
        if (!ToosUtils.MatchPhone(ToosUtils.getTextContent(oldPhone))) {
            ToastUtils.displayShortToast(this, "输入的旧手机号格式错误！");
            return false;
        }
        if (ToosUtils.isTextEmpty(oldcode)) {
            ToastUtils.displayShortToast(this, "验证码不能为空！");
            return false;
        }
        if (ToosUtils.isTextEmpty(newPhone)) {
            ToastUtils.displayShortToast(this, "新手机号不能为空！");
            return false;
        }
        if (!ToosUtils.MatchPhone(ToosUtils.getTextContent(newPhone))) {
            ToastUtils.displayShortToast(this, "输入的新手机号格式错误！");
            return false;
        }
        if (ToosUtils.isTextEmpty(newCode)) {
            ToastUtils.displayShortToast(this, "验证码不能为空！");
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
            getOldCode.setText("发送验证码");
            getOldCode.setClickable(true);
            getOldCode.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            getOldCode.setClickable(false);
            getOldCode.setEnabled(false);
            getOldCode.setText(millisUntilFinished / 1000 + "S");
        }
    }


    /* 定义一个倒计时的内部类 */
    class TimeCount2 extends CountDownTimer {
        public TimeCount2(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            getNewCode.setText("发送验证码");
            getNewCode.setClickable(true);
            getNewCode.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            getNewCode.setClickable(false);
            getNewCode.setEnabled(false);
            getNewCode.setText(millisUntilFinished / 1000 + "S");
        }
    }



    // 发送短信
    private void sendMessage() {
        if (ToosUtils.isTextEmpty(oldPhone)) {
            ToastUtils.displayShortToast(this, "手机号不能为空！");
            return ;
        }
        if (!ToosUtils.MatchPhone(ToosUtils.getTextContent(oldPhone))) {
            ToastUtils.displayShortToast(this, "输入的手机号格式错误！");
            return ;
        }
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("mobile", ToosUtils.getTextContent(oldPhone));
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
                        ToastUtils.displayFailureToast(UpdatePhoneActivity.this);
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
                                        UpdatePhoneActivity.this, "发送成功！");
                                time.start();
                            } else {
                                ToastUtils.displayShortToast(
                                        UpdatePhoneActivity.this,
                                        (String) state.result);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils
                                    .displaySendFailureToast(UpdatePhoneActivity.this);
                        }

                    }

                });

    }


    // 发送短信
    private void sendMessage2() {
        if (ToosUtils.isTextEmpty(newPhone)) {
            ToastUtils.displayShortToast(this, "手机号不能为空！");
            return ;
        }
        if (!ToosUtils.MatchPhone(ToosUtils.getTextContent(newPhone))) {
            ToastUtils.displayShortToast(this, "输入的手机号格式错误！");
            return ;
        }
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("mobile", ToosUtils.getTextContent(newPhone));
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
                        ToastUtils.displayFailureToast(UpdatePhoneActivity.this);
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
                                        UpdatePhoneActivity.this, "发送成功！");
                                time2.start();
                            } else {
                                ToastUtils.displayShortToast(
                                        UpdatePhoneActivity.this,
                                        (String) state.result);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils
                                    .displaySendFailureToast(UpdatePhoneActivity.this);
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
        rp.addBodyParameter("oldMobile", ToosUtils.getTextContent(oldPhone));
        rp.addBodyParameter("oldAuthCode", ToosUtils.getTextContent(oldcode));
        rp.addBodyParameter("newMobile", ToosUtils.getTextContent(newPhone));
        rp.addBodyParameter("newAuthCode", ToosUtils.getTextContent(newCode));
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
                ToastUtils.displayFailureToast(UpdatePhoneActivity.this);
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
                        Intent intent=new Intent();
                        intent.putExtra("phone",ToosUtils.getTextContent(newPhone));
                        setResult(RESULT_OK,intent);
                        finish();
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(UpdatePhoneActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(UpdatePhoneActivity.this);
                    } else {
                        ToastUtils.displayShortToast(UpdatePhoneActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(UpdatePhoneActivity.this);
                }

            }
        });
    }
}
