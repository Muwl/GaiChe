package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.SeriesAdapter;

/**
 * Created by Administrator on 2016/2/11.
 * 排量列表
 */
public class CarTimeActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;

    private ImageView back;

    private TextView brand;

    private ListView listView;

    private SeriesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);
        initView();
    }

    private void initView() {
        title= (TextView) findViewById(R.id.title_text);
        back= (ImageView) findViewById(R.id.title_back);
        brand= (TextView) findViewById(R.id.series_brand);
        listView= (ListView) findViewById(R.id.series_list);

        title.setText("设置车型");
        back.setOnClickListener(this);
        adapter=new SeriesAdapter(this);
        brand.setText("奥迪>A6>1.8L");
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                finish();
            }
        });
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
