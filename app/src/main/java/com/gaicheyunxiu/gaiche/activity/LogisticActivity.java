package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.LogisticAdapter;

/**
 * Created by Mu on 2016/1/18.
 * 物流列表页面
 */
public class LogisticActivity extends BaseActivity implements View.OnClickListener{

    private TextView title;

    private ImageView back;

    private ListView listView;

    private LogisticAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics);
        initView();
    }

    private void initView() {

        title= (TextView) findViewById(R.id.title_text);
        back= (ImageView) findViewById(R.id.title_back);
        listView= (ListView) findViewById(R.id.logistic_list);

        title.setText("查看物流");
        back.setOnClickListener(this);

        adapter=new LogisticAdapter(this);
        listView.setAdapter(adapter);
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
