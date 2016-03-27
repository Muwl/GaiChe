package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.ShopDetailAdapter;
import com.gaicheyunxiu.gaiche.view.RatingBar;

/**
 * Created by Administrator on 2016/3/20.
 * 商品详情
 */
public class ShopDetailActivity extends BaseActivity implements View.OnClickListener{

    private TextView title;

    private ImageView back;

    private ListView listView;

    private ShopDetailAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopdetail);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        listView= (ListView) findViewById(R.id.shopdetail_list);
        adapter=new ShopDetailAdapter(this);
        listView.setAdapter(adapter);
        title.setText("商品详情");
        title.setOnClickListener(this);
        back.setOnClickListener(this);
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
