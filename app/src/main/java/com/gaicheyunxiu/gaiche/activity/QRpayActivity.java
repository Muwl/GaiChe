package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.dialog.PaymentDialog;
import com.gaicheyunxiu.gaiche.view.RoundAngleImageView;

/**
 * Created by Administrator on 2016/3/13.
 * 扫码支付
 */
public class QRpayActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private RoundAngleImageView imageView;

    private TextView name;

    private EditText money;

    private EditText info;

    private TextView pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrpay);
        initView();
    }

    private void initView() {
        title= (TextView) findViewById(R.id.title_text);
        back= (ImageView) findViewById(R.id.title_back);
        imageView= (RoundAngleImageView) findViewById(R.id.qrpay_iamge);
        name= (TextView) findViewById(R.id.qrpay_name);
        money= (EditText) findViewById(R.id.qrpay_money);
        info= (EditText) findViewById(R.id.qrpay_info);
        pay= (TextView) findViewById(R.id.qrpay_pay);

        title.setText("扫码支付");
        back.setOnClickListener(this);
        pay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;

            case R.id.qrpay_pay:
                PaymentDialog dialog=new PaymentDialog(QRpayActivity.this);

                break;
        }
    }
}
