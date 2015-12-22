package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;

/**
 * Created by Mu on 2015/12/22.
 * 密码管理页面
 */
public class PasswordActivity extends  BaseActivity implements View.OnClickListener {

    private ImageView back;

    private TextView title;

    private View loginpwd;

    private View paypwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        initView();

    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        loginpwd=findViewById(R.id.password_loginpwd);
        paypwd=findViewById(R.id.password_paypwd);
        title.setText("密码管理");
        back.setOnClickListener(this);
        loginpwd.setOnClickListener(this);
        paypwd.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.password_loginpwd:
                Intent intent=new Intent(PasswordActivity.this,LoginPwdActivity.class);
                startActivity(intent);
                break;
            case  R.id.password_paypwd:
                Intent intent1=new Intent(PasswordActivity.this,PayPwdActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
