package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.utils.ToosUtils;

/**
 * Created by Administrator on 2015/12/28.
 * 支付成功页面
 */
public class PaySuccessActivity extends  BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private TextView money;

    private TextView ok;

    private String smoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        money= (TextView) findViewById(R.id.pay_success_money);
        ok= (TextView) findViewById(R.id.pay_success_ok);

        smoney=getIntent().getStringExtra("money");
        if (!ToosUtils.isStringEmpty(smoney)){
            money.setText("￥"+smoney);
        }


        back.setOnClickListener(this);
        title.setText("去结算");
        ok.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                Intent intent=new Intent(PaySuccessActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.pay_success_ok:
                Intent intent1=new Intent(PaySuccessActivity.this,MainActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
