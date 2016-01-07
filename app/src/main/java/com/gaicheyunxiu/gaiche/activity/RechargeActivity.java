package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.dialog.PaymentDialog;

/**
 * Created by Mu on 2016/1/7.
 * 充值页面
 */
public class RechargeActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private EditText money;

    private View zhifubao;

    private CheckBox zhifubaocb;

    private View weixin;

    private CheckBox weixincb;

    private View yinlian;

    private CheckBox yinliancb;

    private TextView ok;

    private int checkIndex=1;//1支付宝 2 微信 3银联

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        initView();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        money= (EditText) findViewById(R.id.recharge_money);
        zhifubao=findViewById(R.id.recharge_zhifubao);
        zhifubaocb= (CheckBox) findViewById(R.id.recharge_zhifubao_cb);
        weixin=findViewById(R.id.recharge_weixin);
        weixincb= (CheckBox) findViewById(R.id.recharge_weixin_cb);
        yinlian=findViewById(R.id.recharge_yinlian);
        yinliancb= (CheckBox) findViewById(R.id.recharge_yinlian_cb);
        ok= (TextView) findViewById(R.id.recharge_ok);

        title.setText("付款");
        back.setOnClickListener(this);
        zhifubao.setOnClickListener(this);
        weixin.setOnClickListener(this);
        yinlian.setOnClickListener(this);
        ok.setOnClickListener(this);


        zhifubaocb.setChecked(true);
        weixincb.setChecked(false);
        yinliancb.setChecked(false);
        checkIndex=1;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;

            case R.id.recharge_zhifubao:
                zhifubaocb.setChecked(true);
                weixincb.setChecked(false);
                yinliancb.setChecked(false);
                checkIndex=1;

                break;

            case R.id.recharge_weixin:
                zhifubaocb.setChecked(false);
                weixincb.setChecked(true);
                yinliancb.setChecked(false);
                checkIndex=2;
                break;

            case R.id.recharge_yinlian:
                zhifubaocb.setChecked(false);
                weixincb.setChecked(false);
                yinliancb.setChecked(true);
                checkIndex=3;
                break;

            case R.id.recharge_ok:
                Intent intent=new Intent(RechargeActivity.this,RechargeSucActivity.class);
                startActivity(intent);
                break;
        }
    }
}
