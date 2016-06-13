package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.utils.ToastUtils;

/**
 * Created by Mu on 2016/1/6.
 * 钱包
 */
public class WalletActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private TextView detail;

    private TextView money;

    private TextView recharge;

    private TextView deposit;

    private String smoney;

    private int flag;//0代表以有银行卡  1代表无银行卡

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        smoney=getIntent().getStringExtra("money");
        initView();

    }

    private void initView() {
        flag=getIntent().getIntExtra("flag",0);
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        detail= (TextView) findViewById(R.id.title_service);
        money= (TextView) findViewById(R.id.wallet_money);
        recharge= (TextView) findViewById(R.id.wallet_recharge);
        deposit= (TextView) findViewById(R.id.wallet_deposit);

        back.setOnClickListener(this);
        title.setText("钱包");
        detail.setText("钱包明细");
        detail.setOnClickListener(this);
        detail.setVisibility(View.VISIBLE);
        recharge.setOnClickListener(this);
        deposit.setOnClickListener(this);
        money.setText("￥"+smoney);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_service:
                Intent intent=new Intent(WalletActivity.this,WalletdetailActivity.class);
                startActivity(intent);
                break;
            case R.id.wallet_recharge:
                Intent intent1=new Intent(WalletActivity.this,RechargeActivity.class);
                startActivity(intent1);
                break;
            case R.id.wallet_deposit:
                if (flag == 1) {
                    ToastUtils.displayShortToast(WalletActivity.this, "请添加银行卡！");
                    Intent intent3 = new Intent(WalletActivity.this, AddbrandActivity.class);
                    startActivityForResult(intent3, 1552);
                    break;
                }
                Intent intent2=new Intent(WalletActivity.this,DepositActivity.class);
                intent2.putExtra("money",smoney);
                startActivity(intent2);
                break;
        }
    }
}
