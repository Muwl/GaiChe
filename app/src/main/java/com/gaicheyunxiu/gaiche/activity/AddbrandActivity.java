package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;

/**
 * Created by Mu on 2016/1/7.
 * 添加银行卡
 */
public class AddbrandActivity extends BaseActivity implements View.OnClickListener{

    private TextView title;

    private ImageView back;

    private EditText brandName;

    private EditText brandNo;

    private EditText name;

    private EditText idno;

    private EditText phone;

    private CheckBox cb;

    private TextView ok;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbrand);
        initView();
    }

    private void initView() {
        title= (TextView) findViewById(R.id.title_text);
        back= (ImageView) findViewById(R.id.title_back);
        brandName= (EditText) findViewById(R.id.addbrand_brandname);
        brandNo= (EditText) findViewById(R.id.addbrand_brandno);
        name= (EditText) findViewById(R.id.addbrand_name);
        idno= (EditText) findViewById(R.id.addbrand_certno);
        phone= (EditText) findViewById(R.id.addbrand_phone);
        cb= (CheckBox) findViewById(R.id.addbrand_cb);
        ok = (TextView) findViewById(R.id.addbrand_ok);

        title.setText("添加银行卡");
        back.setOnClickListener(this);
        ok.setOnClickListener(this);
        cb.setText(Html.fromHtml("同意<font color=\"#566892\">《用户协议》</font>"));
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.addbrand_ok:
                finish();
                break;
        }
    }
}
