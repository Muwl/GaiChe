package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.MaintainDetailServiceAdapter;
import com.gaicheyunxiu.gaiche.adapter.MaintainDetailShopAdapter;

/**
 * Created by Administrator on 2016/3/12.
 * 保养档案页面
 */
public class MartainDetailActivity extends BaseActivity implements OnClickListener {

    private TextView title;

    private ImageView back;

    private TextView car;

    private RadioGroup group;

    private RadioButton shop;

    private RadioButton service;

    private ListView listView;

    private TextView money;

    private MaintainDetailServiceAdapter serviceAdapter;

    private MaintainDetailShopAdapter shopAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_martaindetail);
        initView();
    }

    private void initView() {
        title= (TextView) findViewById(R.id.title_text);
        back= (ImageView) findViewById(R.id.title_back);
        car= (TextView) findViewById(R.id.martaindetial_car);
        group= (RadioGroup) findViewById(R.id.martaindetial_group);
        shop= (RadioButton) findViewById(R.id.martaindetial_good);
        service= (RadioButton) findViewById(R.id.martaindetial_service);
        listView= (ListView) findViewById(R.id.martaindetial_list);
        money= (TextView) findViewById(R.id.martaindetial_money);

        title.setText("保养档案");
        back.setOnClickListener(this);
        group.check(R.id.martaindetial_good);

        serviceAdapter=new MaintainDetailServiceAdapter(this);
        shopAdapter=new MaintainDetailShopAdapter(this);
        listView.setAdapter(shopAdapter);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.martaindetial_good){
                    listView.setAdapter(shopAdapter);
                }else{
                    listView.setAdapter(serviceAdapter);
                }
            }
        });




    }

    @Override
    public void onClick(View v) {

    }
}
