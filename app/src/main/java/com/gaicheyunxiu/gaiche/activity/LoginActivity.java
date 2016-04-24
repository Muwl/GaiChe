package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by Administrator on 2016/4/19.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static final int FORGETPWD_RETURN=111;
    private static final int REGISTER_RETURN=112;
    private ImageView back;
    private TextView title;
    private EditText phoneView;
    private EditText pwdView;
    private TextView okView;
    private TextView registerView;
    private TextView forgetPwdView;
    private TextView weixinView;
    private View pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        phoneView= (EditText) findViewById(R.id.login_phone);
        pwdView= (EditText) findViewById(R.id.login_pwd);
        okView= (TextView) findViewById(R.id.login_ok);
        registerView= (TextView) findViewById(R.id.login_register);
        forgetPwdView= (TextView) findViewById(R.id.login_forgetpwd);
        weixinView= (TextView) findViewById(R.id.login_weixin);
        pro=  findViewById(R.id.login_pro);

        back.setOnClickListener(this);
        title.setText("登录");
        okView.setOnClickListener(this);
        registerView.setOnClickListener(this);
        forgetPwdView.setOnClickListener(this);
        weixinView.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.login_forgetpwd:
                Intent intent=new Intent(LoginActivity.this,LoginPwdActivity.class);
                startActivityForResult(intent, FORGETPWD_RETURN);
                break;

            case R.id.login_register:
                Intent intent2=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent2,REGISTER_RETURN);
                break;
            case R.id.login_ok:
                if (checkInput()){
                    sendLog();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode!=RESULT_OK){
            return;
        }
        if (requestCode==FORGETPWD_RETURN || requestCode==REGISTER_RETURN){
            Intent intent=new Intent();
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    /**
     * 注册输入
     *
     * @return
     */
    private boolean checkInput() {
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
        return true;

    }

    /**
     * 联网注册
     */
    private void sendLog() {
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("username", ToosUtils.getTextContent(phoneView));
        rp.addBodyParameter("password",ToosUtils.getTextContent(pwdView));
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH + "user/login",
                rp, new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        pro.setVisibility(View.VISIBLE);
                        super.onStart();
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        pro.setVisibility(View.GONE);
                        ToastUtils.displayFailureToast(LoginActivity.this);
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
                                ShareDataTool.SaveInfo(LoginActivity.this, registerState.result);
                                Intent intent=new Intent();
                                setResult(RESULT_OK,intent);
                                finish();
                            } else {
                                ToastUtils.displayShortToast(
                                        LoginActivity.this,
                                        (String) state.result);
                            }
                        } catch (Exception e) {
                            ToastUtils
                                    .displaySendFailureToast(LoginActivity.this);
                        }

                    }
                });

    }
}
