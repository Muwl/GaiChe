package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.BrandAdapter;

/**
 * Created by Administrator on 2016/3/20.
 * 品牌选择
 */
public class BrandActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;

    private TextView title;

    private ListView listView;

    private BrandAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        listView= (ListView) findViewById(R.id.brand_list);

        back.setOnClickListener(this);
        title.setText("品牌");

        adapter=new BrandAdapter(this);
        listView.setAdapter(adapter);

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
