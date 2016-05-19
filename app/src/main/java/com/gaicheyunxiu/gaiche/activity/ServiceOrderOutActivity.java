package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.ServiceOrderOutAdapter;
import com.gaicheyunxiu.gaiche.model.ShopServiceEntity;

import org.w3c.dom.ls.LSInput;

import java.util.List;

/**
 * Created by Administrator on 2016/5/20.
 */
public class ServiceOrderOutActivity extends BaseActivity  {

    private ImageView back;
    private TextView title;
    private ListView listView;
    private ServiceOrderOutAdapter adapter;
    private List<ShopServiceEntity> entities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceoder);
        initView();
    }

    private void initView() {
        entities= (List<ShopServiceEntity>) getIntent().getSerializableExtra("entities");
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        listView= (ListView) findViewById(R.id.serviceorder_list);
        adapter=new ServiceOrderOutAdapter(this,entities);
        listView.setAdapter(adapter);
        title.setText("服务清单");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
