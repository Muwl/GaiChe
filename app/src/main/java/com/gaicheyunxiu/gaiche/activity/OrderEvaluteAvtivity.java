package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.view.RatingBar;

/**
 * Created by Administrator on 2015/12/29.
 * 评价商品订单
 */
public class OrderEvaluteAvtivity extends BaseActivity implements View.OnClickListener{

    private TextView title;

    private ImageView back;

    private TextView orderNo;

    private TextView time;

    private ImageView imageView;

    private TextView name;

    private TextView num;

    private TextView money;

    private TextView m;

    private EditText evalute;

    private CheckBox cb;

    private RatingBar bar;

    private TextView ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_evaluate);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        orderNo= (TextView) findViewById(R.id.order_evaluate_no);
        time= (TextView) findViewById(R.id.order_evaluate_time);
        imageView= (ImageView) findViewById(R.id.order_evaluate_image);
        name= (TextView) findViewById(R.id.order_evaluate_name);
        num= (TextView) findViewById(R.id.order_evaluate_num);
        money= (TextView) findViewById(R.id.order_evaluate_money);
        m= (TextView) findViewById(R.id.order_evaluate_m);
        evalute= (EditText) findViewById(R.id.order_evaluate_text);
        cb= (CheckBox) findViewById(R.id.order_evaluate_cb);
        bar= (RatingBar) findViewById(R.id.order_evaluate_bar);
        ok= (TextView) findViewById(R.id.order_evaluate_ok);

        back.setOnClickListener(this);
        title.setText("评价商品订单");
        ok.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;

            case R.id.order_evaluate_ok:
                finish();
                break;
        }
    }
}
