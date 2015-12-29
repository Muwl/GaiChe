package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.ShopOrderAdapter;

/**
 * Created by Administrator on 2015/12/29.
 * 商品订单页面
 */
public class ShopOrderActivity extends  BaseActivity implements View.OnClickListener{

    private TextView title;

    private ImageView back;

    private ListView listView;

    private ShopOrderAdapter adapter;

    private RadioGroup group;

    private RadioButton all;

    private RadioButton paying;

    private RadioButton receiving;

    private RadioButton evaluating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoporder);
        initView();
    }

    private void initView() {
        title= (TextView) findViewById(R.id.title_text);
        back = (ImageView) findViewById(R.id.title_back);
        listView= (ListView) findViewById(R.id.shoporder_list);
        group= (RadioGroup) findViewById(R.id.shoporder_group);
        all= (RadioButton) findViewById(R.id.shoporder_all);
        paying= (RadioButton) findViewById(R.id.shoporder_paying);
        receiving= (RadioButton) findViewById(R.id.shoporder_receiving);
        evaluating= (RadioButton) findViewById(R.id.shoporder_evaluating);

        title.setText("商品订单");
        back.setOnClickListener(this);
        adapter=new ShopOrderAdapter(this);
        listView.setAdapter(adapter);
        group.check(R.id.shoporder_all);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
