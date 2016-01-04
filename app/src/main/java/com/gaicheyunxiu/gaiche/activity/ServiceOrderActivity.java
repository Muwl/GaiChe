package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.ServiceOrderAdapter;

import static com.gaicheyunxiu.gaiche.R.id.serviceorder_paying;

/**
 * Created by Administrator on 2016/1/1.
 * 服务订单页面
 */
public class ServiceOrderActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private RadioGroup group;

    private RadioButton all;

    private RadioButton paying;

    private RadioButton use;

    private RadioButton evalute;

    private ListView listView;

    private ServiceOrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceorder);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        group= (RadioGroup) findViewById(R.id.serviceorder_group);
        all= (RadioButton) findViewById(R.id.serviceorder_all);
        use= (RadioButton) findViewById(R.id.serviceorder_use);
        paying= (RadioButton) findViewById(R.id.serviceorder_paying);
        evalute= (RadioButton) findViewById(R.id.serviceorder_evaluating);
        listView= (ListView) findViewById(R.id.serviceorder_list);

        back.setOnClickListener(this);
        group.check(R.id.serviceorder_all);
        title.setText("服务订单");
        adapter=new ServiceOrderAdapter(this);
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
