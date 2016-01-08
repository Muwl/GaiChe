package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.ShipaddressAdapter;
import com.gaicheyunxiu.gaiche.dialog.CustomeDialog;

/**
 * Created by Mu on 2016/1/8.
 * 收货地址页面
 */
public class ShipaddressActivity extends BaseActivity implements View.OnClickListener {


    private TextView title;

    private ImageView back;

    private ListView listView;

    private ShipaddressAdapter adapter;

    private  TextView add;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipaddress);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title = (TextView) findViewById(R.id.title_text);
        listView= (ListView) findViewById(R.id.shipaddress_list);
        add= (TextView) findViewById(R.id.title_service);
        adapter=new ShipaddressAdapter(this);

        back.setOnClickListener(this);
        title.setText("收货地址");
        add.setText("新增");
        add.setVisibility(View.VISIBLE);
        add.setOnClickListener(this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(ShipaddressActivity.this,ShipaddressaddActivity.class);
                startActivity(intent);
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                CustomeDialog dialog=new CustomeDialog(ShipaddressActivity.this,handler);
                return true;
            }
        });

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_service:
                Intent intent=new Intent(ShipaddressActivity.this,ShipaddressaddActivity.class);
                startActivity(intent);
                break;
        }
    }
}
