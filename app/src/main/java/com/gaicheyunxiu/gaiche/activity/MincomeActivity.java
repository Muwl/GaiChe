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
 * M值收益页面
 */
public class MincomeActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private TextView no;

    private TextView wallet;

    private MyGridView gridView;

    private TextView money;

    private MarginAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mincome);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        no= (TextView) findViewById(R.id.mincome_no);
        wallet= (TextView) findViewById(R.id.mincome_wallet);
        gridView= (MyGridView) findViewById(R.id.mincome_grid);
        money= (TextView) findViewById(R.id.mincome_money);
        back.setOnClickListener(this);
        title.setText("M值收益");
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

            case R.id.mincome_wallet:
                Intent intent=new Intent(MincomeActivity.this,DepwalletActivity.class);
                startActivity(intent);
                break;
        }
    }
}
