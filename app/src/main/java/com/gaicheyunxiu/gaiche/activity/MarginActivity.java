package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.MarginAdapter;
import com.gaicheyunxiu.gaiche.view.MyGridView;

/**
 * Created by Mu on 2016/1/6.
 * 商品保证金页面
 */
public class MarginActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private TextView no;

    private TextView wallet;

    private MyGridView gridView;

    private TextView m;

    private TextView money;

    private MarginAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_margin);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        no= (TextView) findViewById(R.id.margin_no);
        wallet= (TextView) findViewById(R.id.margin_wallet);
        gridView= (MyGridView) findViewById(R.id.margin_grid);
        m= (TextView) findViewById(R.id.margin_m);
        money= (TextView) findViewById(R.id.margin_money);
        back.setOnClickListener(this);
        title.setText("商品保证金");
        back.setOnClickListener(this);
        wallet.setOnClickListener(this);
        adapter=new MarginAdapter(this);
        gridView.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;

            case R.id.margin_wallet:
                Intent intent=new Intent(MarginActivity.this,DepwalletActivity.class);
                startActivity(intent);
                break;
        }
    }
}
