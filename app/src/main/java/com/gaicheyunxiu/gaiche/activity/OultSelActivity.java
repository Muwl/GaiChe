package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.OuletSelAdapter;
import com.gaicheyunxiu.gaiche.utils.DensityUtil;

/**
 * Created by Mu on 2015/12/24.
 * 选择门店页面
 */
public class OultSelActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private View map;

    private RadioGroup group;

    private RadioButton rdefault;

    private RadioButton moods;

    private RadioButton technology;

    private RadioButton price;

    private ListView listView;

    private OuletSelAdapter adapter;

    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ouletsel);
        width= DensityUtil.getScreenWidth(this);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        map=findViewById(R.id.title_map);
        group= (RadioGroup) findViewById(R.id.ouletsel_rb);
        rdefault= (RadioButton) findViewById(R.id.ouletsel_default);
        moods= (RadioButton) findViewById(R.id.ouletsel_moods);
        technology= (RadioButton) findViewById(R.id.ouletsel_technology);
        price= (RadioButton) findViewById(R.id.ouletsel_price);
        listView= (ListView) findViewById(R.id.ouletsel_list);


        title.setText("选择门店");
        map.setVisibility(View.VISIBLE);
        map.setOnClickListener(this);
        adapter=new OuletSelAdapter(this,width);
        listView.setAdapter(adapter);
        group.check(R.id.ouletsel_default);
        back.setOnClickListener(this);


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
