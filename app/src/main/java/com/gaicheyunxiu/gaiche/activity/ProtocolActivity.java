package com.gaicheyunxiu.gaiche.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaicheyunxiu.gaiche.R;

/**
 * Created by Administrator on 2016/2/11.
 * 关于界面
 */
public class ProtocolActivity extends BaseActivity implements View.OnClickListener {

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
        title.setText("用户协议");
        WebSettings wSet = webView.getSettings();
        wSet.setJavaScriptEnabled(true);
        webView.loadUrl("http://api.gaicheyunxiu.com/api/description/agreementAndAbout?modelType=1");

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
