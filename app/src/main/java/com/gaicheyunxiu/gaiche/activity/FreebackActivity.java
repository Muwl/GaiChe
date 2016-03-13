package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;

/**
 * Created by Administrator on 2016/2/11.
 */
public class FreebackActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;

    private ImageView back;

    private EditText content;

    private TextView ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freeback);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        content= (EditText) findViewById(R.id.freeback_content);
        ok= (TextView) findViewById(R.id.freeback_ok);

        back.setOnClickListener(this);
        title.setText("意见反馈");
        ok.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;

            case R.id.freeback_ok:
                break;
        }
    }
}
