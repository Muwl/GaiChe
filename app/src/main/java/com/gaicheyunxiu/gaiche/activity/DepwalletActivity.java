package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;

/**
 * Created by Mu on 2016/1/6.
 * 存入钱包
 */
public class DepwalletActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private EditText money;

    private TextView ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depwallet);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        money= (EditText) findViewById(R.id.depwallet_money);
        ok= (TextView) findViewById(R.id.depwallet_ok);

        back.setOnClickListener(this);
        title.setText("存入钱包");
        ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;

            case R.id.depwallet_ok:
                break;
        }

    }
}
