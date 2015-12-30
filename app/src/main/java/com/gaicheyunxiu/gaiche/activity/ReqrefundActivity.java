package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.adapter.ReqrefundAdapter;
import com.gaicheyunxiu.gaiche.utils.DensityUtil;
import com.gaicheyunxiu.gaiche.view.MyGridView;

/**
 * Created by Administrator on 2015/12/30.
 * 申请退货
 */
public class ReqrefundActivity extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private TextView money;

    private EditText reason;

    private EditText info;

    private MyGridView gridView;

    private TextView ok;

    private ReqrefundAdapter adapter;

    private  int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reqrefund);
        width= DensityUtil.getScreenWidth(this);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        money= (TextView) findViewById(R.id.reqrefund_money);
        reason= (EditText) findViewById(R.id.reqrefund_reason);
        info= (EditText) findViewById(R.id.reqrefund_info);
        gridView= (MyGridView) findViewById(R.id.reqrefund_grid);
        ok= (TextView) findViewById(R.id.reqrefund_ok);
        adapter=new ReqrefundAdapter(this,width);

        back.setOnClickListener(this);
        title.setText("申请退货");
        ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;

            case R.id.reqrefund_ok:

                break;
        }

    }
}
