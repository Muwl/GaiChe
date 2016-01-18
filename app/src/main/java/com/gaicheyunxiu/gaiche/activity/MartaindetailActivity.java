package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.MartaindetailServiceAdapter;
import com.gaicheyunxiu.gaiche.adapter.MartaindetailShopAdapter;

/**
 * Created by Mu on 2016/1/18.
 *  保养档案详情
 */
public class MartaindetailActivity extends BaseActivity implements View.OnClickListener{

    private TextView title;

    private ImageView back;

    private TextView brand;

    private RadioGroup group;

    private RadioButton goodrb;

    private RadioButton servicerb;

    private ListView listView;

    private TextView money;

    private MartaindetailServiceAdapter serviceAdapter;

    private MartaindetailShopAdapter shopAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_martaindetail);
        initView();
    }

    private void initView() {
        title= (TextView) findViewById(R.id.title_text);
        back= (ImageView) findViewById(R.id.title_back);
        brand = (TextView) findViewById(R.id.martaindetial_car);
        group= (RadioGroup) findViewById(R.id.martaindetial_group);
        goodrb= (RadioButton) findViewById(R.id.martaindetial_good);
        servicerb= (RadioButton) findViewById(R.id.martaindetial_service);
        listView= (ListView) findViewById(R.id.martaindetial_list);
        money= (TextView) findViewById(R.id.martaindetial_money);

        title.setText("保养档案");
        back.setOnClickListener(this);
        serviceAdapter=new MartaindetailServiceAdapter(this);
        shopAdapter=new MartaindetailShopAdapter(this);
        listView.setAdapter(shopAdapter);
        group.check(R.id.martaindetial_good);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId==R.id.martaindetial_good){
                    listView.setAdapter(shopAdapter);
                }else {
                    listView.setAdapter(serviceAdapter);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back:
                finish();
                break;
        }
    }
}
