package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.CrowdfundAdapter;

/**
 * Created by Administrator on 2016/3/27.
 * 众筹页面
 */
public class CrowdFundActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;

    private TextView title;

    private ImageView schedule;

    private TextView bartext;

    private TextView crowed;

    private TextView totalcrow;

    private TextView everycrow;

    private TextView hour;

    private TextView minute;

    private TextView sec;

    private TextView day;

    private RadioGroup group;

    private RadioButton defaultrb;

    private RadioButton pricerb;

    private RadioButton volume;

    private RadioButton brand;

    private ListView listView;

    private CrowdfundAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crowdfund);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        schedule= (ImageView) findViewById(R.id.crowdfund_schedule);
        bartext= (TextView) findViewById(R.id.crowdfund_bartext);
        crowed= (TextView) findViewById(R.id.crowdfund_crowed);
        totalcrow= (TextView) findViewById(R.id.crowdfund_totalcrow);
        everycrow= (TextView) findViewById(R.id.crowdfund_everycrow);
        hour= (TextView) findViewById(R.id.crowdfund_hour);
        minute= (TextView) findViewById(R.id.crowdfund_minute);
        sec= (TextView) findViewById(R.id.crowdfund_sec);
        day= (TextView) findViewById(R.id.crowdfund_day);
        group= (RadioGroup) findViewById(R.id.crowdfund_rb);
        defaultrb= (RadioButton) findViewById(R.id.crowdfund_default);
        pricerb= (RadioButton) findViewById(R.id.crowdfund_moods);
        volume= (RadioButton) findViewById(R.id.crowdfund_technology);
        brand= (RadioButton) findViewById(R.id.crowdfund_price);
        listView= (ListView) findViewById(R.id.crowdfund_lisview);
        adapter=new CrowdfundAdapter(this);
        listView.setAdapter(adapter);

        title.setText("众筹");
        back.setOnClickListener(this);
        group.check(R.id.crowdfund_default);

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
