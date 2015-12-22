package com.gaicheyunxiu.gaiche.activity;

import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;

import static com.gaicheyunxiu.gaiche.R.id.personname_cancel;
import static com.gaicheyunxiu.gaiche.R.id.title_service;

/**
 * Created by Mu on 2015/12/22.
 * 设置昵称
 */
public class PersonnameActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private TextView save;

    private EditText name;

    private ImageView cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personname);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        save= (TextView) findViewById(title_service);
        name= (EditText) findViewById(R.id.personname_name);
        cancel= (ImageView) findViewById(R.id.personname_cancel);

        back.setOnClickListener(this);
        title.setText("昵称");
        save.setOnClickListener(this);
        save.setText("保存");
        save.setVisibility(View.VISIBLE);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back:
                finish();
                break;

            case  R.id.title_service:
                finish();
                break;

            case personname_cancel:
                name.setText("");
                break;
        }
    }
}
