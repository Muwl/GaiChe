package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.internal.widget.ListViewCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.SeriesAdapter;

/**
 * Created by Administrator on 2016/2/11.
 * 型号
 */
public class SeriesActivity extends BaseActivity implements View.OnClickListener {

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
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(SeriesActivity.this,VolumeActivity.class);
                startActivity(intent);
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
