package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.dialog.PaymentDialog;

/**
 * Created by Mu on 2015/12/28.
 * 结算界面
 */
public class ClearingActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private TextView person;

    private TextView phone;

    private TextView address;

    private TextView delivery;

    private View addressview;

    private View wallet;

    private CheckBox walletcb;

    private View zhifubao;

    private CheckBox zhifubaocb;

    private View weixin;

    private CheckBox weixincb;

    private View yinlian;

    private CheckBox yinliancb;

    private TextView installcost;

    private TextView shopPrice;

    private TextView freightcost;

    private TextView money;

    private TextView submit;

    private int checkIndex=1;//1钱包 2支付宝 3 微信 4银联

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clearing);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        person= (TextView) findViewById(R.id.clearing_person);
        phone= (TextView) findViewById(R.id.clearing_phone);
        address= (TextView) findViewById(R.id.clearing_address);
        addressview=findViewById(R.id.clearing_addview);
        delivery= (TextView) findViewById(R.id.clearing_delivery);
        wallet=findViewById(R.id.pay_wallet);
        walletcb= (CheckBox) findViewById(R.id.pay_wallet_cb);
        zhifubao=findViewById(R.id.pay_zhifubao);
        zhifubaocb= (CheckBox) findViewById(R.id.pay_zhifubao_cb);
        weixin=findViewById(R.id.pay_weixin);
        weixincb= (CheckBox) findViewById(R.id.pay_weixin_cb);
        yinlian=findViewById(R.id.pay_yinlian);
        yinliancb= (CheckBox) findViewById(R.id.pay_yinlian_cb);
        installcost= (TextView) findViewById(R.id.pay_install_cost);
        shopPrice= (TextView) findViewById(R.id.pay_shop_cost);
        freightcost= (TextView) findViewById(R.id.pay_freight_cost);
        money= (TextView) findViewById(R.id.clearing_money);
        submit= (TextView) findViewById(R.id.clearing_ok);

        title.setText("去结算");
        back.setOnClickListener(this);
        addressview.setOnClickListener(this);
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

            case R.id.clearing_addview:
                break;

            case R.id.pay_zhifubao:
                walletcb.setChecked(false);
                zhifubaocb.setChecked(true);
                weixincb.setChecked(false);
                yinliancb.setChecked(false);
                checkIndex=2;

                break;

            case R.id.pay_wallet:
                walletcb.setChecked(true);
                zhifubaocb.setChecked(false);
                weixincb.setChecked(false);
                yinliancb.setChecked(false);
                checkIndex=1;
                break;

            case R.id.pay_weixin:
                walletcb.setChecked(false);
                zhifubaocb.setChecked(false);
                weixincb.setChecked(true);
                yinliancb.setChecked(false);
                checkIndex=3;
                break;

            case R.id.pay_yinlian:
                walletcb.setChecked(false);
                zhifubaocb.setChecked(false);
                weixincb.setChecked(false);
                yinliancb.setChecked(true);
                checkIndex=4;
                break;

            case R.id.clearing_ok:
                PaymentDialog dialog=new PaymentDialog(ClearingActivity.this);
                break;
        }
    }
}
