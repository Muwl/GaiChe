package com.gaicheyunxiu.gaiche.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.CarUp;
import com.gaicheyunxiu.gaiche.model.MyCarEntity;
import com.gaicheyunxiu.gaiche.model.RegisterState;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.MyCarEntityUtils;
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
import com.umeng.socialize.Config;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private UMShareAPI mShareAPI;
    private MyCarEntityUtils carEntityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mShareAPI = UMShareAPI.get(this);
        carEntityUtils=new MyCarEntityUtils(this);
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

        Dialog progressDialog = new Dialog(this,R.style.progress_dialog);
        progressDialog.setContentView(R.layout.dialog);
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Config.dialog=progressDialog;




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

            case R.id.login_weixin:
                SHARE_MEDIA platform = SHARE_MEDIA.WEIXIN;
                mShareAPI.doOauthVerify(this, platform, umAuthListener);
                break;
        }
    }
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
//            ToastUtils.displayShortToast(LoginActivity.this, "认证成功！");
            LogManager.LogShow("-------------", data.toString(), LogManager.ERROR);
            sendThirdLog(data.get("access_token"));
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            ToastUtils.displayShortToast(LoginActivity.this, "认证失败！");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode==FORGETPWD_RETURN || requestCode==REGISTER_RETURN ) && resultCode==RESULT_OK){
            Intent intent=new Intent();
            setResult(RESULT_OK, intent);
            finish();
            return;
        }

        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 注册输入
     *
     * @return
     */
    private boolean checkInput() {
        if (ToosUtils.isTextEmpty(phoneView)) {
            ToastUtils.displayShortToast(this, "账号不能为空！");
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
                                updateCar();
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


    /**
     * 联网注册
     */
    private void sendThirdLog(String tokenId) {
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("username", ToosUtils.getEncrypt(tokenId));
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH + "user/thirdLogin",
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
                                updateCar();
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



    /**
     * 同步我的爱车
     */
    private void updateCar() {
        List<MyCarEntity> myCarEntities=carEntityUtils.getAllMyCar();
        if (myCarEntities==null || myCarEntities.size()==0){
            return;
        }
        List<CarUp> carUps=new ArrayList<>();
        for (int i=0;i<myCarEntities.size();i++){
            CarUp carUp=new CarUp();
            carUp.carTypeId=myCarEntities.get(i).carTypeId;
            carUp.isDefault=myCarEntities.get(i).isDefault;
            carUps.add(carUp);
        }
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("sign",ShareDataTool.getToken(this));
        rp.addBodyParameter("carTypes",new Gson().toJson(carUps));
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        LogManager.LogShow("------------------", Constant.ROOT_PATH + "myCar/sync?sign=" + ShareDataTool.getToken(this) + "&carTypes=" + new Gson().toJson(carUps), LogManager.ERROR);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH + "myCar/sync",
                rp, new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
//                        ToastUtils.displayFailureToast(LoginActivity.this);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        carEntityUtils.removeAllCar();
                        try {
                            Gson gson = new Gson();
                            LogManager.LogShow("----(((((****", arg0.result,
                                    LogManager.ERROR);
                            ReturnState state = gson.fromJson(arg0.result,
                                    ReturnState.class);
                            if (Constant.RETURN_OK.equals(state.msg)) {
//                                RegisterState registerState=gson.fromJson(arg0.result,RegisterState.class);
//                                registerState.result.phone=ToosUtils.getTextContent(phoneView);
//                                ShareDataTool.SaveInfo(LoginActivity.this, registerState.result);
//                                Intent intent=new Intent();
//                                setResult(RESULT_OK,intent);
//                                finish();
                            } else {
//                                ToastUtils.displayShortToast(
//                                        LoginActivity.this,
//                                        (String) state.result);
                            }
                        } catch (Exception e) {
//                            ToastUtils
//                                    .displaySendFailureToast(LoginActivity.this);
                        }

                    }
                });

    }

}
