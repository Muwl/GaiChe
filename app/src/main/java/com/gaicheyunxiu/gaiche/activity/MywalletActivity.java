package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.MywalletAdapter;
import com.gaicheyunxiu.gaiche.dialog.BrandDelDialog;
import com.gaicheyunxiu.gaiche.view.MyListView;

/**
 * Created by Mu on 2016/1/6.
 * 我的钱包页面
 */
public class MywalletActivity  extends BaseActivity implements View.OnClickListener{

    private TextView title;

    private ImageView back;

    private View walletlin;

    private TextView money;

    private MyListView listView;

    private View addBrandlin;

    private ImageView div;

    private MywalletAdapter adapter;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywallet);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        walletlin=findViewById(R.id.mywallet_wallet);
        money= (TextView) findViewById(R.id.mywallet_money);
        listView= (MyListView) findViewById(R.id.mywallet_list);
        div= (ImageView) findViewById(R.id.mywallet_div);
        addBrandlin=findViewById(R.id.mywallet_addbrandlin);

        back.setOnClickListener(this);
        title.setText("我的钱包");
        walletlin.setOnClickListener(this);
        addBrandlin.setOnClickListener(this);
        adapter=new MywalletAdapter(this);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                BrandDelDialog dialog=new BrandDelDialog(MywalletActivity.this,handler);
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
            case R.id.mywallet_addbrandlin:
                Intent intent1=new Intent(MywalletActivity.this,AddbrandcheckActivity.class);
                startActivity(intent1);

                break;
            case R.id.mywallet_wallet:
                Intent intent=new Intent(MywalletActivity.this,WalletActivity.class);
                startActivity(intent);
                break;
        }
    }
}
