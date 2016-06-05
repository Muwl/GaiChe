package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.model.ReturnState;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;
import com.gaicheyunxiu.gaiche.utils.MD5Util;
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
 * Created by Mu on 2016/1/7.
 * 添加银行卡验证页面
 */
public class AddbrandcheckActivity extends BaseActivity implements View.OnClickListener{

    private TextView title;

    private ImageView back;

    private EditText pwd;

    private TextView forgetpwd;

    private TextView ok;

    private View pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbrand_check);
        initView();
    }

    private void initView() {
        title= (TextView) findViewById(R.id.title_text);
        back= (ImageView) findViewById(R.id.title_back);
        pwd= (EditText) findViewById(R.id.addbrand_check_pwd);
        forgetpwd= (TextView) findViewById(R.id.addbrand_check_forgetpwd);
        ok= (TextView) findViewById(R.id.addbrand_check_ok);
        pro= findViewById(R.id.addbrand_check_pro);

        title.setText("添加银行卡");
        back.setOnClickListener(this);
        ok.setOnClickListener(this);
        forgetpwd.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.addbrand_check_forgetpwd:
                Intent intent=new Intent(AddbrandcheckActivity.this, PaymentPwdActivity.class);
                startActivity(intent);
                break;
            case R.id.addbrand_check_ok:
                if (ToosUtils.isTextEmpty(pwd)){
                    ToastUtils.displayShortToast(AddbrandcheckActivity.this,"请输入支付密码！");
                    return;
                }
//                Intent intent1=new Intent(AddbrandcheckActivity.this,AddbrandActivity.class);
//                startActivityForResult(intent1, 15576);
                checkPwd();
                break;
        }
    }


    /**
     * 验证支付密码
     */
    private void checkPwd() {
        RequestParams rp = new RequestParams();
        rp.addBodyParameter("sign", ShareDataTool.getToken(this));
        rp.addBodyParameter("payPwd",MD5Util.MD5(ToosUtils.getTextContent(pwd)));
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(20000);
        LogManager.LogShow("-----", Constant.ROOT_PATH + "user/authentication?sign="+ShareDataTool.getToken(this)+"&payPwd="+ ToosUtils.getTextContent(pwd),
                LogManager.ERROR);
        utils.send(HttpRequest.HttpMethod.POST, Constant.ROOT_PATH
                + "user/authentication", rp, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                pro.setVisibility(View.VISIBLE);
                super.onStart();
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                pro.setVisibility(View.GONE);
                ToastUtils.displayFailureToast(AddbrandcheckActivity.this);
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
                        ToastUtils.displayShortToast(AddbrandcheckActivity.this, "验证成功");
                        Intent intent1=new Intent(AddbrandcheckActivity.this,AddbrandActivity.class);
                        startActivityForResult(intent1, 15576);
                    } else if (Constant.TOKEN_ERR.equals(state.msg)) {
                        ToastUtils.displayShortToast(AddbrandcheckActivity.this,
                                "验证错误，请重新登录");
                        ToosUtils.goReLogin(AddbrandcheckActivity.this);
                    } else {
                        ToastUtils.displayShortToast(AddbrandcheckActivity.this,
                                (String) state.result);
                    }
                } catch (Exception e) {
                    ToastUtils.displaySendFailureToast(AddbrandcheckActivity.this);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode!=RESULT_OK){
            return;
        }
        if (requestCode==15576){
            Intent intent=new Intent();
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}
