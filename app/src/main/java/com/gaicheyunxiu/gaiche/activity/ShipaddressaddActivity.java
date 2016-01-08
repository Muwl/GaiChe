package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;

/**
 * Created by Mu on 2016/1/8.
 * 新增收货地址
 */
public class ShipaddressaddActivity extends BaseActivity implements View.OnClickListener{

    private TextView title;

    private ImageView back;

    private TextView com;

    private EditText name;

    private EditText phone;

    private View arealin;

    private TextView area;

    private EditText address;

    private EditText telphone;

    private EditText code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipaddress_add);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        com= (TextView) findViewById(R.id.title_service);
        name= (EditText) findViewById(R.id.shipaddress_add_name);
        phone= (EditText) findViewById(R.id.shipaddress_add_phone);
        arealin=findViewById(R.id.shipaddress_add_arealin);
        area= (TextView) findViewById(R.id.shipaddress_add_area);
        address= (EditText) findViewById(R.id.shipaddress_add_address);
        telphone= (EditText) findViewById(R.id.shipaddress_add_telphone);
        code= (EditText) findViewById(R.id.shipaddress_add_code);

        back.setOnClickListener(this);
        title.setText("新增收货地址");
        com.setVisibility(View.VISIBLE);
        com.setText("完成");
        arealin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;

            case R.id.title_service:

                break;

            case R.id.shipaddress_add_arealin:

                break;
        }
    }
}
