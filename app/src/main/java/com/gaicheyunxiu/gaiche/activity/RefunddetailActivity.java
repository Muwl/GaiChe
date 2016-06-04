package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.RefunddetailAdapter;
import com.gaicheyunxiu.gaiche.model.ShopOrderVo;
import com.gaicheyunxiu.gaiche.view.MyListView;

/**
 * Created by Administrator on 2015/12/30.
 * 退款详情页面
 */
public class RefunddetailActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private TextView money;

    private TextView style;

    private TextView state;

    private MyListView listView;

    private RefunddetailAdapter adapter;

    private String orderId;

    private ShopOrderVo entity;

    private String orderNo;

    private String smoney;

    private int flag;

    private View pro;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refunddetail);

        orderId=getIntent().getStringExtra("orderId");
        entity= (ShopOrderVo) getIntent().getSerializableExtra("entity");
        smoney=getIntent().getStringExtra("smoney");
        flag=getIntent().getIntExtra("flag",0);

        initView();
    }

    private void initView() {
        title= (TextView) findViewById(R.id.title_text);
        back= (ImageView) findViewById(R.id.title_back);
        money= (TextView) findViewById(R.id.refunddetail_money);
        style= (TextView) findViewById(R.id.refunddetail_style);
        listView= (MyListView) findViewById(R.id.refunddetail_list);
        pro= findViewById(R.id.refunddetail_pro);
        title.setText("退款详情");
        back.setOnClickListener(this);
        adapter=new RefunddetailAdapter(this);
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
