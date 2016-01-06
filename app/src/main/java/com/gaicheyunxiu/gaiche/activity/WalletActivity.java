package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        detail= (TextView) findViewById(R.id.title_service);
        money= (TextView) findViewById(R.id.wallet_money);
        recharge= (TextView) findViewById(R.id.wallet_recharge);
        deposit= (TextView) findViewById(R.id.wallet_deposit);

        back.setOnClickListener(this);
        title.setText("钱包");
        detail.setText("钱包明细");
        detail.setVisibility(View.VISIBLE);
        recharge.setOnClickListener(this);
        deposit.setOnClickListener(this);
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
                break;
            case R.id.wallet_deposit:
                break;
        }
    }
}
