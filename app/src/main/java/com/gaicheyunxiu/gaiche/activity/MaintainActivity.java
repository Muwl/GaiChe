package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.MaintainAdapter;

/**
 * Created by Mu on 2016/1/8.
 * 保养档案页面
 */
public class MaintainActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private ListView listView;

    private MaintainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        listView= (ListView) findViewById(R.id.maintain_list);
        adapter=new MaintainAdapter(this);

        back.setOnClickListener(this);
        title.setText("保养档案");
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MaintainActivity.this,MartainDetailActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }
}
