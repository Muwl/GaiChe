package com.gaicheyunxiu.gaiche.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;

/**
 * Created by Mu on 2016/1/4.
 * 收益页面
 */
public class EarningActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private View marginlin;

    private TextView margin;

    private View  mlin;

    private TextView m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earnings);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        margin= (TextView) findViewById(R.id.earning_margin);
        marginlin=findViewById(R.id.earning_marginlin);
        mlin=findViewById(R.id.earning_mlin);
        m= (TextView) findViewById(R.id.earning_m);

        back.setOnClickListener(this);
        title.setText("收益");
        marginlin.setOnClickListener(this);
        mlin.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.earning_marginlin:
                Intent intent=new Intent(EarningActivity.this,MarginActivity.class);
                startActivity(intent);

                break;
            case R.id.earning_mlin:
                Intent intent2=new Intent(EarningActivity.this,MincomeActivity.class);
                startActivity(intent2);

                break;
        }

    }
}
