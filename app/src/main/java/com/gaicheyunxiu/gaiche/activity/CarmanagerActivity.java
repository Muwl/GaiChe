package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.CarManagerAdapter;

/**
 * Created by Administrator on 2016/2/11.
 */
public class CarmanagerActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;

    private TextView title;

    private TextView add;

    private ListView listView;

    private CarManagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carmanager);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
    }

    @Override
    public void onClick(View v) {

    }
}
