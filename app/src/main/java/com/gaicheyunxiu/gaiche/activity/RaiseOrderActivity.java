package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.RaiseOrderAdapter;
import com.gaicheyunxiu.gaiche.adapter.ShopOrderAdapter;

/**
 * Created by Mu on 2016/1/18.
 * 众筹订单
 */
public class RaiseOrderActivity extends BaseActivity implements View.OnClickListener{

    private TextView title;

    private ImageView back;

    private ListView listView;

    private RaiseOrderAdapter adapter;

    private RadioGroup group;

    private RadioButton all;

    private RadioButton paying;

    private RadioButton state;

    private RadioButton evaluating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raiseorder);
        initView();
    }

    private void initView() {

        title= (TextView) findViewById(R.id.title_text);
        back = (ImageView) findViewById(R.id.title_back);
        listView= (ListView) findViewById(R.id.raiseorder_list);
        group= (RadioGroup) findViewById(R.id.raiseorder_group);
        all= (RadioButton) findViewById(R.id.raiseorder_all);
        paying= (RadioButton) findViewById(R.id.raiseorder_paying);
        state= (RadioButton) findViewById(R.id.raiseorder_state);
        evaluating = (RadioButton) findViewById(R.id.raiseorder_evaluating);


        title.setText("众筹订单");
        back.setOnClickListener(this);
        adapter=new RaiseOrderAdapter(this);
        listView.setAdapter(adapter);
        group.check(R.id.raiseorder_all);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }
}
