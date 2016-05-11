package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.CarManagerAdapter;
import com.gaicheyunxiu.gaiche.model.MyCarEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/2/11.
 */
public class CarmanagerActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;

    private TextView title;

    private TextView add;

    private ListView listView;

    private CarManagerAdapter adapter;

    private List<MyCarEntity> entities;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carmanager);
        initView();
    }

    private void initView() {
        entities= (List<MyCarEntity>) getIntent().getSerializableExtra("cars");
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        add= (TextView) findViewById(R.id.title_service);
        listView= (ListView) findViewById(R.id.activity_listview);
        back.setOnClickListener(this);
        title.setText("车辆管理");
        add.setVisibility(View.VISIBLE);
        add.setOnClickListener(this);
        adapter=new CarManagerAdapter(this,entities,handler);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.title_service:

                break;
        }
    }
}
