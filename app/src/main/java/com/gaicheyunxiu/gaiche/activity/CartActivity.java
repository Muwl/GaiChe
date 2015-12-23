package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.CartAdapter;

import java.io.Serializable;

/**
 * Created by Mu on 2015/12/23.
 * 购物车页面
 */
public class CartActivity extends BaseActivity  implements View.OnClickListener {

    private ImageView back;

    private TextView title;

    private View map;

    private ListView listView;

    private CartAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        map=findViewById(R.id.title_map);
        listView= (ListView) findViewById(R.id.cart_list);
        adapter=new CartAdapter(this);

        back.setOnClickListener(this);
        title.setOnClickListener(this);
        title.setText("购物车");
        map.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        map.setOnClickListener(this);
        listView.setAdapter(adapter);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.title_map:
                break;
        }

    }
}
