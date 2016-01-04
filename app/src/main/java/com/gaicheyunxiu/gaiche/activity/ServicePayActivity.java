package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.ServiceOrderAdapter;
import com.gaicheyunxiu.gaiche.dialog.PaymentDialog;

/**
 * Created by Administrator on 2016/1/2.
 */
public class ServicePayActivity extends  BaseActivity implements View.OnClickListener {
    private ImageView back;

    private TextView title;

    private TextView person;

    private TextView phone;

    private View lin;

    private TextView num;

    private View wallet;

    private CheckBox walletcb;

    private View zhifubao;

    private CheckBox zhifubaocb;

    private View weixin;

    private CheckBox weixincb;

    private View yinlian;

    private CheckBox yinliancb;

    private TextView money;

    private TextView submit;

    private int checkIndex=1;//1钱包 2支付宝 3 微信 4银联

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicepay);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        wallet=findViewById(R.id.servicepay_wallet);
        person= (TextView) findViewById(R.id.service_pay_person);
        phone= (TextView) findViewById(R.id.service_pay_phone);
        lin=findViewById(R.id.servicepay_lin);
        num= (TextView) findViewById(R.id.servicepay_num);
        walletcb= (CheckBox) findViewById(R.id.servicepay_wallet_cb);
        zhifubao=findViewById(R.id.servicepay_zhifubao);
        zhifubaocb= (CheckBox) findViewById(R.id.servicepay_zhifubao_cb);
        weixin=findViewById(R.id.servicepay_weixin);
        weixincb= (CheckBox) findViewById(R.id.servicepay_weixin_cb);
        yinlian=findViewById(R.id.servicepay_yinlian);
        yinliancb= (CheckBox) findViewById(R.id.servicepay_yinlian_cb);
        money= (TextView) findViewById(R.id.servicepay_money);
        submit= (TextView) findViewById(R.id.servicepay_ok);

        title.setText("付款");
        back.setOnClickListener(this);
        lin.setOnClickListener(this);
        zhifubao.setOnClickListener(this);
        weixin.setOnClickListener(this);
        wallet.setOnClickListener(this);
        yinlian.setOnClickListener(this);
        submit.setOnClickListener(this);

        walletcb.setChecked(true);
        zhifubaocb.setChecked(false);
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

            case R.id.servicepay_lin:

                break;
            
            case R.id.servicepay_zhifubao:
                walletcb.setChecked(false);
                zhifubaocb.setChecked(true);
                weixincb.setChecked(false);
                yinliancb.setChecked(false);
                checkIndex=2;

                break;

            case R.id.servicepay_wallet:
                walletcb.setChecked(true);
                zhifubaocb.setChecked(false);
                weixincb.setChecked(false);
                yinliancb.setChecked(false);
                checkIndex=1;
                break;

            case R.id.servicepay_weixin:
                walletcb.setChecked(false);
                zhifubaocb.setChecked(false);
                weixincb.setChecked(true);
                yinliancb.setChecked(false);
                checkIndex=3;
                break;

            case R.id.servicepay_yinlian:
                walletcb.setChecked(false);
                zhifubaocb.setChecked(false);
                weixincb.setChecked(false);
                yinliancb.setChecked(true);
                checkIndex=4;
                break;

            case R.id.servicepay_ok:
                PaymentDialog dialog=new PaymentDialog(ServicePayActivity.this);
                break;
        }
    }
}
