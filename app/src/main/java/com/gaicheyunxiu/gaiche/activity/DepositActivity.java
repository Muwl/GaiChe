package com.gaicheyunxiu.gaiche.activity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.DepositAdapter;
import com.gaicheyunxiu.gaiche.view.MyListView;

import java.util.List;

/**
 * Created by Mu on 2016/1/7.
 * 提现页面
 */
public class DepositActivity  extends BaseActivity implements View.OnClickListener{

    private TextView title;

    private ImageView back;

    private MyListView listView;

    private EditText money;

    private TextView ok;

    private DepositAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        listView= (MyListView) findViewById(R.id.deposit_list);
        money= (EditText) findViewById(R.id.deposit_money);
        ok= (TextView) findViewById(R.id.deposit_ok);
        back.setOnClickListener(this);
        title.setText("提现");
        ok.setOnClickListener(this);

        adapter=new DepositAdapter(this);
        listView.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;

            case R.id.deposit_ok:
                break;
        }
    }
}
