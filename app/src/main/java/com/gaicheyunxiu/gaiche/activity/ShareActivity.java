package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;

/**
 * Created by Administrator on 2016/2/11.
 * 分享
 */
public class ShareActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;

    private ImageView back;

    private TextView weixin;

    private TextView weixinCricle;

    private TextView qq;

    private TextView qqCricle;

    private TextView sina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        initView();
    }

    private void initView() {
        title= (TextView) findViewById(R.id.title_text);
        back= (ImageView) findViewById(R.id.title_back);
        weixin= (TextView) findViewById(R.id.share_weixin);
        weixinCricle= (TextView) findViewById(R.id.share_weixincricle);
        qq= (TextView) findViewById(R.id.share_qq);
        qqCricle= (TextView) findViewById(R.id.share_qqcricle);
        sina= (TextView) findViewById(R.id.share_sina);

        title.setText("分享");
        back.setOnClickListener(this);
        weixin.setOnClickListener(this);
        weixinCricle.setOnClickListener(this);
        qq.setOnClickListener(this);
        qqCricle.setOnClickListener(this);
        sina.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;

            case R.id.share_weixin:

                break;
            case R.id.share_weixincricle:

                break;
            case R.id.share_qq:

                break;
            case R.id.share_qqcricle:

                break;
            case R.id.share_sina:

                break;
        }
    }
}
