package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.SerchAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/4/4.
 * 搜索页面
 */
public class SerchActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;

    private ImageView back;

    private TextView textView;

    private ImageView serch;

    private ImageView cancel;

    private ListView listView;

    private SerchAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serch);
        initView();
    }

    private void initView() {
        title= (TextView) findViewById(R.id.title_text);
        back= (ImageView) findViewById(R.id.title_back);
        textView= (TextView) findViewById(R.id.serch_text);
        serch= (ImageView) findViewById(R.id.serch_serch);
        cancel= (ImageView) findViewById(R.id.serch_cancel);
        listView= (ListView) findViewById(R.id.serch_listview);

        adapter=new SerchAdapter(this);
        listView.setAdapter(adapter);
        title.setText("搜索");
        back.setOnClickListener(this);
        serch.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.serch_serch:

                break;
            case R.id.serch_cancel:

                break;
        }
    }
}
