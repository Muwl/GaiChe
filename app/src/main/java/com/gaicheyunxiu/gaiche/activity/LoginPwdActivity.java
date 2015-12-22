package com.gaicheyunxiu.gaiche.activity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;

import org.w3c.dom.Text;

/**
 * Created by Mu on 2015/12/22.
 * 修改登陆密码
 */
public class LoginPwdActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private TextView phone;

    private EditText code;

    private TextView getCode;

    private EditText pwd;

    private EditText rePwd;

    private TextView submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpwd);
        initView();

    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        phone= (TextView) findViewById(R.id.loginpwd_phone);
        code= (EditText) findViewById(R.id.loginpwd_code);
        getCode= (TextView) findViewById(R.id.loginpwd_getcode);
        pwd= (EditText) findViewById(R.id.loginpwd_pwd);
        rePwd= (EditText) findViewById(R.id.loginpwd_repwd);
        submit= (TextView) findViewById(R.id.loginpwd_ok);
        back.setOnClickListener(this);
        title.setText("修改登录密码");
        getCode.setOnClickListener(this);
        submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;

            case R.id.loginpwd_getcode:
                break;

            case R.id.loginpwd_ok:
                finish();
                break;
        }
    }
}
