package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;
import com.gaicheyunxiu.gaiche.utils.Constant;
import com.gaicheyunxiu.gaiche.utils.LogManager;

/**
 * Created by Administrator on 2016/2/11.
 * 退款帮助
 */
public class FundInfoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView back;

    private TextView title;

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
    }

    private void initView() {
        back= (ImageView) findViewById(R.id.title_back);
        title= (TextView) findViewById(R.id.title_text);
        webView= (WebView) findViewById(R.id.adbout_webview);
        back.setOnClickListener(this);
        title.setText("退款帮助");
        WebSettings wSet = webView.getSettings();
        wSet.setJavaScriptEnabled(true);
        String temp=Constant.ROOT_PATH+"description/agreementAndAbout?modelType=7";
        LogManager.LogShow("-----",temp,LogManager.ERROR);
        webView.loadUrl(temp);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
        }
    }
}
