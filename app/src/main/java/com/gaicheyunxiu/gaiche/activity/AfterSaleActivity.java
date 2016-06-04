package com.gaicheyunxiu.gaiche.activity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.AfterSaleAdapter;
import com.gaicheyunxiu.gaiche.model.ShopOrderVo;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Administrator on 2015/12/30.
 * 退款售后
 */
public class AfterSaleActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private ListView listView;

    private AfterSaleAdapter adapter;

    private String orderId;

    private ShopOrderVo entity;

    private String orderNo;

    private String smoney;

    private int flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aftersale);
        initView();
    }

    private void initView() {
        orderId=getIntent().getStringExtra("orderId");
        entity= (ShopOrderVo) getIntent().getSerializableExtra("entity");
        orderNo=getIntent().getStringExtra("orderNo");
        smoney=getIntent().getStringExtra("money");
        flag=getIntent().getIntExtra("flag",0);

        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        listView= (ListView) findViewById(R.id.aftersale_list);
        back.setOnClickListener(this);
        title.setText("钱款去向");
        adapter=new AfterSaleAdapter(this,orderId,entity,orderNo,smoney,flag);
        listView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;

        }
    }
}
