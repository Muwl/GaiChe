package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;

/**
 * Created by Administrator on 2016/5/12.
 */
public class ShopDetailInfoActivity  extends BaseActivity implements View.OnClickListener{

    private ImageView back;

    private TextView title;

    private WebView webView;

    String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopdetailinfo);
        initView();

    }

    private void initView() {
        url=getIntent().getStringExtra("url");

        back = (ImageView) findViewById(R.id.title_back);
        title = (TextView) findViewById(R.id.title_text);
        back.setOnClickListener(this);
        title.setText("商品介绍");

        webView = (WebView) findViewById(R.id.shopdetailinfo_webview);
        WebSettings wSet = webView.getSettings();
        wSet.setJavaScriptEnabled(true);
        webView.loadUrl(url);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;

            default:
                break;
        }
    }
}
