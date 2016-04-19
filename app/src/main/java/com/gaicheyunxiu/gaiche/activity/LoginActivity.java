package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;

/**
 * Created by Administrator on 2016/4/19.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

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
                startActivity(intent);
                break;

            case R.id.login_register:
                Intent intent2=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
