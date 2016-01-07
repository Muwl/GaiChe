package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;

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

        title.setText("添加银行卡");
        back.setOnClickListener(this);
        ok.setOnClickListener(this);

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
                Intent intent1=new Intent(AddbrandcheckActivity.this,AddbrandActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
