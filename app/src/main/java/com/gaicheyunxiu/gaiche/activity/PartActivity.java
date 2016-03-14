package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.PartsAdapter;

import org.apache.http.impl.cookie.BestMatchSpec;

/**
 * Created by Administrator on 2016/3/14.
 * 配件列表页面
 */
public class PartActivity  extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private TextView car;

    private View carLin;

    private RadioGroup group;

    private RadioButton defaultrb;

    private RadioButton pricerb;

    private RadioButton volume;

    private RadioButton brand;

    private ListView listView;

    private PartsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        car= (TextView) findViewById(R.id.part_carbrand);
        carLin=findViewById(R.id.part_carlin);
        group= (RadioGroup) findViewById(R.id.part_rb);
        defaultrb= (RadioButton) findViewById(R.id.part_default);
        pricerb= (RadioButton) findViewById(R.id.part_moods);
        volume= (RadioButton) findViewById(R.id.part_technology);
        brand= (RadioButton) findViewById(R.id.part_price);
        listView= (ListView) findViewById(R.id.part_lisview);
        adapter=new PartsAdapter(this);
        listView.setAdapter(adapter);
        group.check(R.id.part_default);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
        }
    }
}
