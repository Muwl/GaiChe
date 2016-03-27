package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.FacialAdapter;

/**
 * Created by Administrator on 2016/3/27.
 * 美容页面
 */
public class FcialActivity extends BaseActivity  implements View.OnClickListener{

    private TextView title;

    private ImageView back;

    private ListView listView;

    private TextView ok;

    private FacialAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facial);
        initView();
    }

    private void initView() {
        title= (TextView) findViewById(R.id.title_text);
        back= (ImageView) findViewById(R.id.title_back);
        listView= (ListView) findViewById(R.id.facical_listview);
        ok= (TextView) findViewById(R.id.facical_ok);

        back.setOnClickListener(this);
        title.setText("美容");
        adapter=new FacialAdapter(this);
        listView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                break;
        }
    }
}
