package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.CarbrandAdapter;
import com.gaicheyunxiu.gaiche.view.sortlistview.SideBar;

/**
 * Created by Administrator on 2016/2/11.
 */
public class CarbrandActivity extends BaseActivity  implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private ListView listView;

    private TextView dialog;

    private SideBar sideBar;

    private View pro;

    private View gv;

    private CarbrandAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbrand);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        listView= (ListView) findViewById(R.id.carbrand_list);
        gv=  findViewById(R.id.carbrand_gv);
        dialog= (TextView) findViewById(R.id.carbrand_dialog);
        sideBar= (SideBar) findViewById(R.id.constants_sidrbar);
        pro=findViewById(R.id.constants_pro);
        adapter=new CarbrandAdapter(this);

        back.setOnClickListener(this);
        title.setText("设置车型");
        listView.setAdapter(adapter);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
        }

    }
}
