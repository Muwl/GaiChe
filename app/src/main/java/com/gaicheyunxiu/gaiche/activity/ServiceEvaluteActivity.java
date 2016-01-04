package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.view.RatingBar;

import static com.gaicheyunxiu.gaiche.R.id.service_evaluate_cb;

/**
 * Created by Administrator on 2016/1/1.
 * 评价服务订单
 */
public class ServiceEvaluteActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;

    private ImageView back;

    private TextView store;

    private TextView name;

    private RatingBar bar1;

    private RatingBar bar2;

    private RatingBar bar3;

    private RatingBar bar4;

    private EditText content;

    private CheckBox cb;

    private RatingBar bar5;

    private TextView commite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_evalute);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        store= (TextView) findViewById(R.id.service_evalute_store);
        name= (TextView) findViewById(R.id.service_evalute_name);
        bar1= (RatingBar) findViewById(R.id.service_evalute_bar1);
        bar2= (RatingBar) findViewById(R.id.service_evalute_bar2);
        bar3= (RatingBar) findViewById(R.id.service_evalute_bar3);
        bar4= (RatingBar) findViewById(R.id.service_evalute_bar4);
        content= (EditText) findViewById(R.id.service_evaluate_text);
        cb= (CheckBox) findViewById(service_evaluate_cb);
        bar5= (RatingBar) findViewById(R.id.service_evaluate_bar);
        commite= (TextView) findViewById(R.id.service_evaluate_ok);

        back.setOnClickListener(this);
        title.setText("评价服务订单");
        commite.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case  R.id.service_evaluate_ok:
                finish();
                break;

        }
    }
}
