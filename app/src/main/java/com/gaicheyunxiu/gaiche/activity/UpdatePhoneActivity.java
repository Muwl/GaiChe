package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatephone);
        initView();
    }

    private void initView() {
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.updatephone_getoldcode:
                break;

            case R.id.updatephone_getnewcode:
                break;

            case R.id.updatephone_ok:
                finish();
                break;
        }
    }
}
